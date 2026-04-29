<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'：'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><a>{{item.name}}</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div v-if="centerType " class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui14"></span>
				<span class="text">返回</span>
			</el-button>
		</div>
		<div class="list-preview">
			<div class="category-1">
				<div class="item" :class="swiperIndex == '-1' ? 'active' : ''" @click="getList(1, '全部')" :plain="isPlain">全部</div>
				<div class="item" :class="swiperIndex == index ? 'active' : ''" v-for="(item, index) in fenlei" :key="index" @click="getList(1, item, 'btn' + index)" :ref="'btn' + index" plain>{{item}}</div>
			</div>
			<el-form :inline="true" :model="formSearch" class="list-form-pv">
				<el-form-item class="list-item" >
					<div class="lable">课程名称：</div>
					<el-input v-model="formSearch.kechengmingcheng" placeholder="课程名称" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">授课教师：</div>
					<el-input v-model="formSearch.shoukejiaoshi" placeholder="授课教师" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">授课地点：</div>
					<el-input v-model="formSearch.shoukedidian" placeholder="授课地点" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<div class="list-btn-box">
					<el-button class="list-search-btn" v-if=" true " type="primary" @click="getList(1, curFenlei)">
						<span class="icon iconfont icon-fangdajing07"></span>
						搜索
					</el-button>
					<el-button class="list-add-btn" v-if="btnAuth('kechengxinxi','新增')" type="primary" @click="add('/index/kechengxinxiAdd')">
						<span class="icon iconfont icon-tianjia7"></span>
						新增
					</el-button>
  
				</div>
			</el-form>
			<div class="sort_view">
				<el-button class="click-sort-btn" @click="sortClick('clicknum')">
					<span class="icon iconfont icon-liulan13" v-if="sortType!='clicknum'"></span>
					<span class="icon iconfont icon-jiantou35" v-else-if="sortType=='clicknum'&&sortOrder=='desc'"></span>
					<span class="icon iconfont icon-jiantou36" v-else-if="sortType=='clicknum'&&sortOrder=='asc'"></span>
					<span class="text">点击量</span>
				</el-button>
				<el-button class="collect-sort-btn" @click="sortClick('storeupnum')">
					<span class="icon iconfont icon-shoucang10" v-if="sortType!='storeupnum'"></span>
					<span class="icon iconfont icon-jiantou35" v-else-if="sortType=='storeupnum'&&sortOrder=='desc'"></span>
					<span class="icon iconfont icon-jiantou36" v-else-if="sortType=='storeupnum'&&sortOrder=='asc'"></span>
					<span class="text">收藏数</span>
				</el-button>
				<el-button class="like-sort-btn" @click="sortClick('thumbsupnum')">
					<span class="icon iconfont icon-zan10" v-if="sortType!='thumbsupnum'"></span>
					<span class="icon iconfont icon-jiantou35" v-else-if="sortType=='thumbsupnum'&&sortOrder=='desc'"></span>
					<span class="icon iconfont icon-jiantou36" v-else-if="sortType=='thumbsupnum'&&sortOrder=='asc'"></span>
					<span class="text">点赞数</span>
				</el-button>
			</div>
			<div class="list">
				<div class="list5">
					<div v-for="(item,index) in dataList" :key="index" class="list-item" @click.stop="toDetail(item)" >
						<div class="imgbox">
							<img @click.stop="imgPreView(item.tupian)" v-if="item.tupian && item.tupian.substr(0,4)=='http'&&item.tupian.split(',w').length>1" :src="item.tupian" class="image" />
							<img @click.stop="imgPreView(item.tupian.split(',')[0])" v-else-if="item.tupian && item.tupian.substr(0,4)=='http'" :src="item.tupian.split(',')[0]" class="image" />
							<img @click.stop="imgPreView(baseUrl + (item.tupian?item.tupian.split(',')[0]:''))" v-else :src="baseUrl + (item.tupian?item.tupian.split(',')[0]:'')" class="image" />
						</div>
						<div class="infoBox">
							<div class="info-line primary">
								<span class="field-label">课程名称</span>
								<span class="field-value">{{item.kechengmingcheng}}</span>
							</div>
							<div class="info-line">
								<span class="field-label">课程类型</span>
								<span class="field-value">{{item.kechengleixing}}</span>
							</div>
							<div class="info-line">
								<span class="field-label">授课教师</span>
								<span class="field-value">{{item.shoukejiaoshi}}</span>
							</div>
							<div class="info-line">
								<span class="field-label">授课地点</span>
								<span class="field-value">{{item.shoukedidian}}</span>
							</div>
							<div class="bottomInfo">
								<div class="time_item">
									<span class="icon iconfont icon-shijian21"></span>
									<span class="label">发布时间：</span>
									<span class="text">{{item.addtime.split(' ')[0]}}</span>
								</div>
								<div class="like_item">
									<span class="icon iconfont icon-zan10"></span>
									<span class="label">点赞数：</span>
									<span class="text">{{item.thumbsupnum}}</span>
								</div>
								<div class="collect_item">
									<span class="icon iconfont icon-shoucang10"></span>
									<span class="label">收藏量：</span>
									<span class="text">{{item.storeupnum}}</span>
								</div>
								<div class="view_item">
									<span class="icon iconfont icon-chakan2"></span>
									<span class="label">点击量：</span>
									<span class="text">{{item.clicknum}}</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

	
			<el-pagination
				background
				id="pagination"
				class="pagination"
				:pager-count="7"
				:page-size="pageSize"
				prev-text="<"
				next-text=">"
				:hide-on-single-page="true"
				:layout='["total","prev","pager","next","sizes","jumper"].join()'
				:total="total"
				:page-sizes="pageSizes"
				@current-change="curChange"
				@size-change="sizeChange"
				@prev-click="prevClick"
				@next-click="nextClick"
				></el-pagination>
		</div>
		<el-dialog title="预览图" :visible.sync="previewVisible" width="50%">
			<img :src="previewImg" alt="" style="width: 100%;">
		</el-dialog>
	</div>
</template>
<script>
	import axios from 'axios';
	export default {
		//数据集合
		data() {
			return {
				layouts: '',
				swiperIndex: -1,
				baseUrl: '',
				breadcrumbItem: [
					{
						name: '课程信息'
					}
				],
				formSearch: {
					kechengmingcheng: '',
					shoukejiaoshi: '',
					shoukedidian: '',
				},
				fenlei: [],
				dataList: [],
				total: 1,
				pageSize: 12,
				pageSizes: [],
				totalPage: 1,
				curFenlei: '全部',
				isPlain: false,
				indexQueryCondition: '',
				timeRange: [],
				centerType:false,
				previewImg: '',
				previewVisible: false,
				sortType: 'id',
				sortOrder: 'desc',
			}
		},
		async created() {
			if(this.$route.query.centerType&&this.$route.query.centerType!=0){
				this.centerType = true
			}
			this.baseUrl = this.$config.baseUrl;
			if(this.centerType) {
			}
			await this.getFenlei();
			let fenlei = '全部'
			if(this.$route.query.homeFenlei){
				fenlei = this.$route.query.homeFenlei
			}
			this.getList(1, fenlei);
		},
		watch:{
			$route(newValue){
				this.getList(1, newValue.query.homeFenlei);
			}
		},
		computed: {
			role(){
				return localStorage.getItem('frontRole');
			},
			username() {
				return localStorage.getItem('username')
			}
		},
		//方法集合
		methods: {
			queryChange(arr){
				for(let x in arr) {
					if(arr[x] == this.role) {
						return true
					}
				}
				return false
			},
			add(path) {
				let query = {}
				if(this.centerType){
					query.centerType = 1
				}
				this.$router.push({path: path,query:query});
			},
			async getFenlei() {
				await this.$http.get('option/kechengleixing/kechengleixing').then(res => {
					if (res.data.code == 0) {
						this.fenlei = res.data.data;
						if(this.$route.query.homeFenlei){
							for(let i=0;i<this.fenlei.length;i++) {
								if(this.$route.query.homeFenlei == this.fenlei[i]) {
									this.swiperIndex = i;
									const currentRoute = this.$route;
									const routeWithoutQuery = { ...currentRoute };
									delete routeWithoutQuery.query;
									this.$router.replace(routeWithoutQuery)
									break;
								}
							}
						}
					}
				});
			},
			getList(page, fenlei, ref = '') {
				if(fenlei == '全部') this.swiperIndex = -1;
				for(let i=0;i<this.fenlei.length;i++) {
					if(fenlei == this.fenlei[i]) {
						this.swiperIndex = i;
						break;
					}
				}
				if(fenlei){
					this.curFenlei = fenlei;
				}
				let params = {
					page,
					limit: this.pageSize,
				};
				if(!this.centerType) {
					params.onshelves = 1
				}
				let searchWhere = {};
				if (this.formSearch.kechengmingcheng != '') searchWhere.kechengmingcheng = '%' + this.formSearch.kechengmingcheng + '%';
				if (this.formSearch.shoukejiaoshi != '') searchWhere.shoukejiaoshi = '%' + this.formSearch.shoukejiaoshi + '%';
				if (this.formSearch.shoukedidian != '') searchWhere.shoukedidian = '%' + this.formSearch.shoukedidian + '%';
				if (this.curFenlei != '全部') searchWhere.kechengleixing = this.curFenlei;
				let user = JSON.parse(localStorage.getItem('sessionForm'))
				if (this.sortType) searchWhere.sort = this.sortType
				if (this.sortOrder) searchWhere.order = this.sortOrder
				this.$http.get(`kechengxinxi/${this.centerType?'page':'list'}`, {params: Object.assign(params, searchWhere)}).then(res => {
					if (res.data.code == 0) {
						this.dataList = res.data.data.list;
						this.total = Number(res.data.data.total);
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
						if(this.pageSizes.length==0){
							this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
						}
					}
				});
			},
			sortClick(type){
				if(this.sortType==type){
					if(this.sortOrder == 'desc'){
						this.sortOrder = 'asc'
					}else{
						this.sortOrder = 'desc'
					}
				}else{
					this.sortType = type
					this.sortOrder = 'desc'
				}
				this.getList(1, '全部')
			},
			curChange(page) {
				this.getList(page,this.curFenlei);
			},
			prevClick(page) {
				this.getList(page,this.curFenlei);
			},
			sizeChange(size){
				this.pageSize = size
				this.getList(1,this.curFenlei);
			},
			nextClick(page) {
				this.getList(page,this.curFenlei);
			},
			imgPreView(url){
				this.previewImg = url
				this.previewVisible = true
			},
			toDetail(item) {
				let params = {
					id: item.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/kechengxinxiDetail', query: params});
			},
			btnAuth(tableName,key){
				if(this.centerType){
					return this.isBackAuth(tableName,key)
				}else{
					return this.isAuth(tableName,key)
				}
			},
			backClick() {
				this.$router.push({path: '/index/center'});
			},

			discussClick (row){
				let params = {
					refid: row.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/discusskechengxinxi', query: params});
			},
			chapterClick (row){
				let params = {
					refid: row.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/chapterkechengxinxi', query: params});
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.list-preview {
		padding-top: 26px;
		padding-bottom: 40px;

		.category-1 {
			margin-bottom: 18px;
			display: flex;
			gap: 12px;
			flex-wrap: wrap;
			align-items: center;
			padding: 14px 18px !important;
			background: none !important;
		}

		.list-form-pv {
			display: grid;
			grid-template-columns: repeat(3, minmax(0, 1fr)) auto;
			gap: 18px;
			align-items: center;
			padding: 22px !important;
		}

		.list-form-pv .list-item {
			margin: 0 !important;
		}

		.list-form-pv .list-item .lable {
			margin-bottom: 8px;
			display: block;
			font-size: 16px;
			font-weight: 600;
			color: #264660;
		}

		.list-btn-box {
			display: flex;
			gap: 12px;
			align-items: center;
			justify-content: flex-start;
			padding-bottom: 0;
			align-self: center;
		}

		.list {
			margin-top: 20px;
		}

		.list5 {
			margin: 0 !important;
			width: 100% !important;
			display: grid !important;
			grid-template-columns: repeat(3, minmax(0, 1fr));
			gap: 24px;
		}

		.list5 .list-item {
			width: 100% !important;
			margin: 0 !important;
			padding: 16px !important;
		}

		.list5 .list-item .imgbox {
			width: 100% !important;
		}

		.list5 .list-item .imgbox .image {
			width: 100% !important;
			height: 220px !important;
			margin-bottom: 12px !important;
		}

		.list5 .list-item .infoBox {
			padding: 0 !important;
		}

		.list5 .list-item .info-line {
			margin-bottom: 10px !important;
			align-items: flex-start !important;
		}

		.list5 .list-item .field-label {
			flex: 0 0 72px !important;
		}

		.list5 .list-item .field-value {
			white-space: normal !important;
			word-break: break-word;
		}

		.list5 .list-item .bottomInfo {
			justify-content: flex-start !important;
			gap: 8px 14px;
		}
	}

	@media (max-width: 1280px) {
		.list-preview .list-form-pv {
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}

		.list-preview .list5 {
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}
	}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
	.list-preview {
		padding: 0 10%;
		margin: 0px auto;
		align-content: flex-start;
		background: none;
		display: flex;
		width: 100%;
		align-items: flex-start;
		position: relative;
		flex-wrap: wrap;
		.category-1 {
			border: 0px solid #eee;
			padding: 30px 0px 0px;
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
		.list-form-pv {
			padding: 0;
			margin: 10px auto;
			background: none;
			display: flex;
			width: 100%;
			border-color: #ddd;
			border-width: 0px;
			justify-content: center;
			align-items: flex-end;
			border-style: solid;
			flex-wrap: nowrap;
			gap: 20px;
			height: auto;
			.list-item {
				margin: 0;
				display: flex;
				flex-direction: column;
				align-items: flex-start;
				::v-deep.el-form-item__content {
					display: flex;
					flex-direction: column;
					align-items: flex-start;
				}
				.lable {
					padding: 0 0 8px;
					color: #333;
					white-space: nowrap;
					display: block;
					width: auto;
					line-height: 1.2;
					font-size: 16px;
					font-weight: 600;
				}
				.el-input {
					width: 280px;
				}
				.datetimerange {
					border: 1px solid #ddd;
					border-radius: 2px;
					padding: 3px 0px;
					outline: none;
					background: #fff;
					width: auto;
					justify-content: center;
				}
				.el-input ::v-deep .el-input__inner {
					border: 1px solid #ddd;
					border-radius: 2px;
					padding: 0 10px;
					margin: 0;
					outline: none;
					color: #333;
					width: 280px;
					font-size: 14px;
					line-height: 42px;
					height: 42px;
				}
				.el-select {
					width: 100%;
				}
				.el-select ::v-deep .el-input__inner {
					border: 1px solid #ddd;
					border-radius: 2px;
					padding: 0 10px;
					margin: 0;
					outline: none;
					color: #333;
					width: 280px;
					font-size: 14px;
					line-height: 42px;
					height: 42px;
				}
				.el-date-editor {
					width: 100%;
				}
				.el-date-editor ::v-deep .el-input__inner {
					border: 1px solid #ddd;
					border-radius: 2px;
					padding: 0 30px;
					margin: 0;
					outline: none;
					color: #333;
					width: 280px;
					font-size: 14px;
					line-height: 42px;
					height: 42px;
				}
			}
			.list-btn-box {
				display: flex;
				align-items: flex-end;
				padding-bottom: 0;
			}
			.list-search-btn {
				cursor: pointer;
				border: 0;
				border-radius: 4px;
				padding: 0px 28px;
				margin: 0;
				outline: none;
				color: #fff;
				background: #253b43;
				width: auto;
				font-size: 16px;
				line-height: 42px;
				height: 42px;
				.icon {
					margin: 0 3px 0 0;
					color: #fff;
					font-size: 16px;
				}
			}
			.list-add-btn {
				cursor: pointer;
				border: 0;
				border-radius: 4px;
				padding: 0px 10px;
				margin: 0 10px 0 0;
				outline: none;
				color: #fff;
				background: #253b43;
				width: auto;
				font-size: 16px;
				line-height: 42px;
				height: 42px;
				.icon {
					margin: 0 3px 0 0;
					color: #fff;
					font-size: 16px;
				}
			}
		}
		.sort_view {
			border: 0px solid #ddd;
			padding: 0;
			margin: 10px auto;
			color: #333;
			background: #fff;
			width: 100%;
			font-size: 16px;
			text-align: center;
			order: 2;
			.click-sort-btn {
				border: 0;
				border-radius: 8px;
				padding: 0 10px;
				margin: 0 5px;
				color: inherit;
				background: none;
				font-size: inherit;
				.icon {
					margin: 0 2px 0 0;
					line-height: 40px;
				}
				.text {
					line-height: 40px;
				}
			}
			.collect-sort-btn {
				border: 0;
				border-radius: 8px;
				padding: 0 10px;
				margin: 0 5px;
				color: inherit;
				background: none;
				font-size: inherit;
				.icon {
					margin: 0 2px 0 0;
					line-height: 40px;
				}
				.text {
					line-height: 40px;
				}
			}
			.like-sort-btn {
				border: 0;
				border-radius: 8px;
				padding: 0 10px;
				margin: 0 5px;
				color: inherit;
				background: none;
				font-size: inherit;
				.icon {
					margin: 0 2px 0 0;
					line-height: 40px;
				}
				.text {
					line-height: 40px;
				}
			}
		}
		.list {
			margin: 30px 0 0 0;
			background: none;
			width: 100%;
			order: 4;
			.index-pv1 .animation-box {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				z-index: initial;
			}
				
			.index-pv1 .animation-box:hover {
				transform: rotate(0) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
				z-index: 1;
			}
				
			.index-pv1 .animation-box img {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
			}
			
			.index-pv1 .animation-box img:hover {
				transform: rotate(0) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
			}
			.list5 {
				margin: 0px auto 20px -10px;
				color: #999;
				display: flex;
				width: calc(100% + 20px);
				font-size: 14px;
				flex-wrap: wrap;
				.list-item {
					border: 0px solid #eee;
					border-radius: 6px;
					padding: 0;
					margin: 0 10px 20px;
					overflow: hidden;
					background: #fff;
					display: block;
					width: calc(33.33% - 20px);
					position: relative;
					.imgbox {
						border-radius: 6px;
						overflow: hidden;
						width: auto;
						.image {
							filter: saturate(150%);
							border-radius: 6px;
							transform: rotate(0deg);
							margin: 0 0 5px;
							object-fit: cover;
							display: block;
							width: 100%;
							opacity: 0.9;
							height: 380px;
						}
					}
					.infoBox {
						border-radius: 6px;
						padding: 6px 4px 0;
						left: 0px;
						bottom: 0;
						background: none;
						display: flex;
						width: 100%;
						font-size: 14px;
						justify-content: flex-start;
						position: inherit;
						flex-wrap: wrap;
						transition: all 0.5s;
						.info-line {
							display: flex;
							align-items: baseline;
							gap: 10px;
							width: 100%;
							margin-bottom: 6px;
							.field-label {
								flex: 0 0 72px;
								color: #6b7d89;
								font-size: 13px;
								font-weight: 600;
								line-height: 1.6;
							}
							.field-value {
								flex: 1;
								overflow: hidden;
								color: #223844;
								white-space: nowrap;
								font-size: 16px;
								line-height: 1.6;
								text-overflow: ellipsis;
							}
						}
						.info-line.primary {
							.field-value {
								font-size: 18px;
								font-weight: 700;
							}
						}
						.price {
							border: 0px solid #707070;
							margin: 0px auto;
							color: #ff0000;
							width: auto;
							font-size: 14px;
							line-height: 30px;
							text-align: center;
							.price_text {
								color: #ff0000;
								font-size: 18px;
							}
						}
						.bottomInfo {
							display: flex;
							justify-content: flex-start;
							flex-wrap: wrap;
							margin-top: 6px;
							.time_item {
								padding: 0;
								margin: 0 10px 0 0;
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
								padding: 0;
								margin: 0 10px 0 0;
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
								padding: 0;
								margin: 0 10px 0 0;
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
								padding: 0;
								margin: 0 10px 0 0;
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
								padding: 0;
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
						}
					}
				}
				.list-item:hover {
					cursor: pointer;
					border-radius: 6px;
					background: none;
					.imgbox {
						.image {
							border-radius: 6px;
							transform: scale(1.05);
							ilter: saturate(100%);
							opacity: 1;
							transition: all 200ms linear;
						}
					}
					.infoBox {
						border-radius: 6px;
						bottom: 0px;
						background: none;
						font-size: 14px;
						.name {
						}
						.price {
							.price_text {
							}
						}
						.bottomInfo {
							display: flex;
							justify-content: center;
							flex-wrap: wrap;
							.time_item {
								.icon {
								}
								.label {
								}
								.text {
								}
							}
							.publisher_item {
								.icon {
								}
								.label {
								}
								.text {
								}
							}
							.like_item {
								.icon {
								}
								.label {
								}
								.text {
								}
							}
							.collect_item {
								.icon {
								}
								.label {
								}
								.text {
								}
							}
							.view_item {
								.icon {
								}
								.label {
								}
								.text {
								}
							}
						}
					}
				}
			}
		}
	}
</style>
