<template>
	<div class="ai-study-page">
		<div class="hero">
			<div class="hero-copy">
				<div class="eyebrow">DEEPSEEK + RAG</div>
				<h1>AI辅助学习</h1>
				<p>围绕课程内容、平台题库和安全教育资料做检索增强回答，支持课程推荐、试题推荐和复习问答。</p>
			</div>
			<div class="hero-actions">
				<el-button type="primary" @click="usePrompt('请根据平台资料，为我制定一份校园消防安全复习计划')">消防复习</el-button>
				<el-button @click="usePrompt('推荐3门适合新生入学学习的校园安全课程，并说明原因')">推荐课程</el-button>
				<el-button @click="usePrompt('围绕反诈和网络安全，给我推荐3道练习题并讲解')">推荐练题</el-button>
			</div>
		</div>

		<div class="workspace">
			<div class="ask-card">
				<div class="card-title">提问</div>
				<el-input
					type="textarea"
					:rows="6"
					v-model="question"
					placeholder="例如：帮我梳理大学生网络安全与防范这门课的重点，并推荐3道相关练习题">
				</el-input>
				<div class="submit-row">
					<el-button type="primary" :loading="loading" @click="submitQuestion">开始分析</el-button>
					<el-button @click="resetAnswer">清空结果</el-button>
				</div>
			</div>

			<div class="answer-card">
				<div class="card-title">AI回答</div>
				<div v-if="answer" class="answer-text">{{ answer }}</div>
				<div v-else class="empty-text">提交问题后，这里会输出学习建议、推荐课程和练习题。</div>
				<div v-if="usedModel !== null" class="status-row">
					<el-tag :type="usedModel ? 'success' : 'warning'">
						{{ usedModel ? 'DeepSeek 已参与生成' : '当前为本地RAG回退回答' }}
					</el-tag>
				</div>
			</div>
		</div>

		<div class="panel-grid">
			<div class="panel">
				<div class="panel-title">推荐课程</div>
				<div v-if="recommendedCourses.length" class="course-list">
					<div class="course-item" v-for="item in recommendedCourses" :key="item.id" @click="toCourse(item.id)">
						<div class="course-name">{{ item.title }}</div>
						<div class="course-meta">{{ item.category }} / {{ item.teacher }}</div>
						<div class="course-reason">{{ item.reason }}</div>
					</div>
				</div>
				<div v-else class="empty-text">暂无推荐课程</div>
			</div>

			<div class="panel">
				<div class="panel-title">推荐练题</div>
				<div v-if="practiceQuestions.length" class="question-list">
					<div class="question-item" v-for="item in practiceQuestions" :key="item.id">
						<div class="question-top">
							<el-tag size="mini">{{ item.questionType }}</el-tag>
							<span class="paper-name">{{ item.paperName }}</span>
						</div>
						<div class="question-name">{{ item.question }}</div>
						<div class="question-analysis" v-if="item.analysis">解析：{{ item.analysis }}</div>
					</div>
				</div>
				<div v-else class="empty-text">暂无推荐试题</div>
			</div>
		</div>

		<div class="panel refs-panel">
			<div class="panel-title">检索到的学习资料</div>
			<div v-if="references.length" class="ref-list">
				<div class="ref-item" v-for="(item, index) in references" :key="index">
					<div class="ref-top">
						<el-tag size="mini" type="info">{{ typeLabel(item.type) }}</el-tag>
						<span class="ref-title">{{ item.title }}</span>
					</div>
					<div class="ref-subtitle" v-if="item.subtitle">{{ item.subtitle }}</div>
					<div class="ref-summary">{{ item.summary }}</div>
				</div>
			</div>
			<div v-else class="empty-text">暂无检索资料</div>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			question: '',
			loading: false,
			answer: '',
			usedModel: null,
			references: [],
			recommendedCourses: [],
			practiceQuestions: [],
		}
	},
	methods: {
		usePrompt(text) {
			this.question = text
		},
		typeLabel(type) {
			const map = {
				course: '课程',
				question: '试题',
				news: '公告',
				intro: '简介',
			}
			return map[type] || '资料'
		},
		toCourse(id) {
			this.$router.push({ path: '/index/kechengxinxiDetail', query: { id } })
		},
		resetAnswer() {
			this.answer = ''
			this.usedModel = null
			this.references = []
			this.recommendedCourses = []
			this.practiceQuestions = []
		},
		submitQuestion() {
			if (!this.question || !this.question.trim()) {
				this.$message.warning('请输入问题')
				return
			}
			this.loading = true
			this.$http.post('aistudy/chat', {
				question: this.question,
			}).then((res) => {
				if (res.data.code === 0) {
					const data = res.data.data || {}
					this.answer = data.answer || ''
					this.usedModel = !!data.usedModel
					this.references = data.references || []
					this.recommendedCourses = data.recommendedCourses || []
					this.practiceQuestions = data.practiceQuestions || []
				} else {
					this.$message.error(res.data.msg || 'AI分析失败')
				}
			}).finally(() => {
				this.loading = false
			})
		},
	},
}
</script>

<style scoped lang="scss">
.ai-study-page {
	padding: 30px 10%;
	background:
		radial-gradient(circle at top left, rgba(74, 162, 255, .14), transparent 28%),
		radial-gradient(circle at top right, rgba(0, 199, 140, .12), transparent 22%),
		linear-gradient(180deg, #f5f8fb 0%, #eef3f7 100%);
	min-height: 100vh;
}

.hero {
	padding: 32px;
	border-radius: 24px;
	background: linear-gradient(135deg, #0d2b45 0%, #123f5b 48%, #1d6a73 100%);
	color: #fff;
	box-shadow: 0 22px 50px rgba(10, 37, 64, .18);
}

.eyebrow {
	font-size: 12px;
	letter-spacing: 2px;
	color: rgba(255,255,255,.68);
}

.hero h1 {
	margin: 10px 0 12px;
	font-size: 40px;
	line-height: 1.1;
}

.hero p {
	max-width: 780px;
	line-height: 1.8;
	color: rgba(255,255,255,.86);
}

.hero-actions {
	margin-top: 18px;
	display: flex;
	flex-wrap: wrap;
	gap: 12px;
}

.hero-actions .el-button {
	min-width: 132px;
}

.workspace {
	margin-top: 24px;
	display: grid;
	grid-template-columns: 1.05fr .95fr;
	gap: 20px;
	align-items: stretch;
}

.ask-card,
.answer-card,
.panel {
	padding: 24px;
	border-radius: 20px;
	background: rgba(255,255,255,.92);
	box-shadow: 0 16px 36px rgba(16, 39, 58, .08);
	backdrop-filter: blur(8px);
}

.ask-card,
.answer-card {
	height: 520px;
	display: flex;
	flex-direction: column;
}

.card-title,
.panel-title {
	font-size: 22px;
	font-weight: 600;
	color: #163041;
}

.ask-card ::v-deep .el-textarea {
	flex: 1;
	display: flex;
}

.ask-card ::v-deep .el-textarea__inner {
	height: 100%;
	min-height: 320px !important;
	resize: none;
}

.submit-row {
	margin-top: 16px;
	display: flex;
	gap: 12px;
	flex-shrink: 0;
}

.answer-text {
	margin-top: 16px;
	white-space: pre-wrap;
	line-height: 1.9;
	color: #1f3442;
	overflow-y: auto;
	padding-right: 8px;
	flex: 1;
	min-height: 0;
}

.status-row {
	margin-top: 16px;
	flex-shrink: 0;
}

.empty-text {
	margin-top: 18px;
	color: #627c90;
	line-height: 1.8;
}

.panel-grid {
	margin-top: 20px;
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 20px;
}

.course-list,
.question-list,
.ref-list {
	margin-top: 16px;
	display: grid;
	gap: 12px;
}

.course-item,
.question-item,
.ref-item {
	padding: 16px;
	border-radius: 16px;
	background: #f4f8fb;
	border: 1px solid #deebf2;
}

.course-item {
	cursor: pointer;
	transition: transform .18s ease, box-shadow .18s ease;
}

.course-item:hover {
	transform: translateY(-2px);
	box-shadow: 0 10px 20px rgba(16, 53, 87, .08);
}

.course-name,
.ref-title {
	font-size: 17px;
	font-weight: 600;
	color: #163041;
}

.course-meta,
.ref-subtitle,
.paper-name {
	margin-top: 6px;
	font-size: 13px;
	color: #5b7384;
}

.course-reason,
.question-analysis,
.ref-summary,
.question-name {
	margin-top: 10px;
	line-height: 1.8;
	color: #274355;
}

.question-top,
.ref-top {
	display: flex;
	align-items: center;
	gap: 10px;
}

.refs-panel {
	margin-top: 20px;
}

.empty-text {
	margin-top: 16px;
	color: #8093a0;
	line-height: 1.8;
	flex: 1;
}

@media (max-width: 900px) {
	.ai-study-page {
		padding: 20px 16px 40px;
	}

	.workspace,
	.panel-grid {
		grid-template-columns: 1fr;
	}

	.ask-card,
	.answer-card {
		height: auto;
	}

	.hero h1 {
		font-size: 30px;
	}
}
</style>
