package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ExamquestionEntity;
import com.entity.KechengxinxiEntity;
import com.entity.NewsEntity;
import com.entity.SystemintroEntity;
import com.hankcs.hanlp.HanLP;
import com.service.AiStudyService;
import com.service.ExamquestionService;
import com.service.KechengxinxiService;
import com.service.NewsService;
import com.service.SystemintroService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("aiStudyService")
public class AiStudyServiceImpl implements AiStudyService {

    @Autowired
    private KechengxinxiService kechengxinxiService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private SystemintroService systemintroService;

    @Autowired
    private ExamquestionService examquestionService;

    @Value("${deepseek.api-url:https://api.deepseek.com/chat/completions}")
    private String apiUrl;

    @Value("${deepseek.api-key:}")
    private String apiKey;

    @Value("${deepseek.model:deepseek-chat}")
    private String model;

    @Override
    public Map<String, Object> chat(String question, String mode) {
        String safeQuestion = StringUtils.defaultIfBlank(question, "请推荐适合当前平台的安全教育课程与练习题");
        String safeMode = detectMode(question, mode);

        List<Chunk> chunks = buildChunks();
        List<String> keywords = buildKeywords(safeQuestion, safeMode);
        List<Chunk> retrieved = retrieveChunks(chunks, keywords, safeMode);
        List<Map<String, Object>> references = buildReferences(retrieved);
        List<Map<String, Object>> courses = buildRecommendedCourses(retrieved, chunks, safeQuestion);
        List<Map<String, Object>> questions = buildPracticeQuestions(retrieved, chunks);

        String localAnswer = buildLocalAnswer(safeQuestion, references, courses, questions);
        String aiAnswer = callDeepSeek(safeQuestion, safeMode, references, courses, questions);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("question", safeQuestion);
        data.put("mode", safeMode);
        data.put("answer", StringUtils.isNotBlank(aiAnswer) ? aiAnswer : localAnswer);
        data.put("fallbackAnswer", localAnswer);
        data.put("usedModel", StringUtils.isNotBlank(aiAnswer));
        data.put("references", references);
        data.put("recommendedCourses", courses);
        data.put("practiceQuestions", questions);
        data.put("generatedAt", new Date());
        return data;
    }

    private String detectMode(String question, String mode) {
        if (StringUtils.isNotBlank(mode)) {
            return mode;
        }
        String clean = sanitize(question);
        if (clean.contains("课程") || clean.contains("推荐课") || clean.contains("学什么")) {
            return "course";
        }
        if (clean.contains("试题") || clean.contains("题目") || clean.contains("练题") || clean.contains("考试")) {
            return "question";
        }
        return "study";
    }

    private List<Chunk> buildChunks() {
        List<Chunk> chunks = new ArrayList<Chunk>();

        List<KechengxinxiEntity> courses = kechengxinxiService.selectList(
                new EntityWrapper<KechengxinxiEntity>().eq("onshelves", 1).orderBy("clicknum", false)
        );
        for (KechengxinxiEntity item : courses) {
            Chunk chunk = new Chunk();
            chunk.type = "course";
            chunk.id = item.getId();
            chunk.title = item.getKechengmingcheng();
            chunk.subtitle = item.getKechengleixing();
            chunk.summary = sanitize(item.getKechengdagang());
            chunk.content = join(
                    item.getKechengmingcheng(),
                    item.getKechengleixing(),
                    item.getSuoshuxueke(),
                    item.getShoukejiaoshi(),
                    item.getShoukeshijian(),
                    item.getShoukedidian(),
                    sanitize(item.getKechengdagang())
            );
            chunk.payload = new LinkedHashMap<String, Object>();
            chunk.payload.put("id", item.getId());
            chunk.payload.put("title", item.getKechengmingcheng());
            chunk.payload.put("category", item.getKechengleixing());
            chunk.payload.put("teacher", item.getShoukejiaoshi());
            chunk.payload.put("picture", item.getTupian());
            chunks.add(chunk);
        }

        List<NewsEntity> newsList = newsService.selectList(new EntityWrapper<NewsEntity>().orderBy("addtime", false));
        for (NewsEntity item : newsList) {
            Chunk chunk = new Chunk();
            chunk.type = "news";
            chunk.id = item.getId();
            chunk.title = item.getTitle();
            chunk.subtitle = item.getName();
            chunk.summary = sanitize(item.getIntroduction());
            chunk.content = join(item.getTitle(), item.getIntroduction(), sanitize(item.getContent()), item.getName());
            chunk.payload = new LinkedHashMap<String, Object>();
            chunk.payload.put("id", item.getId());
            chunk.payload.put("title", item.getTitle());
            chunk.payload.put("publisher", item.getName());
            chunks.add(chunk);
        }

        List<SystemintroEntity> intros = systemintroService.selectList(new EntityWrapper<SystemintroEntity>().orderBy("id", true));
        for (SystemintroEntity item : intros) {
            Chunk chunk = new Chunk();
            chunk.type = "intro";
            chunk.id = item.getId();
            chunk.title = item.getTitle();
            chunk.subtitle = item.getSubtitle();
            chunk.summary = sanitize(item.getContent());
            chunk.content = join(item.getTitle(), item.getSubtitle(), sanitize(item.getContent()));
            chunk.payload = new LinkedHashMap<String, Object>();
            chunk.payload.put("id", item.getId());
            chunk.payload.put("title", item.getTitle());
            chunks.add(chunk);
        }

        List<ExamquestionEntity> examQuestions = examquestionService.selectList(
                new EntityWrapper<ExamquestionEntity>().orderBy("paperid", true).orderBy("sequence", false)
        );
        for (ExamquestionEntity item : examQuestions) {
            Chunk chunk = new Chunk();
            chunk.type = "question";
            chunk.id = item.getId();
            chunk.title = item.getPapername();
            chunk.subtitle = questionTypeText(item.getType());
            chunk.summary = sanitize(item.getQuestionname());
            chunk.content = join(
                    item.getPapername(),
                    sanitize(item.getQuestionname()),
                    sanitize(item.getAnalysis()),
                    questionTypeText(item.getType())
            );
            chunk.payload = new LinkedHashMap<String, Object>();
            chunk.payload.put("id", item.getId());
            chunk.payload.put("paperName", item.getPapername());
            chunk.payload.put("questionType", questionTypeText(item.getType()));
            chunk.payload.put("question", sanitize(item.getQuestionname()));
            chunk.payload.put("analysis", sanitize(item.getAnalysis()));
            chunk.payload.put("answer", item.getAnswer());
            chunks.add(chunk);
        }
        return chunks;
    }

    private List<String> buildKeywords(String question, String mode) {
        Set<String> words = new HashSet<String>();
        String clean = sanitize(question);
        if (StringUtils.isNotBlank(clean)) {
            words.add(clean);
            List<String> hanlpWords = HanLP.extractKeyword(clean, 8);
            words.addAll(hanlpWords);
            Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]{2,}|[a-zA-Z0-9]{2,}").matcher(clean);
            while (matcher.find()) {
                words.add(matcher.group());
            }
        }
        if ("course".equals(mode) || clean.contains("课程") || clean.contains("学习")) {
            words.addAll(Arrays.asList("课程", "学习", "推荐", "复习"));
        }
        if ("question".equals(mode) || clean.contains("试题") || clean.contains("考试") || clean.contains("练习")) {
            words.addAll(Arrays.asList("试题", "考试", "练习", "判断题", "选择题"));
        }
        if (clean.contains("消防") || clean.contains("火灾")) {
            words.addAll(Arrays.asList("消防", "火灾", "应急", "逃生"));
        }
        if (clean.contains("诈骗") || clean.contains("网络")) {
            words.addAll(Arrays.asList("诈骗", "网络", "账号", "隐私"));
        }
        return new ArrayList<String>(words);
    }

    private List<Chunk> retrieveChunks(List<Chunk> chunks, List<String> keywords, String mode) {
        List<Chunk> ranked = new ArrayList<Chunk>();
        for (Chunk chunk : chunks) {
            double score = scoreChunk(chunk, keywords, mode);
            if (score > 0) {
                chunk.score = score;
                ranked.add(chunk);
            }
        }
        Collections.sort(ranked, new Comparator<Chunk>() {
            @Override
            public int compare(Chunk a, Chunk b) {
                return Double.compare(b.score, a.score);
            }
        });
        if (ranked.size() < 6) {
            ranked.addAll(fallbackChunks(chunks, ranked, mode));
        }
        return ranked.subList(0, Math.min(6, ranked.size()));
    }

    private List<Chunk> fallbackChunks(List<Chunk> chunks, List<Chunk> ranked, String mode) {
        Set<String> existing = new HashSet<String>();
        for (Chunk chunk : ranked) {
            existing.add(chunk.type + "-" + chunk.id);
        }
        List<Chunk> fallback = new ArrayList<Chunk>();
        for (Chunk chunk : chunks) {
            if (existing.contains(chunk.type + "-" + chunk.id)) {
                continue;
            }
            if ("question".equals(mode) && !"question".equals(chunk.type)) {
                continue;
            }
            if ("course".equals(mode) && !"course".equals(chunk.type)) {
                continue;
            }
            chunk.score = 0.1D;
            fallback.add(chunk);
            if (fallback.size() >= 6) {
                break;
            }
        }
        return fallback;
    }

    private double scoreChunk(Chunk chunk, List<String> keywords, String mode) {
        double score = 0D;
        String title = StringUtils.defaultString(chunk.title);
        String content = StringUtils.defaultString(chunk.content);
        for (String keyword : keywords) {
            if (StringUtils.isBlank(keyword)) {
                continue;
            }
            if (title.contains(keyword)) {
                score += 4D;
            }
            if (content.contains(keyword)) {
                score += 2D;
            }
        }
        if ("course".equals(mode) && "course".equals(chunk.type)) {
            score += 3D;
        }
        if ("question".equals(mode) && "question".equals(chunk.type)) {
            score += 3D;
        }
        if ("study".equals(mode) && ("course".equals(chunk.type) || "intro".equals(chunk.type))) {
            score += 1D;
        }
        return score;
    }

    private List<Map<String, Object>> buildReferences(List<Chunk> retrieved) {
        List<Map<String, Object>> references = new ArrayList<Map<String, Object>>();
        for (Chunk chunk : retrieved) {
            Map<String, Object> item = new LinkedHashMap<String, Object>();
            item.put("type", chunk.type);
            item.put("id", chunk.id);
            item.put("title", chunk.title);
            item.put("subtitle", chunk.subtitle);
            item.put("summary", truncate(chunk.summary, 160));
            references.add(item);
        }
        return references;
    }

    private List<Map<String, Object>> buildRecommendedCourses(List<Chunk> retrieved, List<Chunk> chunks, String question) {
        LinkedHashMap<Long, Map<String, Object>> results = new LinkedHashMap<Long, Map<String, Object>>();
        for (Chunk chunk : retrieved) {
            if (!"course".equals(chunk.type)) {
                continue;
            }
            results.put(chunk.id, buildCourseItem(chunk, question));
            if (results.size() >= 3) {
                break;
            }
        }
        if (results.size() < 3) {
            for (Chunk chunk : chunks) {
                if (!"course".equals(chunk.type) || results.containsKey(chunk.id)) {
                    continue;
                }
                results.put(chunk.id, buildCourseItem(chunk, question));
                if (results.size() >= 3) {
                    break;
                }
            }
        }
        return new ArrayList<Map<String, Object>>(results.values());
    }

    private Map<String, Object> buildCourseItem(Chunk chunk, String question) {
        Map<String, Object> item = new LinkedHashMap<String, Object>();
        item.put("id", chunk.id);
        item.put("title", chunk.payload.get("title"));
        item.put("category", chunk.payload.get("category"));
        item.put("teacher", chunk.payload.get("teacher"));
        item.put("picture", chunk.payload.get("picture"));
        item.put("reason", "与“" + truncate(sanitize(question), 16) + "”相关，且资料中匹配度较高。");
        return item;
    }

    private List<Map<String, Object>> buildPracticeQuestions(List<Chunk> retrieved, List<Chunk> chunks) {
        LinkedHashMap<Long, Map<String, Object>> results = new LinkedHashMap<Long, Map<String, Object>>();
        for (Chunk chunk : retrieved) {
            if (!"question".equals(chunk.type)) {
                continue;
            }
            results.put(chunk.id, buildQuestionItem(chunk));
            if (results.size() >= 3) {
                break;
            }
        }
        if (results.size() < 3) {
            for (Chunk chunk : chunks) {
                if (!"question".equals(chunk.type) || results.containsKey(chunk.id)) {
                    continue;
                }
                results.put(chunk.id, buildQuestionItem(chunk));
                if (results.size() >= 3) {
                    break;
                }
            }
        }
        return new ArrayList<Map<String, Object>>(results.values());
    }

    private Map<String, Object> buildQuestionItem(Chunk chunk) {
        Map<String, Object> item = new LinkedHashMap<String, Object>();
        item.put("id", chunk.id);
        item.put("paperName", chunk.payload.get("paperName"));
        item.put("questionType", chunk.payload.get("questionType"));
        item.put("question", chunk.payload.get("question"));
        item.put("analysis", chunk.payload.get("analysis"));
        item.put("answer", chunk.payload.get("answer"));
        return item;
    }

    private String buildLocalAnswer(String question, List<Map<String, Object>> references,
                                    List<Map<String, Object>> courses, List<Map<String, Object>> questions) {
        StringBuilder builder = new StringBuilder();
        builder.append("已结合平台内课程、公告、系统简介和试题做检索。\n\n");
        builder.append("学习建议：\n");
        int refLimit = Math.min(3, references.size());
        for (int i = 0; i < refLimit; i++) {
            Map<String, Object> ref = references.get(i);
            builder.append(i + 1).append(". ");
            builder.append(ref.get("title")).append("：");
            builder.append(ref.get("summary")).append("\n");
        }
        if (!courses.isEmpty()) {
            builder.append("\n推荐课程：\n");
            for (int i = 0; i < courses.size(); i++) {
                Map<String, Object> course = courses.get(i);
                builder.append(i + 1).append(". ").append(course.get("title"))
                        .append("（").append(course.get("category")).append("）")
                        .append("，").append(course.get("reason")).append("\n");
            }
        }
        if (!questions.isEmpty()) {
            builder.append("\n推荐练习：\n");
            for (int i = 0; i < questions.size(); i++) {
                Map<String, Object> item = questions.get(i);
                builder.append(i + 1).append(". [").append(item.get("questionType")).append("] ")
                        .append(item.get("question")).append("\n");
            }
        }
        builder.append("\n如需更细的复习方案，可以继续追问，例如：")
                .append("“帮我围绕").append(truncate(sanitize(question), 12)).append("出3道练习题”。");
        return builder.toString();
    }

    private String callDeepSeek(String question, String mode, List<Map<String, Object>> references,
                                List<Map<String, Object>> courses, List<Map<String, Object>> questions) {
        if (StringUtils.isBlank(apiKey)) {
            return null;
        }
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(apiUrl);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000)
                    .setSocketTimeout(20000)
                    .build();
            post.setConfig(requestConfig);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Authorization", "Bearer " + apiKey);

            JSONObject body = new JSONObject();
            body.put("model", model);
            body.put("temperature", 0.3);

            JSONArray messages = new JSONArray();
            JSONObject systemMsg = new JSONObject();
            systemMsg.put("role", "system");
            systemMsg.put("content", "你是校园安全教育平台的AI学习助手。必须严格依据给定检索资料回答，不要编造课程、试题或政策。回答使用中文，结构清晰，优先给出学习建议、推荐课程和练习建议。");
            messages.add(systemMsg);

            JSONObject userMsg = new JSONObject();
            String context = "用户问题：" + question
                    + "\n模式：" + mode
                    + "\n\n检索资料：" + JSON.toJSONString(references)
                    + "\n\n推荐课程候选：" + JSON.toJSONString(courses)
                    + "\n\n推荐练习候选：" + JSON.toJSONString(questions)
                    + "\n\n请基于以上内容生成一段适合学生阅读的学习建议，如果资料不足要明确说明。";
            userMsg.put("role", "user");
            userMsg.put("content", context);
            messages.add(userMsg);

            body.put("messages", messages);
            post.setEntity(new StringEntity(body.toJSONString(), StandardCharsets.UTF_8));

            response = client.execute(post);
            String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            JSONObject root = JSON.parseObject(result);
            JSONArray choices = root.getJSONArray("choices");
            if (choices == null || choices.isEmpty()) {
                return null;
            }
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            return message == null ? null : message.getString("content");
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                client.close();
            } catch (Exception ignored) {
            }
        }
    }

    private String sanitize(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        return value.replaceAll("<[^>]+>", " ")
                .replaceAll("&nbsp;", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private String truncate(String value, int maxLength) {
        if (StringUtils.isBlank(value) || value.length() <= maxLength) {
            return StringUtils.defaultString(value);
        }
        return value.substring(0, maxLength) + "...";
    }

    private String join(String... parts) {
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            if (StringUtils.isNotBlank(part)) {
                builder.append(part).append(" ");
            }
        }
        return builder.toString().trim();
    }

    private String questionTypeText(Long type) {
        if (type == null) {
            return "试题";
        }
        switch (type.intValue()) {
            case 0:
                return "单选题";
            case 1:
                return "多选题";
            case 2:
                return "判断题";
            case 3:
                return "填空题";
            case 4:
                return "主观题";
            default:
                return "试题";
        }
    }

    private static class Chunk {
        private String type;
        private Long id;
        private String title;
        private String subtitle;
        private String summary;
        private String content;
        private double score;
        private Map<String, Object> payload;
    }
}
