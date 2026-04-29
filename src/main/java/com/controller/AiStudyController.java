package com.controller;

import com.annotation.IgnoreAuth;
import com.service.AiStudyService;
import com.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/aistudy")
public class AiStudyController {

    @Autowired
    private AiStudyService aiStudyService;

    @IgnoreAuth
    @RequestMapping("/chat")
    public R chat(@RequestBody Map<String, Object> params) {
        String question = params.get("question") == null ? "" : String.valueOf(params.get("question"));
        String mode = params.get("mode") == null ? "study" : String.valueOf(params.get("mode"));
        if (StringUtils.isBlank(question)) {
            return R.error("请输入学习问题");
        }
        return R.ok().put("data", aiStudyService.chat(question, mode));
    }
}
