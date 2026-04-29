<template>
	<div :style='{"width":"100%","padding":"20px 10% 40px","margin":"10px auto","position":"relative","background":"none"}'>
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui14"></span>
				<span class="text">返回</span>
			</el-button>
		</div>
		<div class="section-title" :style='{"padding":"15px 10% 0 10%","margin":"0 0 20px","color":"#f4fbff","textAlign":"center","background":"none","display":"block","width":"100%","lineHeight":"30px","fontSize":"32px","fontWeight":"700","textShadow":"0 4px 18px rgba(0,0,0,.22)"}'>考试记录</div>
		<el-form :inline="true" :model="formSearch" class="list-form-pv">
			<el-form-item class="search-item">
				<el-input v-model="papername" placeholder="试卷名称"></el-input>
			</el-form-item>
			<div class="search-btn-item">
				<el-button class="searchBtn" type="primary" @click="getExamList(1)">
					<span class="icon iconfont icon-fangdajing07"></span>
					查询
				</el-button>
			</div>
		</el-form>
		<el-table :data="tableData" style="width: 100%">
			<el-table-column label="考试编号" prop="examno"> </el-table-column>
			<el-table-column label="试卷名称" prop="papername"> </el-table-column>
			<el-table-column label="考试得分">
				<template slot-scope="scope">
					<el-tag v-if="scope.row.myscore==0&&scope.row.ismark==0" type="danger">{{scope.row.myscore}}</el-tag>
					<el-tag v-else-if="scope.row.myscore>0&&scope.row.ismark==0" type="success">{{scope.row.myscore}}</el-tag>
					<el-tag v-else-if="scope.row.ismark>0" type="warning">批阅中</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="准确率">
				<template slot-scope="scope">
					<el-tag type="success" v-if="scope.row.ismark==0">{{(scope.row.accuracy * 100).toFixed(0)}}%</el-tag>
					<el-tag type="warning" v-if="scope.row.ismark>0">/</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="错误率">
				<template slot-scope="scope">
					<el-tag type="danger" v-if="scope.row.ismark==0">{{((1 - Number(scope.row.accuracy)) * 100).toFixed(0)}}%</el-tag>
					<el-tag type="warning" v-if="scope.row.ismark>0">/</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="150">
				<template slot-scope="scope">
					<el-button type="danger" size="mini" @click="handleView(scope.$index, scope.row)">查看</el-button>
					<el-button v-if="isAuth('examrecord','批卷')&&scope.row.ismark>0" type="primary" size="mini" @click="gradeClick(scope.row)">
						批卷
					</el-button>
				</template>
			</el-table-column>
		</el-table>
	
		<el-pagination
			background
			id="pagination" class="pagination"
			:pager-count="7"
			:page-size="pageSize"
			prev-text="<"
			next-text=">"
			:hide-on-single-page="true"
			:layout='["total","prev","pager","next","sizes","jumper"].join()'
			:total="total"
			:style='{"padding":"0","margin":"20px auto","whiteSpace":"nowrap","color":"#333","textAlign":"center","width":"100%","fontSize":"16px","fontWeight":"500","order":"50"}'
			@current-change="curChange"
			@size-change="sizeChange"
			@prev-click="prevClick"
			@next-click="nextClick"
			></el-pagination>
		<el-dialog title="批卷" :visible.sync="gradeVisible" fullscreen>
			<el-card v-for="(item,index) in gradeList" :key="index" style="width: 90%;margin: 10px auto">
				<div style="padding: 20px;box-sizing:border-box;border-bottom:1px solid #eee">
					{{index + 1}}、<span class="ql-snow ql-editor" v-html="item.questionname"></span> （ {{item.score}} ） 					<el-tag type="success" v-if="item.type==0">单选题</el-tag>
					<el-tag type="warning" v-if="item.type==1">多选题</el-tag>
					<el-tag type="info" v-if="item.type==2">判断题</el-tag>
					<el-tag type="primary" v-if="item.type==3">填空题</el-tag>
					<el-tag type="danger" v-if="item.type==4">主观题</el-tag>
				</div>
				<div style="padding: 10px;box-sizing:border-box">
					考生答案：{{item.myanswer}}
				</div>
				<div style="padding: 10px;box-sizing:border-box" v-if="item.type!=4">
					正确答案：{{item.answer}}
				</div>
				<div style="padding: 20px;box-sizing:border-box">
					解析：{{item.analysis}}
				</div>
				<div style="padding: 20px;box-sizing:border-box;display:flex;align-items:center" v-if="item.type==4">
					评分：<el-input-number v-model="item.myscore" :min="0" :max="item.score"></el-input-number>
				</div>
			</el-card>
			<span slot="footer" class="dialog-footer">
				<el-button @click="gradeVisible=false">取 消</el-button>
				<el-button type="primary" @click="gradeSave">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				layouts: '',
				tableData: [],
				total: 1,
				pageSize: 10,
				totalPage: 1,
				papername: '',
				formSearch: {},
				gradeList: [],
				gradeVisible: false
			}
		},
		created() {
			this.getExamList(1);
		},
		methods: {
			backClick() {
				this.$router.push('/index/center')
			},
			getExamList(page) {
				let params = {page, limit: this.pageSize, userid: Number(localStorage.getItem('frontUserid'))}
				if(this.papername&&this.papername!='') {
					params.papername = `%${this.papername}%`
				}
				this.$http.get('examrecord/groupby', {params: params}).then(res => {
					if (res.data.code == 0) {
						this.tableData = res.data.data.list;
						this.total = res.data.data.total;
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
					}
				});
			},
			curChange(page) {
				this.getExamList(page);
			},
			prevClick(page) {
				this.getExamList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getExamList(1);
			},
			nextClick(page) {
				this.getExamList(page);
			},
			handleView(index, row) {
				this.$router.push({path: '/index/examRecord/1', query: {paperid: row.paperid,examno: row.examno}})
			},
			// 批卷
			gradeClick(row) {
				this.$http.get('examrecord/page',{params:{page:1,limit:100,paperid:row.paperid,userid:row.userid,examno: row.examno}}).then(res=>{
					if (res.data.code == 0) {
						for(let x in res.data.data.list){
							if(res.data.data.list[x].type==4){
								res.data.data.list[x].myscore = 0
							}
							res.data.data.list[x].questionname = res.data.data.list[x].questionname.replace(/img src/gi,"img style=\"width:100%;\" src");
						}
						this.gradeList = res.data.data.list
						this.gradeVisible = true
					}
				})
			},
			async gradeSave(){
				let score = 0
				for(let i in this.gradeList){
					score += Number(this.gradeList[i].myscore)
					await this.updaterecord(this.gradeList[i])
				}
				this.$http.post('examstatistics/add', {
					username: this.gradeList[0].username,
					paperid: this.gradeList[0].paperid,
					papername: this.gradeList[0].papername,
					examno: this.gradeList[0].examno,
					totalscore: score,
					userid: this.gradeList[0].userid,
				}).then(rs=>{})
				this.$message({
					message: "批卷成功",
					type: "success",
					duration: 1500,
					onClose: () => {
						this.getExamList(1)
						this.gradeVisible = false
					}
				});
			},
			updaterecord(item) {
				item.ismark = 0
				return this.$http.post('examrecord/update',item)
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.section-title {
		color: #163958 !important;
		opacity: 1 !important;
		text-shadow: 0 1px 0 rgba(255,255,255,.25);
	}

	.el-table ::v-deep th,
	.el-table ::v-deep td,
	.list-form-pv {
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
