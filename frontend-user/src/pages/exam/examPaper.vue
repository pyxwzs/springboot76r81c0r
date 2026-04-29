<template>
	<div class="exam-paper-page">
		<div class="section-title">考试选择</div>
		<el-form :inline="true" :model="formSearch" class="center-form-pv paper-search-form">
			<el-form-item>
				<div class="lable" v-if="true">试卷名称：</div>
				<el-input v-model="formSearch.name" placeholder="试卷名称" @keydown.enter.native="getExamList(1)" clearable></el-input>
			</el-form-item>
			<el-button class="query-btn" type="primary" @click="getExamList(1)"><i class="el-icon-search"></i>查询</el-button>
		</el-form>
		<el-table
			:data="tableData"
			class="paper-table">
			<el-table-column
				label="试卷名称"
				prop="name">
			</el-table-column>
			<el-table-column
				label="考试时长">
				<template slot-scope="scope">
					{{ scope.row.time }}分钟
				</template>
			</el-table-column>
			<el-table-column
				label="创建时间"
				prop="addtime">
			</el-table-column>
			<el-table-column label="操作" width="150">
				<template slot-scope="scope">
					<el-button
					type="success"
					size="mini"
					@click="handleExam(scope.$index, scope.row)">考试</el-button>
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
				tableData: [],
				total: 1,
				pageSize: 10,
				pageSizes: [],
				totalPage: 1,
				formSearch: {}
			}
		},
		created() {
			this.getExamList(1);
		},
		methods: {
			getExamList(page) {
				let params = {page, limit: this.pageSize, sort: 'addtime', order: 'desc'}
				if(this.formSearch.name){
					params['name'] = `%${this.formSearch.name}%`
				}
				let user = JSON.parse(localStorage.getItem('sessionForm'))
				this.$http.get('exampaper/list', {params: params}).then(res => {
					if (res.data.code == 0) {
						this.tableData = (res.data.data.list || []).filter(item => ['1', 1, true, '启用'].includes(item.status));
						this.total = this.tableData.length;
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = this.total > 0 ? 1 : 0;
						if(this.pageSizes.length==0){
							this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
						}
					}
				});
			},
		  sizeChange(size){
			this.pageSize = size
			this.getExamList(1);
		  },
			curChange(page) {
				this.getExamList(page);
			},
			prevClick(page) {
				this.getExamList(page);
			},
			nextClick(page) {
				this.getExamList(page);
			},
			async handleExam(index, row) {
				let num = 0
				if(row.examnum>0) {
					await this.$http.get('examrecord/groupby',{params: {
						paperid: row.id,
						userid: Number(localStorage.getItem('frontUserid'))
					}}).then(rs=>{
						num = rs.data.data.list.length
					})
					if(num>=row.examnum) {
						this.$message.error('超过当前考试最大次数！')
						return false
					}
				}
				this.$router.push({path: '/exam', query: {id: row.id, time: row.time}})
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.exam-paper-page {
		width: 100%;
		padding: 24px 8% 42px;
		margin: 0 auto;
		position: relative;
		background: none;
	}

	.section-title {
		padding: 0;
		margin: 0 0 24px;
		color: #f4fbff;
		text-align: center;
		display: block;
		width: 100%;
		line-height: 1.2;
		font-size: 40px;
		font-weight: 700;
		opacity: 1;
		text-shadow: 0 4px 18px rgba(0,0,0,.22);
	}

	.center-form-pv {
		padding: 18px 22px;
		margin: 0 auto 24px;
		display: flex;
		width: 100%;
		justify-content: center;
		align-items: center;
		flex-wrap: wrap;
		gap: 12px;
		background: rgba(255, 255, 255, .82);
	}

	.center-form-pv .lable {
		margin-right: 8px;
		color: #23405a;
		font-weight: 600;
	}

	.center-form-pv .el-input {
		width: 280px;
	}

	.query-btn i {
		margin-right: 4px;
	}

	.paper-table {
		width: 100%;
		background: rgba(255, 255, 255, .82);
	}

	.paper-table ::v-deep th,
	.paper-table ::v-deep td,
	.paper-search-form .lable {
		opacity: 1 !important;
		color: #173958 !important;
	}

	.exam-paper-page {
		color: #173958 !important;
	}
</style>
