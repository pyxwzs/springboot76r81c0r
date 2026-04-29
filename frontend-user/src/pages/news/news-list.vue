<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'：'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><a>{{item.name}}</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
	
		<div class="news-preview-pv">
			<el-form :inline="true" :model="formSearch" class="list-form-pv">
				<el-form-item class="search-item">
					<el-input v-model="title" placeholder="标题"></el-input>
				</el-form-item>
				<el-button class="search-btn" type="primary" @click="getNewsList(1)">
					<span class="icon iconfont icon-chakan14"></span>
					搜索
				</el-button>
			</el-form>
			
			<!-- category -->
			<!-- 样式六 -->
			<div class="list6 index-pv1">
				<div v-for="item in newsList" :key="item.id" @click="toNewsDetail(item)" class="list-item animation-box">
					<img :src="baseUrl + (item.picture?item.picture.split(',')[0]:'')" >
					<div class="infoBox">
						<div class="name">{{item.title}}</div>
						<div class="desc">{{item.introduction}}</div>
						<div class="time_item">
							<span class="icon iconfont icon-shijian21"></span>
							<span class="label">发布时间：</span>
							<span class="text">{{item.addtime.split(' ')[0]}}</span>
						</div>
						<div class="publisher_item">
							<span class="icon iconfont icon-geren16"></span>
							<span class="label">发布人：</span>
							<span class="text">{{item.name}}</span>
						</div>
						<div class="tags">详情>></div>
					</div>
				</div>
			</div>
		
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
				@current-change="curChange"
				@size-change="sizeChange"
				@prev-click="prevClick"
				@next-click="nextClick"
				></el-pagination>
		</div>
	</div>
</template>

<script>
	export default {
		//数据集合
		data() {
			return {
				baseUrl: this.$config.baseUrl,
				breadcrumbItem: [
				  {
					name: '公告'
				  }
				],
				newsList: [],
				total: 1,
				pageSize: 10,
				pageSizes: [],
				totalPage: 1,
				layouts: '',
				title: '',
			}
		},
		created() {
			this.getNewsList(1);
		},
		watch:{
			$route(newValue){
				this.getNewsList(1);
			}
		},
		//方法集合
		methods: {
			getNewsList(page) {
				let params = {page, limit: this.pageSize,sort:'addtime',order:'desc'};
				let searchWhere = {};
				if(this.title != '') searchWhere.title = '%' + this.title + '%';
				this.$http.get('news/list', {params: Object.assign(params, searchWhere)}).then(res => {
					if (res.data.code == 0) {
						this.newsList = res.data.data.list;
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
				this.getNewsList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getNewsList(1);
			},
			prevClick(page) {
				this.getNewsList(page);
			},
			nextClick(page) {
				this.getNewsList(page);
			},
			toNewsDetail(item) {
				this.$router.push({path: '/index/newsDetail', query: {id: item.id}});
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.news-preview-pv {
		padding-top: 20px;
		padding-bottom: 40px;

		.list-form-pv {
			padding: 18px !important;
			display: flex !important;
			justify-content: center;
			align-items: center;
			gap: 12px;
		}

		.list-form-pv .search-item {
			margin: 0 !important;
			flex: 0 1 620px;
		}

		.list-form-pv .search-item ::v-deep .el-input__inner {
			min-width: 0 !important;
			width: 100% !important;
			border-radius: 16px !important;
		}

		.list6 {
			margin: 0 !important;
			width: 100% !important;
			display: grid !important;
			grid-template-columns: repeat(2, minmax(0, 1fr));
			gap: 24px;
		}

		.list6 .list-item {
			width: 100% !important;
			margin: 0 !important;
			padding: 18px !important;
			display: grid !important;
			grid-template-columns: 220px minmax(0, 1fr);
			gap: 20px;
			align-items: stretch;
		}

		.list6 .list-item img {
			width: 100% !important;
			height: 220px !important;
			border-radius: 18px;
			object-fit: contain;
			object-position: center;
			background: #f2f3f5;
		}

		.list6 .list-item .infoBox {
			padding: 6px 0 !important;
			display: flex;
			flex-direction: column;
			justify-content: center;
		}

		.list6 .list-item .name {
			white-space: normal !important;
			line-height: 1.5 !important;
			margin-bottom: 10px;
		}

		.list6 .list-item .desc {
			white-space: normal !important;
			-webkit-line-clamp: 3;
			display: -webkit-box;
			-webkit-box-orient: vertical;
		}

		.list6 .list-item .time_item,
		.list6 .list-item .publisher_item {
			margin-top: 8px;
		}

		.list6 .list-item .tags {
			margin-top: 16px;
			align-self: flex-start;
			padding: 6px 14px;
			border-radius: 999px;
			background: rgba(82, 208, 255, .12);
			color: #11405e;
			font-weight: 600;
		}
	}

	@media (max-width: 1280px) {
		.news-preview-pv .list6 {
			grid-template-columns: 1fr;
		}
	}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
	.news-preview-pv {
				padding: 0 10%;
				margin: 0px auto;
				align-content: flex-start;
				background: none;
				display: flex;
				width: 100%;
				align-items: flex-start;
				position: relative;
				flex-wrap: wrap;
				.list-form-pv {
						padding: 10px;
						margin: 0 0 20px;
						background: none;
						display: flex;
						width: 100%;
						justify-content: center;
						align-items: center;
						flex-wrap: wrap;
						height: auto;
						.search-item {
								margin: 0 0px;
								.el-input {
										width: 100%;
									}
				.el-input ::v-deep .el-input__inner {
										border: 1px solid #707070;
										border-radius: 30px 0 0 30px;
										padding: 0 20px;
										margin: 0;
										outline: none;
										color: #333;
										width: 100%;
										font-size: 16px;
										line-height: 42px;
										min-width: 600px;
										height: 42px;
									}
			}
			.search-btn {
								cursor: pointer;
								border: 0;
								padding: 0px 15px;
								margin: 0 10px 0 0;
								color: #fff;
								font-size: 16px;
								line-height: 42px;
								border-radius: 0 30px 30px 0;
								outline: none;
								background: #253b43;
								width: auto;
								min-width: 100px;
								height: 42px;
								.icon {
										margin: 0 3px 0 0;
										color: #000;
										display: none;
										font-size: 16px;
									}
			}
		}
		.category-list {
						border: 0px solid #eee;
						padding: 40px 0 20px;
						margin: 0px 0 0 0;
						background: url(http://codegen.caihongy.cn/20251211/cb866379f4ed4512a0303bf5909bfada.png) no-repeat left top,url(http://codegen.caihongy.cn/20251211/95c1639d44234c21b91bb9d1fbc79288.png) no-repeat right top,url(http://codegen.caihongy.cn/20251211/2a2bbc3dea684d20b402fe090076a772.png) repeat-x center top;
						display: flex;
						width: 100%;
						justify-content: center;
						flex-wrap: wrap;
						height: auto;
						order: 3;
						.item {
								cursor: pointer;
								border: 0;
								padding: 0 0px;
								margin: 0 30px 0 0;
								color: #333;
								font-weight: 500;
								display: flex;
								font-size: 18px;
								border-color: #fff;
								line-height: 60px;
								border-radius: 20px 0 0 0;
								background: none;
								width: auto;
								border-width: 0 0 4px;
								align-items: center;
								border-style: solid;
							}
			
			.item:hover {
								border-radius: 0px 0 0 0;
								padding: 0 0px;
								margin: 0 30px 0 0;
								color: #000;
								background: none;
								font-weight: 500;
								width: auto;
								font-size: 18px;
								border-color: #253b43;
								line-height: 60px;
							}
			
			.item.active {
								border-radius: 0px 0 0 0;
								padding: 0 0px;
								margin: 0 30px 0 0;
								color: #000;
								background: none;
								font-weight: 500;
								width: auto;
								font-size: 18px;
								border-color: #253b43;
								border-width: 0 0 4px;
								line-height: 60px;
								border-style: solid;
							}
		}
		.list6 {
						padding: 0;
						margin: 30px 0 0 -10px;
						background: none;
						display: flex;
						width: calc(100% + 20px);
						flex-wrap: wrap;
						height: auto;
						order: 4;
						.list-item {
								cursor: pointer;
								margin: 0 10px 20px;
								background: #fff;
								display: flex;
								width: calc(50% - 20px);
								position: relative;
								height: auto;
								img {
										object-fit: contain;
										object-position: center;
										background: #f2f3f5;
										display: inline-block;
										width: 220px;
										height: 220px;
									}
				.infoBox {
										padding: 10px;
										align-content: center;
										flex: 1;
										display: flex;
										width: 100%;
										align-items: center;
										flex-wrap: wrap;
										height: 220px;
										.name {
												overflow: hidden;
												color: #333;
												white-space: nowrap;
												font-weight: 600;
												width: 100%;
												font-size: 15px;
												line-height: 32px;
												text-overflow: ellipsis;
											}
					.desc {
												max-height: 120px;
												margin: 0 0 10px;
												overflow: hidden;
												color: #666;
												width: 100%;
												font-size: 14px;
												line-height: 24px;
											}
					.time_item {
												padding: 0 15px 0 0;
												.icon {
														margin: 0 2px 0 0;
														line-height: 1.5;
													}
						.label {
														line-height: 1.5;
													}
						.text {
														line-height: 1.5;
													}
					}
					.publisher_item {
												padding: 0 15px 0 0;
												.icon {
														margin: 0 2px 0 0;
														line-height: 1.5;
													}
						.label {
														line-height: 1.5;
													}
						.text {
														line-height: 1.5;
													}
					}
					.like_item {
												padding: 0 15px 0 0;
												.icon {
														margin: 0 2px 0 0;
														line-height: 1.5;
													}
						.label {
														line-height: 1.5;
													}
						.text {
														line-height: 1.5;
													}
					}
					.collect_item {
												padding: 0 15px 0 0;
												.icon {
														margin: 0 2px 0 0;
														line-height: 1.5;
													}
						.label {
														line-height: 1.5;
													}
						.text {
														line-height: 1.5;
													}
					}
					.view_item {
												padding: 0 15px 0 0;
												.icon {
														margin: 0 2px 0 0;
														line-height: 1.5;
													}
						.label {
														line-height: 1.5;
													}
						.text {
														line-height: 1.5;
													}
					}
					.tags {
												cursor: pointer;
												padding: 0 10px;
												margin: 0 0 0 auto;
												color: #666;
												background: none;
												bottom: 10px;
												display: block;
												font-size: 14px;
												line-height: 32px;
												position: absolute;
												right: 10px;
											}
				}
			}
		}
	}
	
	.index-pv1 .animation-box {
		transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
		z-index: initial;
	}
	
	.index-pv1 .animation-box:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 6px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
				z-index: 1;
	}
	
	.index-pv1 .animation-box img {
		transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
	}
	
	.index-pv1 .animation-box img:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
			}
</style>
