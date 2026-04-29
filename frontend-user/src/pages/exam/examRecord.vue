<template>
	<div class="exam-record-page" :style='{"width":"100%","padding":"20px 10% 40px","margin":"10px auto","position":"relative","background":"none"}'>
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui14"></span>
				<span class="text">返回</span>
			</el-button>
		</div>
		<div class="section-title title-text" :style='{"padding":"15px 10% 0 10%","margin":"0 0 20px","color":"#f4fbff","textAlign":"center","background":"none","display":"block","width":"100%","lineHeight":"30px","fontSize":"32px","fontWeight":"700","textShadow":"0 4px 18px rgba(0,0,0,.22)"}'>{{title}}</div>
		<el-form :inline="true" :model="formSearch" class="list-form-pv">
			<el-form-item class="search-item">
				<el-input v-model="questionname" placeholder="试题名称"></el-input>
			</el-form-item>
			<div class="search-btn-item">
				<el-button class="searchBtn" type="primary" @click="getExamRecord(1)">
					<span class="icon iconfont icon-fangdajing07"></span>
					查询
				</el-button>
			</div>
		</el-form>
		<el-table
			:data="tableData"
			class="record-table"
			style="width: 100%">
			<el-table-column label="考试编号" prop="examno"> </el-table-column>
			<el-table-column
				label="试卷名称"
				prop="papername">
			</el-table-column>
			<el-table-column
				label="试题名称"
				prop="questionname">
				<template slot-scope="scope">
					<div class="ql-snow ql-editor" v-html="scope.row.questionname"></div>
				</template>
			</el-table-column>
			<el-table-column
				prop="type"
				label="试题名称类型"
			>
				<template slot-scope="scope">
					<el-tag type="success" v-if="scope.row.type==0">单选题</el-tag>
					<el-tag type="warning" v-if="scope.row.type==1">多选题</el-tag>
					<el-tag type="info" v-if="scope.row.type==2">判断题</el-tag>
					<el-tag type="primary" v-if="scope.row.type==3">填空题</el-tag>
					<el-tag type="danger" v-if="scope.row.type==4">主观题</el-tag>
				</template>
			</el-table-column>
			<el-table-column
				label="分值">
				<template slot-scope="scope">
					{{ scope.row.score }}分
				</template>
			</el-table-column>
			<el-table-column
				label="正确答案"
				prop="right">
			</el-table-column>
			<el-table-column
				label="考生答案"
				prop="myanswer">
			</el-table-column>
			<el-table-column
				label="考试得分">
				<template slot-scope="scope">
					{{ scope.row.myscore }}分
				</template>
			</el-table-column>
		</el-table>
		<el-pagination
			background
			id="pagination" class="pagination"
			:pager-count="7"
			:page-size="pageSize"
			:page-sizes="pageSizes"
			prev-text="<"
			next-text=">"
			:hide-on-single-page="true"
			:layout='["total","prev","pager","next","sizes","jumper"].join()'
			:total="total"
			:style='{"padding":"0","margin":"20px auto","whiteSpace":"nowrap","color":"#333","textAlign":"center","width":"100%","fontSize":"16px","fontWeight":"500","order":"50"}'
			@current-change="curChange"
			@prev-click="prevClick"
			@size-change="sizeChange"
			@next-click="nextClick"
		></el-pagination>
	
	</div>
</template>

<script>
	export default {
		data() {
			return {
				layouts: '',
				title: '考试记录',
				tableData: [],
				total: 1,
				pageSize: 10,
				pageSizes: [],
				totalPage: 1,
				formSearch: {},
				questionname: ''
			}
		},
		created() {
			this.getExamRecord(1);
		},
		methods: {
			backClick() {
				this.$router.push('/index/center')
			},
			getExamRecord(page) {
				let params = {
					page, limit: this.pageSize, paperid: this.$route.query.paperid, userid: Number(localStorage.getItem('frontUserid'))
				}
				if(this.$route.query.examno) {
					params.examno = this.$route.query.examno
				}
				if(this.questionname&&this.questionname!='') {
					params.questionname = `%${this.questionname}%`
				}
				let otherParams = {};
				if (this.$route.params.type == 0) {
					this.title = '错题本';
					otherParams.myscore = 0;
					otherParams.ismark = 1
				}
				this.$http.get('examrecord/list', {params: Object.assign(params, otherParams)}).then(res => {
					if (res.data.code == 0) {
						let arr = []
						for(let x in res.data.data.list){
							res.data.data.list[x].questionname = res.data.data.list[x].questionname.replace(/img src/gi,"img style=\"width:100%;\" src");
							if(res.data.data.list[x].type==0||res.data.data.list[x].type==2){
								arr = JSON.parse(res.data.data.list[x].options)
								for(let i in arr){
									if(res.data.data.list[x].answer == arr[i].code){
										res.data.data.list[x].right = arr[i].text
									}
								}
							}else if(res.data.data.list[x].type==1){
								arr = JSON.parse(res.data.data.list[x].options)
								res.data.data.list[x].answer.split(',').forEach(item=>{
									for(let i in arr){
										if (item == arr[i].code) {
											if (res.data.data.list[x].right) {
												res.data.data.list[x].right += ','
												res.data.data.list[x].right = res.data.data.list[x].right + arr[i].text
											}else{
												res.data.data.list[x].right = arr[i].text
											}
										}
									}
								})
							}else if(res.data.data.list[x].type==3){
								res.data.data.list[x].right = res.data.data.list[x].answer
							}else if(res.data.data.list[x].type==4){
								res.data.data.list[x].right = '略'
							}
						}
						this.tableData = res.data.data.list;
						this.total = res.data.data.total;
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
						if(this.pageSizes.length==0){
							this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
						}
					}
				});
			},
			curChange(page) {
				this.getExamRecord(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getExamRecord(1);
			},
			prevClick(page) {
				this.getExamRecord(page);
			},
			nextClick(page) {
				this.getExamRecord(page);
			},
			handleView(index, row) {
				this.$router.push({path: '/index/examRecord', query: {id: row.id}})
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.section-title,
	.title-text {
		color: #163958 !important;
		opacity: 1 !important;
		text-shadow: 0 1px 0 rgba(255,255,255,.25);
	}

	.record-table ::v-deep th,
	.record-table ::v-deep td {
		color: #173958 !important;
		opacity: 1 !important;
	}

	.section {
		width: 900px;
		margin: 0 auto;
	}
	.list-form-pv {
				padding: 10px;
				margin: 10px 0;
				background: none;
				display: flex;
				width: 100%;
				justify-content: center;
				align-items: center;
				flex-wrap: wrap;
				gap: 14px;
				height: auto;
				.search-item {
						margin: 0 0px;
						::v-deep .el-form-item__content {
								display: flex;
								align-items: center;
							}
			.el-input {
								width: 100%;
							}
			.el-input ::v-deep .el-input__inner {
								border: 1px solid #999;
								border-radius: 0px 0 0 0;
								padding: 0 10px;
								margin: 0;
								outline: none;
								color: #666;
								width: 500px;
								font-size: 16px;
								line-height: 42px;
								height: 42px;
							}
		}
		.search-btn-item {
						display: flex;
						align-items: center;
						.searchBtn {
								cursor: pointer;
								border: 0;
								border-radius: 0px 0 0 0;
								padding: 0px 15px;
								margin: 0 10px 0 0;
								outline: none;
								background: #263d44;
								width: auto;
								font-size: 16px;
								line-height: 42px;
								height: 42px;
								.icon {
										margin: 0 5px 0 0;
										color: #fff;
									}
			}
			
			.searchBtn:hover {
								opacity: 0.8;
							}
			
			.pubBtn {
								cursor: pointer;
								border: 0;
								border-radius: 0px 0 0 0;
								padding: 0px 15px;
								margin: 0 10px 0 0;
								outline: none;
								color: #fff;
								background: #263d44;
								width: auto;
								font-size: 16px;
								line-height: 42px;
								height: 42px;
								.icon {
										margin: 0 5px 0 0;
										color: #fff;
										font-size: 16px;
									}
			}
			
			.pubBtn:hover {
								opacity: 0.8;
							}
		}
	}
</style>
