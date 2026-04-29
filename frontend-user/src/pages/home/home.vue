<template>
	<div class="home-preview">




		<!-- 系统简介 -->
		<div id="system" class="animate__animated">
			<div class="system_item">
				<div class="system_text">
					<div class="system_title_box">
						<div class="system_title">{{systemIntroductionDetail.title}}</div>
						<div class="system_subtitle">{{systemIntroductionDetail.subtitle}}</div>
					</div>
					<div class="system_content ql-snow ql-editor" v-html="systemIntroductionDetail.content"></div>
				</div>
				<div class="system_media">
					<div class="system_main">
						<img :src="baseUrl + systemIntroductionDetail.picture1">
					</div>
					<div class="system_side_cards">
						<img :src="baseUrl + systemIntroductionDetail.picture2">
						<img :src="baseUrl + systemIntroductionDetail.picture3">
					</div>
					<div class="system_more" @click="toDetail('systemintroDetail',systemIntroductionDetail)">
						<span>查看详情</span>
						<span class="icon iconfont icon-jiantou25"></span>
					</div>
				</div>
				<div class="system_idea1" />
				<div class="system_idea2" />
				<div class="system_idea3" />
				<div class="system_idea4" />
			</div>
		</div>
		<!-- 系统简介 -->
		<!-- 新闻资讯 -->
		<div id="animate_newsnews" class="news animate__animated">
			<div class="news_title_box">
				<span class="news_title">公告</span>
				<span class="news_subhead">{{'news'.toUpperCase()}}</span>
			</div>
			<!-- 样式十 -->
			<div v-if="newsList.length" class="list list10 index-pv1">
				<div v-if="newsList.length>0" @click="toDetail('newsDetail', newsList[0])" class="list-item1 animation-box">
					<img :src="baseUrl + (newsList[0].picture?newsList[0].picture.split(',')[0]:'')" alt="">
					<div class="name line1">{{newsList[0].title}}</div>
					<div class="desc line2">{{newsList[0].introduction}}</div>
					<div class="time_item">
						<span class="icon iconfont icon-shijian21"></span>
						<span class="label">发布时间：</span>
						<span class="text">{{newsList[0].addtime.split(' ')[0]}}</span>
					</div>
					<div class="publisher_item">
						<span class="icon iconfont icon-geren16"></span>
						<span class="label">发布人：</span>
						<span class="text">{{newsList[0].name}}</span>
					</div>
					<div class="new10-list-item-identification">新闻动态</div>
				</div>
				<div class="right_box" v-if="newsList.length>1">
					<div v-for="(item,index) in newsList" v-if="index<4 && index>0" :key="index" @click="toDetail('newsDetail', item)" class="list-item2 animation-box">
						<div class="name line1">{{ item.title }}</div>
						<div class="desc line2">{{ item.introduction }}</div>
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
						<div class="new10-list-item-identification">新闻动态</div>
					</div>
				</div>
			</div>
			<div class="moreBtn" @click="moreBtn('news')">
				<span class="text">查看更多</span>
				<i class="icon iconfont icon-jiantou25"></i>
			</div>
		</div>
		<!-- 新闻资讯 -->
		<!-- 商品推荐 -->
		<div id="animate_recommendkechengxinxi" class="recommend animate__animated">
			<div class="recommend_title_box">
				<span class="recommend_title">课程信息推荐</span>
				<span class="recommend_subhead">{{'kechengxinxi'.toUpperCase()}} RECOMMEND</span>
			</div>
			<div v-if="kechengxinxiRecommend.length" class="list list12 index-pv1 list12kechengxinxi">
				<div class="list-body">
					<div class="item list-item animation-box" v-for="item,index in kechengxinxiRecommend" :key="index" @click="toDetail('kechengxinxiDetail', item)">
						<img :name="item.id" v-if="preHttp(item.tupian)&&preHttp2(item.tupian)" :src="item.tupian" alt="" />
						<img :name="item.id" v-else-if="preHttp(item.tupian)" :src="item.tupian.split(',')[0]" alt="" />
						<img :name="item.id" v-else :src="baseUrl + (item.tupian?item.tupian.split(',')[0]:'')" alt="" />
						<div class="meta-line primary">
							<span class="meta-label">课程名称</span>
							<span class="meta-value">{{item.kechengmingcheng}}</span>
						</div>
						<div class="meta-line">
							<span class="meta-label">课程类型</span>
							<span class="meta-value">{{item.kechengleixing}}</span>
						</div>
						<div class="meta-line">
							<span class="meta-label">授课教师</span>
							<span class="meta-value">{{item.shoukejiaoshi}}</span>
						</div>
						<div class="meta-line">
							<span class="meta-label">授课地点</span>
							<span class="meta-value">{{item.shoukedidian}}</span>
						</div>
					</div>
				</div>
			</div>
			<div class="moreBtn" @click="moreBtn('kechengxinxi')">
				<span class="text">查看更多</span>
				<i class="icon iconfont "></i>
			</div>
		</div>
		<!-- 商品推荐 -->
	</div>
</template>

<script>
import 'animate.css'
import Swiper from "swiper";

	export default {
		//数据集合
		data() {
			return {
				baseUrl: '',
				systemIntroductionDetail: {},
				newsList: [],
				kechengxinxiRecommend: [],



				recommendIndex12kechengxinxi: 0,
				recommendList12kechengxinxi: [],
				recommendColumn12kechengxinxi: '',


			}
		},
		created() {
			this.baseUrl = this.$config.baseUrl;
			this.getNewsList();
			this.getSystemIntroduction();
			this.getList();
			this.kechengxinxiRecommendCate()
		},
		mounted() {
			window.addEventListener('scroll', this.handleScroll)
			setTimeout(()=>{
				this.handleScroll()
			},100)
			
			this.swiperChanges()
		},
		beforeDestroy() {
			window.removeEventListener('scroll', this.handleScroll)
		},
		computed: {
			username() {
				return localStorage.getItem('username')
			}
		},
		//方法集合
		methods: {
			swiperChanges() {
				setTimeout(()=>{
				},750)
			},
			kechengxinxiRecommendCate(){
				this.$http.get('kechengleixing/list',{
					params: {
						limit: Number(6)
					}
				}).then(res => {
					if (res.data.code == 0) {
						this.recommendList12kechengxinxi = res.data.data.list
					}
				});
				this.recommendColumn12kechengxinxi = 'kechengleixing'
			},
			recommendIndexClick12(index, name) {
				this['recommendIndex12' + name] = index
				this.getList()
				
				document.querySelectorAll('.recommend .list12' + name + ' .list .item').forEach(el => {
					el.classList.remove("active")
				})
				setTimeout(() => {
					document.querySelectorAll('.recommend .list12' + name + ' .list .item').forEach(el => {
						el.classList.add("active")
					})
				}, 1);
			},


			handleScroll() {
				let arr = [
					{id:'about',css:'animate__'},
					{id:'system',css:'animate__'},
					{id:'animate_recommendkechengxinxi',css:'animate__'},
					{id:'animate_newsnews',css:'animate__'},
				]
			
				for (let i in arr) {
					let doc = document.getElementById(arr[i].id)
					if (doc) {
						let top = doc.offsetTop
						let win_top = window.innerHeight + window.pageYOffset
						// console.log(top,win_top)
						if (win_top > top && doc.classList.value.indexOf(arr[i].css) < 0) {
							// console.log(doc)
							doc.classList.add(arr[i].css)
						}
					}
				}
			},
			preHttp(str) {
				return str && str.substr(0,4)=='http';
			},
			preHttp2(str) {
				return str && str.split(',w').length>1;
			},
			getSystemIntroduction() {
				this.$http.get('systemintro/detail/1', {}).then(res => {
					if(res.data.code == 0) {
						this.systemIntroductionDetail = res.data.data;
					}
				})
			},
			getNewsList() {
				let data = {
					page: 1,
					limit: 4,
					sort: 'addtime',
					order: 'desc'
				}
				this.$http.get('news/list', {params: data}).then(res => {
					if (res.data.code == 0) {
						this.newsList = res.data.data.list;
					
					}
				});
			},
			getList() {
				let autoSortUrl = "";
				let data = {}
				autoSortUrl = "kechengxinxi/autoSort";
				if(localStorage.getItem('frontToken')) {
					autoSortUrl = "kechengxinxi/autoSort2";
				}
				data = {
					page: 1,
					limit: 8,
					onshelves: 1,
				}
				if(this.recommendIndex12kechengxinxi != 0){
					data.kechengleixing = this.recommendList12kechengxinxi[this.recommendIndex12kechengxinxi - 1].kechengleixing
				}
				this.$http.get(autoSortUrl, {params: data}).then(res => {
					if (res.data.code == 0) {
						this.kechengxinxiRecommend = res.data.data.list;
					}
				});
			
			},
			toDetail(path, item) {
				this.$router.push({path: '/index/' + path, query: {id: item.id, storeupType: 1}});
			},
			moreBtn(path) {
				this.$router.push({path: '/index/' + path});
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.home-preview {
		margin: 0 auto;
		display: flex;
		width: 100%;
		justify-content: space-between;
		flex-wrap: wrap;
		#system {
			padding: 0 10%;
			box-shadow: none;
			margin: 50px auto;
			background: #fff;
			width: 100%;
			height: auto;
			order: 5;
			.system_item {
				background: none;
				display: flex;
				width: 100%;
				position: relative;
				flex-wrap: wrap;
				.system_title_box {
					padding: 0;
					margin: 0 0 20px;
					display: flex;
					width: 100%;
					flex-wrap: wrap;
					.system_title {
						padding: 15px 0 0 0;
						margin: 0 0 15px;
						color: #000;
						background: url(http://codegen.caihongy.cn/20251211/52bb46d7a43c4001b3cae5dcfa21eafa.png) no-repeat left top;
						font-weight: 500;
						display: block;
						width: 100%;
						font-size: 32px;
						line-height: 40px;
						text-align: left;
					}
					.system_subtitle {
						margin: 0;
						color: #333;
						font-weight: 500;
						display: none;
						width: 100%;
						font-size: 20px;
						line-height: 36px;
						text-align: left;
					}
				}
				.system_img {
					padding: 0;
					margin: 0;
					display: inline-flex;
					width: 62%;
					flex-wrap: wrap;
					height: auto;
					order: 2;
					img:nth-child(1) {
						border-radius: 0px;
						padding: 0;
						margin: 0;
						object-fit: contain;
						object-position: center;
						background: #f2f3f5;
						display: block;
						width: 100%;
						height: 420px;
					}
					img:nth-child(2) {
						margin: 0;
						left: 0;
						bottom: 85px;
						object-fit: contain;
						object-position: center;
						background: #f2f3f5;
						display: block;
						width: 200px;
						position: absolute;
						height: 160px;
					}
					img:nth-child(3) {
						margin: 0;
						left: 230px;
						bottom: 85px;
						object-fit: contain;
						object-position: center;
						background: #f2f3f5;
						display: block;
						width: 200px;
						position: absolute;
						height: 160px;
					}
				}
				.system_content {
					padding: 0;
					margin: 0 2% 0 0;
					color: #666;
					display: -webkit-box;
					font-size: 16px;
					line-height: 26px;
					text-overflow: ellipsis;
					overflow: hidden;
					background: #fff;
					width: 32%;
					-webkit-box-orient: vertical;
					-webkit-line-clamp: 8;
					height: 100%;
					order: 1;
				}
				.system_idea1 {
					background: url(http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg) 0% 0% / cover no-repeat;
					display: none;
					width: 285px;
					height: 100px;
				}
				.system_idea2 {
					background: url(http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg) 0% 0% / cover no-repeat;
					display: none;
					width: 285px;
					height: 100px;
				}
				.system_idea3 {
					background: url(http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg) 0% 0% / cover no-repeat;
					display: none;
					width: 285px;
					height: 100px;
				}
				.system_idea4 {
					background: url(http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg) 0% 0% / cover no-repeat;
					display: none;
					width: 285px;
					height: 100px;
				}
				.system_more {
					padding: 0;
					margin: 40px 0 0 0;
					background: none;
					display: flex;
					width: 100%;
					border-color: #000000;
					border-width: 0px 0 0 0;
					justify-content: center;
					border-style: solid;
					order: 11;
					span:nth-child(1) {
						border-radius: 30px;
						color: #fff;
						background: #263c44;
						width: 180px;
						font-size: 16px;
						line-height: 45px;
						text-align: center;
						height: 45px;
					}
					span:nth-child(2) {
						color: #333;
						display: none;
						width: 23px;
						font-size: 16px;
						height: 7px;
					}
				}
				.system_more:hover {
					cursor: pointer;
					opacity: 1;
				}
			}
		}
		.news {
			padding: 0px 10%;
			margin: 20px auto;
			background: none;
			width: 100%;
			position: relative;
			order: 4;
			.news_title_box {
				margin: 0 0 0px;
				background: none;
				display: flex;
				width: 100%;
				line-height: auto;
				flex-wrap: wrap;
				.news_title {
					padding: 15px 0 0 0;
					margin: 0 0 15px;
					color: #000;
					background: url(http://codegen.caihongy.cn/20251211/52bb46d7a43c4001b3cae5dcfa21eafa.png) no-repeat left top;
					font-weight: 500;
					display: block;
					width: 100%;
					font-size: 32px;
					line-height: 40px;
					text-align: left;
				}
				.news_subhead {
					margin: 0;
					color: #333;
					font-weight: 500;
					display: none;
					width: 100%;
					font-size: 20px;
					line-height: 36px;
					text-align: center;
				}
			}
			.index-pv1 .animation-box:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, -4px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
				z-index: 1;
			}
			.index-pv1 .animation-box img:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
			}
			.list10 {
				padding: 0;
				margin: 20px 0 0;
				background: none;
				display: flex;
				width: 100%;
				height: auto;
				.list-item1 {
					cursor: pointer;
					padding: 0 0 25px 0;
					margin: 0 40px 0 0;
					display: flex;
					border-color: #000;
					flex-wrap: wrap;
					align-content: center;
					background: none;
					width: 48%;
					border-width: 0 0 0px 0;
					align-items: center;
					position: relative;
					border-style: solid;
					height: auto;
					img {
						margin: 0 0 5px;
						object-fit: contain;
						object-position: center;
						background: #f2f3f5;
						display: block;
						width: 100%;
						height: 320px;
					}
					.name {
						cursor: pointer;
						padding: 0;
						overflow: hidden;
						color: #333;
						white-space: nowrap;
						background: none;
						font-weight: 600;
						width: 100%;
						font-size: 16px;
						line-height: 30px;
						text-overflow: ellipsis;
					}
					.desc {
						cursor: pointer;
						padding: 0;
						margin: 0 0 10px;
						overflow: hidden;
						color: #666;
						white-space: nowrap;
						background: none;
						font-weight: normal;
						width: 100%;
						font-size: 14px;
						line-height: 24px;
						text-overflow: ellipsis;
					}
					.time_item {
						padding: 0 10px 0 0;
						.icon {
							margin: 0 2px 0 0;
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
						.label {
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
						.text {
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
					}
					.publisher_item {
						padding: 0 10px 0 0;
						.icon {
							margin: 0 2px 0 0;
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
						.label {
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
						.text {
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
					}
					.like_item {
						padding: 0 10px 0 0;
						.icon {
							margin: 0 2px 0 0;
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
						.label {
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
						.text {
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
					}
					.collect_item {
						padding: 0 10px 0 0;
						.icon {
							margin: 0 2px 0 0;
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
						.label {
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
						.text {
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
					}
					.view_item {
						padding: 0 10px 0 0;
						.icon {
							margin: 0 2px 0 0;
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
						.label {
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
						.text {
							color: #666;
							font-size: 12px;
							line-height: 1.5;
						}
					}
					.new10-list-item-identification {
						padding: 0 10px;
						margin: 0 10px 10px;
						color: #999;
						background: #fff;
						display: none;
						font-size: 12px;
						line-height: 24px;
					}
				}
				.right_box {
					margin: 0;
					flex-direction: column;
					background: none;
					flex: 1;
					display: flex;
					width: 48%;
					border-color: #000;
					border-width: 0px 0 0 0;
					justify-content: space-between;
					position: relative;
					border-style: solid;
					height: auto;
					.list-item2 {
						cursor: pointer;
						align-content: center;
						background: none;
						display: flex;
						width: 100%;
						border-color: #000;
						border-width: 0 0 0px 0;
						align-items: center;
						position: relative;
						border-style: solid;
						flex-wrap: wrap;
						height: calc(33.33% - 10px);
						.name {
							padding: 0;
							overflow: hidden;
							color: #333;
							white-space: nowrap;
							font-weight: 600;
							width: 100%;
							font-size: 16px;
							line-height: 30px;
							text-overflow: ellipsis;
						}
						.desc {
							cursor: pointer;
							padding: 0;
							margin: 0 0 10px;
							overflow: hidden;
							color: #666;
							white-space: nowrap;
							background: none;
							font-weight: normal;
							width: 100%;
							font-size: 14px;
							line-height: 24px;
							text-overflow: ellipsis;
						}
						.time_item {
							padding: 0 10px 0 0;
							.icon {
								margin: 0 2px 0 0;
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
							.label {
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
							.text {
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
						}
						.publisher_item {
							padding: 0 10px 0 0;
							.icon {
								margin: 0 2px 0 0;
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
							.label {
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
							.text {
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
						}
						.like_item {
							padding: 0 10px 0 0;
							.icon {
								margin: 0 2px 0 0;
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
							.label {
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
							.text {
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
						}
						.collect_item {
							padding: 0 10px 0 0;
							.icon {
								margin: 0 2px 0 0;
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
							.label {
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
							.text {
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
						}
						.view_item {
							padding: 0 10px 0 0;
							.icon {
								margin: 0 2px 0 0;
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
							.label {
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
							.text {
								color: #666;
								font-size: 12px;
								line-height: 1.5;
							}
						}
						.new10-list-item-identification {
							padding: 0 10px;
							color: #999;
							background: #fff;
							display: none;
							font-size: 12px;
							line-height: 24px;
						}
					}
				}
			}
			.moreBtn {
				border: 0px solid #ddd;
				cursor: pointer;
				border-radius: 30px;
				margin: 20px auto;
				background: #243a42;
				display: flex;
				width: 180px;
				line-height: 45px;
				justify-content: center;
				.text {
					margin: 0 0px 0 0;
					color: #fff;
					font-size: 16px;
					order: 2;
				}
				.icon {
					color: #999;
					background: none;
					display: none;
					width: 23px;
					font-size: 16px;
					height: 7px;
				}
			}
		}
		.recommend {
			padding: 60px 10% 60px;
			margin: 0 auto 0;
			background: #fff;
			width: 100%;
			position: relative;
			order: 1;
			.recommend_title_box {
				margin: 0 0 0px;
				background: none;
				display: flex;
				width: 100%;
				line-height: auto;
				flex-wrap: wrap;
				.recommend_title {
					padding: 15px 0 0 0;
					margin: 0 0 15px;
					color: #000;
					background: url(http://codegen.caihongy.cn/20251211/52bb46d7a43c4001b3cae5dcfa21eafa.png) no-repeat left top;
					font-weight: 500;
					display: block;
					width: 100%;
					font-size: 32px;
					line-height: 40px;
					text-align: left;
				}
				.recommend_subhead {
					margin: 0;
					color: #333;
					font-weight: 500;
					display: none;
					width: 100%;
					font-size: 20px;
					line-height: 36px;
					text-align: center;
				}
			}
			.index-pv1 .animation-box {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				z-index: initial;
			}
			
			.index-pv1 .animation-box:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, -4px, 0px);
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
			.list12 {
				padding: 0;
				background: none;
				width: 100%;
				height: auto;
				.tab_view {
					padding: 60px 0 0;
					margin: 0 0 40px;
					background: url(http://codegen.caihongy.cn/20251211/cb866379f4ed4512a0303bf5909bfada.png) no-repeat left top,url(http://codegen.caihongy.cn/20251211/95c1639d44234c21b91bb9d1fbc79288.png) no-repeat right top,url(http://codegen.caihongy.cn/20251211/2a2bbc3dea684d20b402fe090076a772.png) repeat-x center top;
					display: flex;
					width: 100%;
					justify-content: center;
					height: auto;
					.tab {
						cursor: pointer;
						border: 0;
						padding: 0 20px;
						margin: 0 10px;
						color: #333;
						display: flex;
						width: auto;
						font-size: 18px;
						line-height: 44px;
						align-items: center;
						img {
							margin: 0 5px 0 0;
							object-fit: contain;
							object-position: center;
							background: #f2f3f5;
							display: block;
							width: 34px;
							height: 34px;
						}
						.text {
							color: inherit;
							font-size: 16px;
						}
					}
					.tab:hover {
						color: #333;
						font-size: 18px;
					}
					.tab.active {
						color: #333;
						font-size: 18px;
						border-color: #ddd;
						border-width: 0 0 2px 0;
						border-style: solid;
					}
					.more_btn {
						cursor: pointer;
						padding: 5px 10px;
						margin: 0 10px;
						color: #fff;
						background: none;
						display: none;
						line-height: 44px;
						align-items: center;
						.text {
							color: inherit;
							font-size: 14px;
						}
						.icon {
							margin: 0 0 0 2px;
							color: inherit;
							font-size: 14px;
						}
					}
					.more_btn:hover {
						color: red;
					}
					.tab.active {
						animation-name: mymove;
					
						&:nth-of-type(1) {
							animation-duration: 1s;
						}
						&:nth-of-type(2) {
							animation-duration: 1.2s;
						}
						&:nth-of-type(3) {
							animation-duration: 1.4s;
						}
						&:nth-of-type(4) {
							animation-duration: 1.6s;
						}
						&:nth-of-type(5) {
							animation-duration: 1.8s;
						}
						&:nth-of-type(6) {
							animation-duration: 2s;
						}
					}
					
					@keyframes mymove {
						from {top: 320px;}
						to {top: 0;}
					}
				}
				.list-body {
					margin: 0 0 0 -10px;
					overflow: hidden;
					background: none;
					display: flex;
					width: calc(100% + 20px);
					flex-wrap: wrap;
					height: auto;
					.list-item {
						cursor: pointer;
						padding: 0 0 5px;
						margin: 0 10px 20px;
						background: #fff;
						width: calc(25% - 20px);
						position: relative;
						height: auto;
						img {
							margin: 0 0 5px;
							object-fit: contain;
							object-position: center;
							background: #f2f3f5;
							display: block;
							width: 100%;
							height: 240px;
						}
						.meta-line {
							display: flex;
							align-items: baseline;
							gap: 8px;
							width: 100%;
							margin-top: 6px;
							.meta-label {
								flex: 0 0 64px;
								color: #6b7d89;
								font-size: 12px;
								font-weight: 600;
								line-height: 1.8;
							}
							.meta-value {
								flex: 1;
								padding: 0;
								overflow: hidden;
								color: #333;
								white-space: nowrap;
								width: 100%;
								font-size: 14px;
								line-height: 1.8;
								text-overflow: ellipsis;
							}
						}
						.meta-line.primary {
							.meta-value {
								font-size: 16px;
								font-weight: 700;
							}
						}
						.price {
							padding: 0;
							color: #f00;
							font-size: 16px;
							line-height: 1.5;
						}
					}
					.list-item.active {
						animation-name: mymove;
					
						&:nth-of-type(1) {
							animation-duration: 1s;
						}
						&:nth-of-type(2) {
							animation-duration: 1.2s;
						}
						&:nth-of-type(3) {
							animation-duration: 1.4s;
						}
						&:nth-of-type(4) {
							animation-duration: 1.6s;
						}
						&:nth-of-type(5) {
							animation-duration: 1.8s;
						}
						&:nth-of-type(6) {
							animation-duration: 2s;
						}
					}
					
					@keyframes mymove {
						from {top: 320px;}
						to {top: 0;}
					}
				}
			}
			.moreBtn {
				border: 0px solid #555;
				cursor: pointer;
				border-radius: 30px;
				padding: 0;
				margin: 20px auto 0;
				background: #243a42;
				display: flex;
				width: 180px;
				line-height: 45px;
				justify-content: center;
				position: relative;
				.text {
					margin: 0 5px 0 0;
					color: #fff;
					font-size: 16px;
					order: 2;
				}
				.icon {
					margin: 0 10px 0 0;
					z-index: 1111;
					color: #333;
					top: 30%;
					left: 85%;
					background: url(http://codegen.caihongy.cn/20251203/33cac7eb175745868d47dde5ba3ab307.png) no-repeat right center;
					display: none;
					width: 42px;
					font-size: 18px;
					position: absolute;
					height: 12px;
				}
			}
		}
	}

	.home-preview {
		padding: 0 8% 48px;
		gap: 28px;
		position: relative;

		#system,
		.news,
		.recommend {
			padding: 34px !important;
			margin: 0 !important;
			overflow: hidden;
			position: relative;

			&::before {
				content: '';
				position: absolute;
				inset: 0;
				background:
					radial-gradient(circle at top right, rgba(82, 208, 255, .12), transparent 26%),
					radial-gradient(circle at bottom left, rgba(126, 245, 212, .1), transparent 24%);
				pointer-events: none;
			}
		}

		#system {
			min-height: 520px;

			.system_item {
				display: grid !important;
				grid-template-columns: minmax(0, 1.2fr) minmax(360px, .8fr);
				gap: 28px !important;
				align-items: start;
			}

			.system_text {
				display: flex;
				flex-direction: column;
				gap: 22px;
			}

			.system_title_box .system_title,
			.news_title_box .news_title,
			.recommend_title_box .recommend_title {
				padding: 0 !important;
				margin-bottom: 18px !important;
				background: none !important;
				font-size: 34px !important;
				font-weight: 700 !important;
				line-height: 1.15 !important;
			}

			.system_content {
				padding: 24px !important;
				border: 1px solid rgba(120, 203, 255, .16);
				border-radius: 24px;
				background: rgba(245, 250, 255, .82) !important;
				line-height: 1.9 !important;
				width: 100% !important;
				margin: 0 !important;
				order: initial !important;
			}

			.system_media {
				width: 100% !important;
				margin: 0 !important;
				display: flex !important;
				flex-direction: column;
				gap: 18px;
				position: relative !important;
				order: initial !important;
			}

			.system_main img {
				border-radius: 28px;
				box-shadow: 0 20px 40px rgba(7, 25, 42, .16);
				width: 100% !important;
				height: 320px !important;
				object-fit: contain;
				object-position: center;
				background: #f2f3f5;
			}

			.system_side_cards {
				display: grid;
				grid-template-columns: repeat(2, minmax(0, 1fr));
				gap: 18px;
			}

			.system_side_cards img {
				border-radius: 22px;
				box-shadow: 0 16px 32px rgba(7, 25, 42, .14);
				width: 100% !important;
				height: 150px !important;
				object-fit: contain;
				object-position: center;
				background: #f2f3f5;
			}

			.system_more {
				margin: 4px 0 0 !important;
				justify-content: flex-start !important;
			}
		}

		.news {
			.list10 {
				gap: 24px;
				align-items: stretch;
			}

			.list10 .list-item1,
			.list10 .right_box .list-item2 {
				padding: 18px !important;
				border: 1px solid rgba(120, 203, 255, .14);
				border-radius: 24px;
				background: rgba(255,255,255,.82);
				box-shadow: 0 18px 32px rgba(8, 31, 54, .08);
			}

			.list10 .list-item1 img {
				border-radius: 20px;
				height: 260px !important;
			}

			.list10 .right_box {
				display: grid !important;
				gap: 18px;
			}

			.list10 .right_box .list-item2 {
				height: auto !important;
				padding: 20px !important;
			}

			.list10 .right_box .list-item2 .name,
			.list10 .list-item1 .name {
				white-space: normal !important;
				line-height: 1.5 !important;
			}

			.list10 .right_box .list-item2 .desc,
			.list10 .list-item1 .desc {
				white-space: normal !important;
				-webkit-line-clamp: 2;
				display: -webkit-box;
				-webkit-box-orient: vertical;
			}
		}

		.recommend {
			.list12 .list-body {
				display: grid;
				grid-template-columns: repeat(4, minmax(0, 1fr));
				gap: 22px;
				margin: 0 !important;
				width: 100% !important;
			}

			.list12 .item.list-item {
				padding: 16px 16px 18px !important;
				border: 1px solid rgba(120, 203, 255, .16);
				border-radius: 24px;
				background: rgba(255,255,255,.84);
				box-shadow: 0 16px 32px rgba(8, 31, 54, .09);
				overflow: hidden;
				width: 100% !important;
				margin: 0 !important;
			}

			.list12 .item.list-item img {
				border-radius: 18px;
				height: 220px !important;
				width: 100% !important;
			}

			.list12 .item.list-item .meta-line {
				align-items: flex-start !important;
				margin-top: 10px !important;
			}

			.list12 .item.list-item .meta-label {
				flex: 0 0 72px !important;
			}

			.list12 .item.list-item .meta-value {
				white-space: normal !important;
				word-break: break-word;
			}
		}

		.moreBtn {
			margin-top: 28px !important;
			border-radius: 18px !important;
			background: linear-gradient(135deg, #0e2e4f 0%, #19597a 100%) !important;
			box-shadow: 0 18px 34px rgba(11, 43, 71, .18);
			transition: transform .24s ease, box-shadow .24s ease;
		}

		.moreBtn:hover {
			transform: translateY(-2px);
			box-shadow: 0 22px 38px rgba(11, 43, 71, .24);
		}
	}

	@media (max-width: 1200px) {
		.home-preview #system .system_item {
			grid-template-columns: 1fr !important;
		}

		.home-preview .recommend .list12 .list-body {
			grid-template-columns: repeat(2, minmax(0, 1fr));
		}
	}
</style>
