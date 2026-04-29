<template>
	<div class="main-containers">
		<div class="ambient ambient-a"></div>
		<div class="ambient ambient-b"></div>
		<div class="ambient ambient-c"></div>
		<div class="body-containers">
			<div class="top-container">
				<!-- info -->
				<div class="top_title">
					<div class="brand-mark"></div>
					<div class="brand-copy" @click="goMenu('/index/home')">
						<span class="brand-cn">校园安全教育管理平台</span>
						<span class="brand-en">Campus Safety Learning Hub</span>
					</div>
				</div>
				<div class="top_tel"></div>
			

				<el-dropdown class="dropdown-box" @command="handleCommand" trigger="click">
					<div class="el-dropdown-link" v-show="Token">
						<img class="top_avatar2" v-show="headportrait&&Token" :src="headportrait?baseUrl + headportrait:require('@/assets/avator.png')">
						<span class="top_label2">欢迎</span>
						<span class="top_nickname2">{{username}}</span>
						<span class="icon iconfont icon-xiala"></span>
					</div>
					<div class="el-dropdown-link" v-show="!Token">
						<div class="login-item" @click="toLogin">
							<span class="icon iconfont icon-touxiang03"></span>
							登录
						</div>
					</div>
					<el-dropdown-menu class="top-el-dropdown-menu" slot="dropdown" v-show="Token">
						<el-dropdown-item v-show="notAdmin" class="user-item" :command="'user'">
							<span class="icon iconfont icon-touxiang09"></span>
							个人中心
						</el-dropdown-item>
						<el-dropdown-item class="register-item" :command="'register'">
							<span class="icon iconfont icon-shanchu16"></span>
							退出
						</el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>
			</div>


			<div class="menu-preview">
				<div class="menu-list">
					<div class="menu-home" :class="activeMenu=='/index/home'?'menu-active':''" @click="goMenu('/index/home')">
						<div class="title">
							<span class="icon iconfont icon-home19"></span>
							<div class="text">首页</div>
						</div>
					</div>
					<div v-if="(item.url == '/index/examPaper'&&isBackAuth('exampaperlist','查看'))||item.url != '/index/examPaper'" class="menu-item" v-for="(item,index) in menuList" :key="index" @mouseenter="menuShowClick4(index)" @mouseleave="menuShowClick4(-1)" :class="activeMenu==item.url?'menu-active':''" @click.stop="goMenu(item.url)">
						<div class="title">
							<span :class="iconArr[index]"></span>
							<div class="text">{{item.name}}</div>
						</div>
						<transition name="el-zoom-in-top">
							<div v-if="showType4==index&&item.hasCate" class="menu-child-list">
								<div class="child-item" v-for="(items,indexs) in item.cateList" :key="indexs" @click.stop="cateClick(item.url,items)">{{items}}</div>
							</div>
						</transition>
					</div>
				</div>
			</div>

			<div class="banner-preview" v-if="carouselChange()">
				<div class="swiper-container mySwiper3">
					<div class="swiper-wrapper">
						<div class="swiper-slide" v-for="item in carouselList" :key="item.id">
							<div class="swiper-item">
								<el-image v-if="preHttp(item.value)" @click="carouselClick(item.url)" :src="item.value" fit="cover"></el-image>
								<el-image v-else @click="carouselClick(item.url)" :src="baseUrl + item.value" fit="cover"></el-image>
							</div>
						</div>
					</div>
					<div class="banner-hidden">
					</div>
					<!-- Add Pagination -->
					<div class="swiper-pagination"></div>
					<!-- Add Arrows -->
					<div class="swiper-button-next">
						<span class="icon iconfont icon-jiantou18"></span>
					</div>
					<div class="swiper-button-prev">
						<span class="icon iconfont icon-jiantou39"></span>
					</div>
				</div>
			</div>
			<router-view id="scrollView"></router-view>
			
			<div class="bottom-preview">
				<div class="footer"><div v-html="bottomContent"></div></div>
			</div>
		</div>
		
		<div class="audioAnimation-box" :class="showType?'audioAnimation-box1':''" v-if="audio.length"
			:style="{'transform':audio[audioIndex].lrc&&audio[audioIndex].lrc!=''?'rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 98px, 0px)':'rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 68px, 0px)'}"
			style="width: 100%;position: fixed;bottom: 0;left: 0;z-index: 99999;" @mouseover="showmouseover">
			<div @click="suoClick"
				style="position: absolute;top: -20px;right: 40%;background: #fff;border-radius: 50% 50% 0 0;font-size: 0;width: 30px;height: 30px;display: flex;justify-content: center;align-items: center;cursor: pointer;">
				<img v-if="suoType" style="width: 20px;height: 20px;" src="../assets/suo.png">
				<img v-else style="width: 20px;height: 20px;" src="../assets/jiesuo.png">
			</div>
			<aplayer :float="true" :volume="1" repeat="repeat-all" ref="aplayer" id="aplayer" :music="audio[audioIndex]" @timeupdate="timeChange" @play="playing"
				:list="audio" :showLrc="audio[audioIndex].lrc&&audio[audioIndex].lrc!=''?true:false" :listFolded="true" :autoplay="true" listMaxHeight="180px" :lrcType="1"></aplayer>
		</div>
	</div>
</template>

<script>
	import Vue from 'vue'
	import Swiper from "swiper";
	import axios from 'axios'
export default {
	data() {
		return {
			activeIndex: '0',
			baseUrl: '',
			carouselList: [],
			carouselForm: {
				inHome: true,
				inOther: true,
			},
			mySwiper3Timer: null,
			menuList: [],
			headportrait: localStorage.getItem('frontHeadportrait')?localStorage.getItem('frontHeadportrait'):'',
			Token: localStorage.getItem('frontToken'),
			username: localStorage.getItem('username'),
			notAdmin: localStorage.getItem('frontSessionTable')!='"users"',
			iconArr: [
				'el-icon-star-off',
				'el-icon-goods',
				'el-icon-warning',
				'el-icon-question',
				'el-icon-info',
				'el-icon-help',
				'el-icon-picture-outline-round',
				'el-icon-camera-solid',
				'el-icon-video-camera-solid',
				'el-icon-video-camera',
				'el-icon-bell',
				'el-icon-s-cooperation',
				'el-icon-s-order',
				'el-icon-s-platform',
				'el-icon-s-operation',
				'el-icon-s-promotion',
				'el-icon-s-release',
				'el-icon-s-ticket',
				'el-icon-s-management',
				'el-icon-s-open',
				'el-icon-s-shop',
				'el-icon-s-marketing',
				'el-icon-s-flag',
				'el-icon-s-comment',
				'el-icon-s-finance',
				'el-icon-s-claim',
				'el-icon-s-opportunity',
				'el-icon-s-data',
				'el-icon-s-check'
			],
			bottomContent: '',
			musicType: false,
			showTimer: null,
			showType: false,
			suoType:false,
			showType4: -1,
			indexBgUrl: '',
		}
	},
	async created() {
		this.$http.get('config/info?name=fTopLogo',).then(rs=>{this.indexLogoUrl = rs.data.data?rs.data.data.value:''})
		this.baseUrl = this.$config.baseUrl;
		this.menuList = this.$config.indexNav;
		this.getCarousel();
		if(localStorage.getItem('frontToken') && localStorage.getItem('frontToken')!=null) {
			this.getSession()
		}
		this.cateList = this.$config.cateList
		if(this.cateList.length){
			for(let x in this.menuList){
				for(let i in this.cateList){
					if(this.menuList[x].name==this.cateList[i].name){
						await this.$http.get(`option/${this.cateList[i].refTable}/${this.cateList[i].refColumn}`).then(rs=>{
							this.menuList[x].cateList = rs.data.data
							this.menuList[x].hasCate = true
						})
					}
				}
			}
		}
	},
	mounted() {
		this.activeIndex = localStorage.getItem('keyPath') || '0';
		this.musicType = localStorage.getItem('musicType') ? true : false;


		// banner
		setTimeout(()=>{
			this.mySwiper3Timer = new Swiper(".mySwiper3", {"navigation":{"nextEl":".swiper-button-next","prevEl":".swiper-button-prev"},"autoplay":{"delay":2500,"disableOnInteraction":false},"pagination":{"el":".swiper-pagination","clickable":true}})
		}, 500)

	},
	computed: {
		activeMenu() {
			const route = this.$route
			const {
				meta,
				path
			} = route
			// if st path, the sidebar will highlight the path you sete
			if (meta.activeMenu) {
				return meta.activeMenu
			}
			return path
		},
		audioIndex: {
			get() {
				return this.$store.state.app.audioIndex
			},
			set(val) {
				return this.$store.state.app.audioIndex = val
			}
		},
		audio: {
			get() {
				return this.$store.state.app.audio
			},
			set(val) {
				return this.$store.state.app.audio = val
			}
		},
	},
	watch: {
		$route(newValue) {
			let that = this
			let url = window.location.href
			let arr = url.split('#')
			for (let x in this.menuList) {
				if (newValue.path == this.menuList[x].url) {
					this.activeIndex = x
				}
			}
			this.Token = localStorage.getItem('frontToken')
			if(arr[1]!='/index/home'){
				var element = document.getElementById('scrollView');
				var distance = element.offsetTop;
				window.scrollTo( 0, distance )
			}else{
				window.scrollTo( 0, 0 )
			}
		},
		headportrait(){
			this.$forceUpdate()
		},
		audio(newValue) {
			this.audioIndex = this.$store.state.app.audioIndex
		},
		audioIndex() {
			this.showmouseover()
			this.$nextTick(() => {
				this.$refs.aplayer.play()
			})
			this.$forceUpdate()
		},
	},
	methods: {
		cateClick(url,fenlei){
			this.$router.push(url + '?homeFenlei=' + fenlei);
		},
		preHttp(str) {
			return str && str.substr(0,4)=='http';
		},

		async getSession() {
			await this.$http.get(`${localStorage.getItem('UserTableName')}/session`, {emulateJSON: true}).then(async res => {
				if (res.data.code == 0) {
					if(localStorage.getItem('UserTableName')== 'yonghu') {
						localStorage.setItem('frontExamName', res.data.data.xingming);
					}
					localStorage.setItem('sessionForm',JSON.stringify(res.data.data))
					localStorage.setItem('frontUserid', res.data.data.id);
					if(res.data.data.vip) {
						localStorage.setItem('vip', res.data.data.vip);
					}
					if(res.data.data.touxiang) {
						this.headportrait = res.data.data.touxiang
						localStorage.setItem('frontHeadportrait', res.data.data.touxiang);
					} else if(res.data.data.headportrait) {
						this.headportrait = res.data.data.headportrait
						localStorage.setItem('frontHeadportrait', res.data.data.headportrait);
					}
				}
			});
		},
		handleSelect(keyPath) {
			if (keyPath) {
				localStorage.setItem('keyPath', keyPath)
			}
		},
		toLogin() {
		  this.$router.push('/login');
		},
		async logout() {
			await this.$http.post(`${localStorage.getItem('frontSessionTable')}/logout`).then(rs=>{
				localStorage.clear();
				Vue.http.headers.common['Token'] = "";
				this.$router.push('/index/home');
				this.activeIndex = '0'
				localStorage.setItem('keyPath', this.activeIndex)
				this.Token = ''
				this.$forceUpdate()
				this.$message({
					message: '登出成功',
					type: 'success',
					duration: 1000,
				});
			})
		},
		getCarousel() {
			this.$http.get('config/list', {params: {type: 1,limit: 100,type: 1}}).then(res => {
				if (res.data.code == 0) {
					this.carouselList = res.data.data.list;
				}
			});
		},
		// 轮播图跳转
		carouselClick(url) {
			if (url) {
				if (url.indexOf('https') != -1) {
					window.open(url)
				} else {
					this.$router.push(url)
				}
			}
		},
		carouselChange(){
			let url = window.location.href
			let arr = url.split('#')
			return (this.carouselForm.inHome&&arr[1]=='/index/home')||(this.carouselForm.inOther&&arr[1]!='/index/home')
		},
		menuShowClick4(index){
			this.showType4 = index
		},
		goMenu(path) {
			this.$router.push(path);
		},
		handleCommand(name){
			if(name == 'register') {
				this.logout()
			}
			else if (name == 'user'){
				this.goMenu('/index/center')
			}
			else if (name == 'login'){
				this.toLogin()
			}
		},
		suoClick(){
			this.suoType = !this.suoType
			if(this.suoType){
				clearTimeout(this.showTimer)
			}else{
				this.showmouseover()
			}
		},
		showmouseover() {
			if(this.suoType){
				return false
			}
			let that = this
			clearTimeout(this.showTimer)
			this.showType = true
			this.showTimer = setTimeout(() => {
				that.$refs.aplayer.showList = false;
				that.showType = false

			}, 6000)
		},
		timeChange(e){
			let user = JSON.parse(localStorage.getItem('sessionForm'))
			if(e.target.currentTime>30&&this.audio[this.audioIndex].isfree==0&&(!user||!user.vip||(user.vip&&user.vip!='是'))){
				this.$refs.aplayer.pause()
				if(this.audioIndex==this.audio.length - 1){
					this.$store.state.app.audioIndex = 0
				}else{
					this.$store.state.app.audioIndex++
				}
			}
		},
		playing(e){
			for(let x in this.audio) {
				if(this.audio[x].id == this.$refs.aplayer.currentMusic.id) {
					this.$store.state.app.audioIndex = Number(x)
					break
				}
			}
		},
	}
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.top-el-dropdown-menu {
		border: 1px solid rgba(120, 203, 255, 0.2);
		border-radius: 18px;
		padding: 8px 0;
		box-shadow: 0 22px 40px rgba(4, 18, 34, .16);
		margin: 18px 0;
		background: rgba(255, 255, 255, .92);
		backdrop-filter: blur(14px);
		.user-item,
		.register-item {
			padding: 0 12px;
			margin: 0 10px;
			color: #173049;
			font-size: 14px;
			line-height: 38px;
			height: 38px;
			border-radius: 12px;
			transition: background .24s ease, color .24s ease, transform .24s ease;
			.icon {
				color: inherit;
				font-size: 14px;
			}
		}
		.user-item:hover,
		.register-item:hover {
			color: #0f4e71;
			background: rgba(82, 208, 255, .12);
			transform: translateX(2px);
		}
	}

	.main-containers {
		position: relative;
		overflow: hidden;

		.ambient {
			position: fixed;
			border-radius: 999px;
			filter: blur(10px);
			pointer-events: none;
			opacity: .58;
			z-index: 0;
			animation: floatGlow 9s ease-in-out infinite;
		}

		.ambient-a {
			top: 90px;
			left: -80px;
			width: 280px;
			height: 280px;
			background: radial-gradient(circle, rgba(82, 208, 255, .35), rgba(82, 208, 255, 0));
		}

		.ambient-b {
			top: 320px;
			right: -120px;
			width: 320px;
			height: 320px;
			background: radial-gradient(circle, rgba(126, 245, 212, .28), rgba(126, 245, 212, 0));
			animation-duration: 12s;
		}

		.ambient-c {
			top: 58%;
			left: 12%;
			width: 220px;
			height: 220px;
			background: radial-gradient(circle, rgba(106, 124, 255, .16), rgba(106, 124, 255, 0));
			animation-duration: 11s;
		}

		.body-containers {
			padding: 144px 0 0;
			margin: 0;
			min-height: 100vh;
			position: relative;
			z-index: 1;

			.top-container {
				padding: 18px 8%;
				z-index: 1002;
				color: #f1fbff;
				display: flex;
				font-size: 15px;
				top: 0;
				left: 0;
				background: rgba(7, 18, 33, .78);
				backdrop-filter: blur(16px);
				border-bottom: 1px solid rgba(120, 203, 255, 0.16);
				width: 100%;
				justify-content: flex-end;
				align-items: center;
				position: fixed;
				height: 72px;
				box-shadow: 0 18px 40px rgba(4, 18, 34, .16);

				.top_title {
					top: 14px;
					left: 8%;
					display: flex;
					align-items: center;
					gap: 16px;
					position: absolute;

					.brand-mark {
						width: 42px;
						height: 42px;
						border-radius: 14px;
						background:
							linear-gradient(135deg, rgba(82, 208, 255, .9), rgba(126, 245, 212, .9));
						box-shadow: 0 14px 26px rgba(82, 208, 255, .28);
						position: relative;
						overflow: hidden;

						&::after {
							content: '';
							position: absolute;
							inset: 8px;
							border-radius: 10px;
							border: 1px solid rgba(255, 255, 255, .46);
						}
					}

					.brand-copy {
						display: flex;
						flex-direction: column;
						cursor: pointer;
					}

					.brand-cn {
						color: #f2fbff;
						font-weight: 700;
						font-size: 24px;
						line-height: 1.1;
						letter-spacing: .04em;
					}

					.brand-en {
						margin-top: 4px;
						color: rgba(210, 240, 255, .72);
						font-size: 11px;
						line-height: 1;
						letter-spacing: .24em;
						text-transform: uppercase;
					}
				}

				.dropdown-box {
					display: flex;

					.el-dropdown-link {
						display: flex;
						align-items: center;
						padding: 8px 14px;
						border-radius: 18px;
						background: rgba(255, 255, 255, .08);
						border: 1px solid rgba(120, 203, 255, .12);
						transition: transform .24s ease, background .24s ease;

						&:hover {
							transform: translateY(-2px);
							background: rgba(255, 255, 255, .12);
						}

						.top_avatar2 {
							border-radius: 50%;
							margin: 0 10px 0 0;
							object-fit: cover;
							width: 36px;
							height: 36px;
							border: 2px solid rgba(255, 255, 255, .2);
						}

						.top_label2 {
							color: rgba(255, 255, 255, .7);
							font-size: 14px;
							line-height: 1;
						}

						.top_nickname2 {
							margin-left: 6px;
							color: #fff;
							font-size: 16px;
							font-weight: 600;
						}

						.icon {
							margin: 0 0 0 8px;
							color: rgba(255, 255, 255, .72);
							font-size: 14px;
						}

						.login-item {
							display: flex;
							align-items: center;
							gap: 8px;
							color: #fff;
							font-size: 15px;
							line-height: 1;
						}
					}
				}
			}

			.menu-preview {
				margin: 0 auto;
				z-index: 100;
				color: #fff;
				top: 72px;
				background: rgba(9, 23, 40, .68);
				backdrop-filter: blur(16px);
				border-bottom: 1px solid rgba(120, 203, 255, .12);
				width: 100%;
				font-size: 17px;
				position: fixed;
				height: 72px;

				.menu-list {
					padding: 14px 8% 0;
					display: flex;
					justify-content: flex-end;
					gap: 8px;
					position: relative;

					.menu-home,
					.menu-item {
						color: rgba(240, 251, 255, .8);
						line-height: 44px;
						height: 44px;

						.title {
							cursor: pointer;
							padding: 0 18px;
							margin: 0;
							border-radius: 16px;
							display: flex;
							align-items: center;
							transition: transform .24s ease, background .24s ease, color .24s ease, box-shadow .24s ease;

							span {
								display: none;
							}

							.text {
								padding: 0;
								color: inherit;
								white-space: nowrap;
								font-size: inherit;
								font-weight: 500;
							}
						}

						&:hover .title {
							color: #fff;
							background: linear-gradient(135deg, rgba(82, 208, 255, .22), rgba(126, 245, 212, .16));
							transform: translateY(-2px);
							box-shadow: 0 16px 30px rgba(5, 17, 31, .18);
						}

						&.menu-active .title {
							color: #fff;
							background: linear-gradient(135deg, rgba(82, 208, 255, .28), rgba(106, 124, 255, .24));
							box-shadow: inset 0 0 0 1px rgba(255, 255, 255, .12);
						}
					}

					.menu-item {
						position: relative;

						.menu-child-list {
							z-index: 9999;
							flex-direction: column;
							background: rgba(255,255,255,.92);
							backdrop-filter: blur(14px);
							border: 1px solid rgba(120, 203, 255, .14);
							border-radius: 18px;
							display: flex;
							width: 220px;
							justify-content: flex-start;
							position: absolute;
							top: 54px;
							left: 0;
							overflow: hidden;

							.child-item {
								cursor: pointer;
								padding: 0 18px;
								color: #173049;
								width: 100% !important;
								font-size: 15px;
								line-height: 46px;
								height: 46px;
								transition: background .22s ease, color .22s ease, padding-left .22s ease;
							}

							.child-item:hover {
								color: #0d5679;
								background: rgba(82, 208, 255, .12);
								padding-left: 24px;
							}
						}
					}
				}
			}

			.banner-preview {
				padding: 0 8%;
				margin: 0 auto 22px;
				width: 100%;
				position: relative;
				height: 640px;

				.swiper-button-prev:after,
				.swiper-button-next:after {
					display:none;
				}

				.swiper-container {
					height: 100%;
					border-radius: 0 0 34px 34px;
					overflow: hidden;
					box-shadow: 0 28px 56px rgba(6, 20, 36, .24);
				}

				.swiper-slide .swiper-item {
					width: 100%;
					height: 640px;
					position: relative;

					&::after {
						content: '';
						position: absolute;
						inset: 0;
						background:
							linear-gradient(180deg, rgba(4, 16, 30, .1), rgba(4, 16, 30, .38)),
							radial-gradient(circle at 80% 20%, rgba(82, 208, 255, .18), transparent 28%);
					}

					.el-image {
						object-fit: cover;
						width: 100%;
						height: 640px;
						transform: scale(1.02);
						transition: transform 4.8s ease;
					}
				}

				.swiper-slide-active .swiper-item .el-image {
					transform: scale(1.08);
				}

				.swiper-pagination {
					left: 0;
					bottom: 24px;
					width: 100%;

					::v-deep span.swiper-pagination-bullet {
						border-radius: 999px;
						margin: 0 5px;
						background: rgba(255, 255, 255, .38);
						display: inline-block;
						width: 28px;
						opacity: 1;
						height: 8px;
						transition: width .24s ease, background .24s ease;
					}

					::v-deep span.swiper-pagination-bullet.swiper-pagination-bullet-active {
						background: #fff;
						width: 46px;
					}
				}

				.swiper-button-next,
				.swiper-button-prev {
					width: 54px;
					height: 54px;
					border-radius: 50%;
					background: rgba(255, 255, 255, .14);
					backdrop-filter: blur(10px);
					box-shadow: 0 14px 26px rgba(5, 17, 31, .22);

					.icon {
						color: #fff;
						width: 24px;
						font-size: 24px;
						height: 24px;
					}
				}

				.swiper-button-next {
					margin: -27px 16px 0 0;
					right: 8%;
					top: 50%;
				}

				.swiper-button-prev {
					margin: -27px 0 0 16px;
					left: 8%;
					top: 50%;
				}
			}

			.bottom-preview {
				width: 100%;
				padding: 32px 8% 46px;

				.footer {
					padding: 30px 7%;
					color: rgba(233, 247, 255, .88);
					display: flex;
					font-size: 15px;
					min-height: 140px;
					overflow: hidden;
					align-content: center;
					background:
						linear-gradient(135deg, rgba(8, 25, 42, .96), rgba(14, 44, 73, .94));
					border: 1px solid rgba(120, 203, 255, .16);
					border-radius: 28px;
					box-shadow: 0 20px 44px rgba(4, 18, 34, .18);
					width: 100%;
					justify-content: center;
					align-items: center;
					text-align: center;
					line-height: 1.9;
				}
			}
		}
	}

	.audioAnimation-box {
		transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 68px, 0px);
		perspective: 1000px;
		transition: .3s ease;
	}

	.audioAnimation-box1 {
		transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0, 0px) !important;
	}

	@keyframes floatGlow {
		0%, 100% {
			transform: translate3d(0, 0, 0) scale(1);
		}
		50% {
			transform: translate3d(0, -18px, 0) scale(1.08);
		}
	}
</style>
