<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'：'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index" :to="'/index/kechengxinxi?centerType=' + (centerType?'1':'0')"><a>{{item.name}}</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item3"><a href="javascript:void(0);">详情</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui14"></span>
				<span class="text">返回</span>
			</el-button>
		</div>
		<div class="detail-preview">
			<div class="attr">
				<div class="info">
					<div class="title-item">
						<div class="detail-title">
							{{detail.kechengmingcheng}}
						</div>
						<div class="colectBtn" @click="storeup(1)" v-show="!isStoreup">
							<i class="icon iconfont icon-shoucang10"></i>
							<span class="text">收藏({{detail.storeupnum}})</span>
						</div>
						<div class="colectBtnActive" @click="storeup(-1)" v-show="isStoreup">
							<i class="icon iconfont icon-shoucang12"></i>
							<span class="text">已收藏({{detail.storeupnum}})</span>
						</div>
					</div>
					<div class="detail-main">
						<div class="meta-column">
							<div class="item">
								<div class="lable">课程类型</div>
								<div class="text "  >{{detail.kechengleixing}}</div>
							</div>
							<div class="item">
								<div class="lable">所属学科</div>
								<div class="text "  >{{detail.suoshuxueke}}</div>
							</div>
							<div class="item">
								<div class="lable">学分</div>
								<div class="text "  >{{detail.xuefen}}</div>
							</div>
							<div class="item">
								<div class="lable">总课时</div>
								<div class="text "  >{{detail.zongkeshi}}</div>
							</div>
							<div class="item">
								<div class="lable">授课教师</div>
								<div class="text "  >{{detail.shoukejiaoshi}}</div>
							</div>
							<div class="item">
								<div class="lable">授课时间</div>
								<div class="text "  >{{detail.shoukeshijian}}</div>
							</div>
							<div class="item">
								<div class="lable">授课地点</div>
								<div class="text "  >{{detail.shoukedidian}}</div>
							</div>
							<div class="item">
								<div class="lable">课程大纲</div>
								<div class="text ql-snow ql-editor outline-content" v-html="detail.kechengdagang"></div>
							</div>
							<div class="item">
								<div class="lable">点击次数</div>
								<div class="text "  >{{detail.clicknum}}</div>
							</div>
							<div class="btn_box">
								<el-button class="editBtn" v-if="btnAuth('kechengxinxi','修改')" @click="editClick">修改</el-button>
								<el-button class="delBtn" v-if="btnAuth('kechengxinxi','删除')" @click="delClick">删除</el-button>
								<el-button class="reserveBtn" 
									v-if="btnAuth('kechengxinxi','课程学习')" 
									@click="onAcross('xuexijilu','','','','')" type="warning">
									课程学习
								</el-button>
								<el-button class="editBtn" @click="allchapterClick()" type="warning" v-if="btnAuth('kechengxinxi','章节管理')">章节管理</el-button>
								<el-button class="editBtn" @click="discussClick()" type="warning" v-if="btnAuth('kechengxinxi','查看评论')">查看评论</el-button>
								<el-button v-if="centerType" class="editBtn" @click="onshelvesClick" type="warning">{{detail.onshelves==1?'下架':'上架'}}</el-button>
							</div>
						</div>
						<div class="media-column" v-if="detailBanner.length">
							<el-carousel trigger="click" indicator-position="inside" arrow="always" type="default" direction="horizontal" height="480px" :autoplay="false" :interval="3000" :loop="true">
								<el-carousel-item v-for="item in detailBanner" :key="item">
									<img :preview-src-list="[item]" v-if="item.substr(0,4)=='http'" :src="item" class="image">
									<img :preview-src-list="[baseUrl + item]" v-else :src="baseUrl + item" class="image">
								</el-carousel-item>
							</el-carousel>
						</div>
					</div>
				</div>
			</div>

			<div class="zancai">
				<div v-if="!isThumbsupnum && !isCrazilynum" class="zan" @click="thumbsupOrCrazily(21)">
					<i class="icon iconfont icon-zan11"></i>
					<span class="text">赞一下({{detail.thumbsupnum}})</span>
				</div>
				<div v-if="!isThumbsupnum && !isCrazilynum" class="cai" @click="thumbsupOrCrazily(22)">
					<i class="icon iconfont icon-cai01"></i>
					<span class="text">踩一下({{detail.crazilynum}})</span>
				</div>
				<div v-if="isThumbsupnum" class="zanActive" @click="cancelThumbsupOrCrazily(21)">
					<i class="icon iconfont icon-zan01"></i>
					<span class="text">已赞({{detail.thumbsupnum}})</span>
				</div>
				<div v-if="isCrazilynum" class="caiActive" @click="cancelThumbsupOrCrazily(22)">
					<i  class="icon iconfont icon-cai16"></i>
					<span class="text">已踩({{detail.crazilynum}})</span>
				</div>
			</div>

			<div class="video-wrapper">
				<div v-if="showVideoFallback" class="video-fallback">
					<div class="fallback-title">{{ videoFallbackTitle }}</div>
					<div class="fallback-desc">{{ videoFallbackMessage }}</div>
					<div class="fallback-actions">
						<el-button
							v-if="courseOfficialLink"
							type="primary"
							size="small"
							@click="openCourseOfficialLink">
							打开课程入口
						</el-button>
					</div>
				</div>
				<video
					v-show="!showVideoFallback"
					ref="courseVideo"
					class="course-video"
					:src="videoSrc"
					controls
					preload="metadata"
					playsinline
					webkit-playsinline
					@error="handleVideoError">
				</video>
			</div>

			<el-tabs class="detail-tabs" v-model="activeName" type="border-card" v-if="tabsNum>0" >
				<el-tab-pane label="评论" name="1">
					<el-form class="add commentForm" :model="form" :rules="rules" ref="form">
						<el-form-item class="item" label="评论" prop="content">
							<editor
								myQuillEditor="content"
								v-model="form.content" 
								class="editor" 
								action="file/upload">
							</editor>
						</el-form-item>
						<el-form-item class="commentBtn">
							<el-button class="submitBtn" type="primary" @click="submitForm('form')">立即提交</el-button>
							<el-button class="resetBtn" @click="resetForm('form')">重置</el-button>
						</el-form-item>
					</el-form>
				
					<div v-if="infoList.length" class="comment-list">
						<div class="comment-item" v-for="item in infoList" :key="item.id" @mouseenter="discussEnter(item.id)" :id="'discuss' + item.id"
							@mouseleave="discussLeave">
							<div class="istop" v-if="item.istop">
								<span class="icon iconfont icon-jiantou24"></span>
							</div>
							<div class="user">
								<el-image v-if="item.avatarurl" :size="50" :src="baseUrl + item.avatarurl"></el-image>
								<el-image v-if="!item.avatarurl" :size="50" :src="require('@/assets/touxiang.png')"></el-image>
								<div class="name">{{item.nickname}}</div>
							</div>
							<div class="comment-content-box">
								<div class="ql-snow ql-editor" v-html="item.content"></div>
								<div class="comment-time">{{item.addtime}}</div>
								<div class="zancai-box">
									<div v-if="!comcaiChange(item)" class="zan-item" :class="comzanChange(item)?'active':''" @click="comzanClick(item)">
										<span class="icon iconfont" :class="comzanChange(item)?'icon-zan11':'icon-zan07'"></span>
										<span class="label">{{comzanChange(item)?'已赞':'赞'}}</span>
										<span class="num">({{item.thumbsupnum}})</span>
									</div>
									<div v-if="!comzanChange(item)" class="cai-item" :class="comcaiChange(item)?'active':''" @click="comcaiClick(item)">
										<span class="icon iconfont" :class="comcaiChange(item)?'icon-cai16':'icon-cai01'"></span>
										<span class="label">{{comcaiChange(item)?'已踩':'踩'}}</span>
										<span class="num">({{item.crazilynum}})</span>
									</div>
								</div>
								<div class="comment-btn">
									<!-- <el-button :style='{"border":"0","cursor":"pointer","padding":"0 20px","margin":"0 10px","outline":"none","color":"#fff","borderRadius":"4px","background":"#263c44","width":"auto","lineHeight":"34px","fontSize":"14px","height":"34px"}'>回复</el-button> -->
									<el-button class="delBtn" v-if="showIndex==item.id&&userid==item.userid" @click="discussDel(item.id)">删除</el-button>
								</div>
							</div>
							<div class="comment-content-box" v-if="item.reply">
								回复：<span class="ql-snow ql-editor" v-html="item.reply"></span>
							</div>
						</div>
					</div>
				
					<el-pagination
						background
						id="pagination" class="pagination"
						:page-size="pageSize"
						prev-text="<"
						next-text=">"
						:hide-on-single-page="true"
						:layout='["total","prev","pager","next","sizes","jumper"].join()'
						:total="total"
						@current-change="curChange"
						@prev-click="prevClick"
						@next-click="nextClick"
						@size-change="sizeChange"
						></el-pagination>
				</el-tab-pane>
			</el-tabs>

		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import Swiper from "swiper";
	export default {
		//数据集合
		data() {
			return {
				tablename: 'kechengxinxi',
				baseUrl: '',
				breadcrumbItem: [
					{
						name: '课程信息'
					}
				],
				title: '',
				detailBanner: [],
				userid: Number(localStorage.getItem('frontUserid')),
				id: 0,
				detail: {},
				tabsNum: 1,
				activeName: '1',
				form: {
					content: '',
					userid: Number(localStorage.getItem('frontUserid')),
					nickname: localStorage.getItem('username'),
					avatarurl: '',
				},
				showIndex: -1,
				infoList: [],
				rules: {
					content: [
						{ required: true, message: '请输入内容', trigger: 'blur' }
					],
				},
				total: 1,
				pageSize: 10,
				totalPage: 1,
				storeupParams: {
					name: '',
					picture: '',
					refid: 0,
					tablename: 'kechengxinxi',
					userid: Number(localStorage.getItem('frontUserid'))
				},
				isStoreup: false,
				storeupInfo: {},
				isCrazilynum: false,
				isThumbsupnum: false,
				thumbsupOrCrazilyInfo: {},
				buynumber: 1,
				centerType: false,
				storeupType: false,
				videoSrc: '',
				showVideoFallback: false,
				videoFallbackTitle: '课程视频暂不可播放',
				videoFallbackMessage: '当前课程已提供课程简介与官方学习入口，可先阅读课程要点。',
				courseVideoMap: {
					'国家安全教育概论': 'upload/大学生国家安全教育.mp4',
					'灾难逃生与自救': 'upload/逃生与自救.mp4',
					'高校学生消防安全教育': 'upload/消防与安全教育.mp4',
					'走进消防': 'upload/消防与安全教育.mp4',
					'实验室安全教育': 'upload/实验室安全教育.mp4',
					'大学生网络安全与防范': 'upload/网络安全与诈骗.mp4',
					'大学生防电信网络诈骗': 'upload/网络安全与诈骗.mp4',
				},
			}
		},
		created() {
			if(this.$route.query.centerType&&this.$route.query.centerType!=0) {
				this.centerType = true
			}
			if(this.$route.query.storeupType&&this.$route.query.storeupType!=0) {
				this.storeupType = true
			}
			
			this.init();
		},
		mounted() {
		},
		beforeDestroy() {
			this.destroyPlayer()
		},
		computed: {
			username() {
				return localStorage.getItem('username')
			},
			courseOfficialLink() {
				return this.getCourseOfficialLink()
			}
		},
		//方法集合
		methods: {
			init() {
				this.id = this.$route.query.id
				this.baseUrl = this.$config.baseUrl;
				this.$http.get(this.tablename + '/detail/'  + this.id, {}).then(async res => {
					if (res.data.code == 0) {
						this.detail = res.data.data;
						this.title = this.detail.kechengmingcheng;
						if(this.detail.tupian) {
							this.detailBanner = this.detail.tupian.split(",w").length>1?[this.detail.tupian]:this.detail.tupian.split(',');
						}
						if(!this.detailBanner.length) {
							this.detailBanner = [
								'upload/security_course_8.svg',
								'upload/security_course_9.svg',
								'upload/security_course_7.svg'
							]
						}
						this.$forceUpdate();
						this.getDiscussList(1);
						if(localStorage.getItem('frontToken')){
							this.getStoreupStatus();
							this.getThumbsupOrCrazilyStatus();
						}

					}
				});
			},
			async onAcross(acrossTable,crossOptAudit,crossOptPay,statusColumnName,tips,statusColumnValue,type=1){
				localStorage.setItem('crossTable',`kechengxinxi`);
				localStorage.setItem('crossObj', JSON.stringify(this.detail));
				localStorage.setItem('statusColumnName',statusColumnName);
				localStorage.setItem('statusColumnValue',statusColumnValue);
				localStorage.setItem('tips',tips);
				if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
					var obj = JSON.parse(localStorage.getItem('crossObj'));
					for (var o in obj){
						if(o==statusColumnName && obj[o]==statusColumnValue){
							this.$message({
								type: 'error',
								message: tips,
								duration: 1500
							});
							return
						}
					}
				}
				this.$router.push({path: '/index/' + acrossTable + 'Add', query: {type: 'cross'}});
			},
			storeup(type) {
				if (type == 1 && !this.isStoreup) {
					this.storeupParams.name = this.title;
					this.storeupParams.picture = this.detailBanner[0];
					this.storeupParams.refid = this.detail.id;
					this.storeupParams.type = String(type);
					this.$http.post('storeup/add', this.storeupParams).then(res => {
						if (res.data.code == 0) {
							this.isStoreup = true;
							this.detail.storeupnum++
							this.$http.post('kechengxinxi/update', this.detail).then(res => {});
							this.$message({
								type: 'success',
								message: '收藏成功!',
								duration: 1500,
							});
						}
					});
				}
				if (type == -1 && this.isStoreup) {
					this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: '1', refid: this.detail.id, tablename: 'kechengxinxi', userid: Number(localStorage.getItem('frontUserid'))}}).then(res => {
						if (res.data.code == 0 && res.data.data.list.length > 0) {
							this.isStoreup = true;
							this.storeupInfo = res.data.data.list[0];
							let delIds = new Array();
							delIds.push(this.storeupInfo.id);
							this.$http.post('storeup/delete', delIds).then(res => {
								if (res.data.code == 0) {
									this.isStoreup = false;
									this.detail.storeupnum--
									this.$http.post('kechengxinxi/update', this.detail).then(res => {});
									this.$message({
										type: 'success',
										message: '取消成功!',
										duration: 1500,
									});
								}
							});
						}
					});
				}
			},
			getStoreupStatus(){
				if(localStorage.getItem("frontToken")) {
					this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: '1', refid: this.detail.id, tablename: 'kechengxinxi', userid: Number(localStorage.getItem('frontUserid'))}}).then(res => {
						if (res.data.code == 0 && res.data.data.list.length > 0) {
							this.isStoreup = true;
							this.storeupInfo = res.data.data.list[0];
						}
					});
				}
			},
			async thumbsupOrCrazily(type) {
				this.storeupParams.name = this.title;
				this.storeupParams.picture = this.detailBanner[0];
				this.storeupParams.refid = this.detail.id;
				this.storeupParams.type = String(type);
				await this.$http.post('storeup/add', this.storeupParams).then(res => {
					if (res.data.code == 0) {
						let detail = JSON.parse(JSON.stringify(this.detail))
						if (type == 21) detail.thumbsupnum = Number(detail.thumbsupnum) + 1;
						if (type == 22) detail.crazilynum = Number(detail.crazilynum) + 1;
						this.$http.post('kechengxinxi/update', detail).then(res => {
							this.detail = detail
						});
						this.getThumbsupOrCrazilyStatus();
						this.$message({
							type: 'success',
							message: '操作成功!',
							duration: 1500,
						});
						
					}
				});
				
			},
			async cancelThumbsupOrCrazily(type) {
				let delIds = new Array();
				delIds.push(this.thumbsupOrCrazilyInfo.id);
				await this.$http.post('storeup/delete', delIds).then(res => {
					if (res.data.code == 0) {
						let detail = JSON.parse(JSON.stringify(this.detail))
						if (type == 21) detail.thumbsupnum -= 1;
						if (type == 22) detail.crazilynum -= 1;
						this.$http.post('kechengxinxi/update', detail).then(res => {
							this.detail = detail
						});
						this.isThumbsupnum = false;
						this.isCrazilynum = false;
						this.$message({
							type: 'success',
							message: '取消成功!',
							duration: 1500,
						});
					}
				});
				
			},
			getThumbsupOrCrazilyStatus() {
				if(localStorage.getItem("frontToken")) {
					this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: '21', refid: this.detail.id, tablename: 'kechengxinxi', userid: Number(localStorage.getItem('frontUserid'))}}).then(res => {
						if (res.data.code == 0 && res.data.data.list.length > 0) {
							this.isThumbsupnum = true;
							this.thumbsupOrCrazilyInfo = res.data.data.list[0];
						}
					});
					this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: '22', refid: this.detail.id, tablename: 'kechengxinxi', userid: Number(localStorage.getItem('frontUserid'))}}).then(res => {
						if (res.data.code == 0 && res.data.data.list.length > 0) {
							this.isCrazilynum = true;
							this.thumbsupOrCrazilyInfo = res.data.data.list[0];
						}
					});
				}
			},
			curChange(page) {
				this.getDiscussList(page);
			},
			prevClick(page) {
				this.getDiscussList(page);
			},
			nextClick(page) {
				this.getDiscussList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getDiscussList(1);
			},
			// 返回按钮
			backClick(){
				if(this.storeupType){
					history.back()
				}else{
					let params = {}
					if(this.centerType){
						params.centerType = 1
					}
					this.$router.push({path: '/index/kechengxinxi', query: params});
				}
			},
			// 下载
			download(file ){
				if(!file) {
					this.$message({
						type: 'error',
						message: '文件不存在',
						duration: 1500,
					});
					return;
				}
				let arr = file.replace(new RegExp('upload/', "g"), "")
				axios.get(this.baseUrl + 'file/download?fileName=' + arr, {
					headers: {
						token: localStorage.getItem("frontToken")
					},
					responseType: "blob"
				}).then(({
					data
				}) => {
					const binaryData = [];
					binaryData.push(data);
					const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
						type: 'application/pdf;chartset=UTF-8'
					}))
					const a = document.createElement('a')
					a.href = objectUrl
					a.download = arr
					// a.click()
					// 下面这个写法兼容火狐
					a.dispatchEvent(new MouseEvent('click', {
						bubbles: true,
						cancelable: true,
						view: window
					}))
					window.URL.revokeObjectURL(data)
				},err=>{
					axios.get((location.href.split(this.$config.name).length>1 ? location.href.split(this.$config.name)[0] :'') + this.$config.name + 'file/download?fileName=' + arr, {
						headers: {
							token: localStorage.getItem("frontToken")
						},
						responseType: "blob"
					}).then(({
						data
					}) => {
						const binaryData = [];
						binaryData.push(data);
						const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
							type: 'application/pdf;chartset=UTF-8'
						}))
						const a = document.createElement('a')
						a.href = objectUrl
						a.download = arr
						// a.click()
						// 下面这个写法兼容火狐
						a.dispatchEvent(new MouseEvent('click', {
							bubbles: true,
							cancelable: true,
							view: window
						}))
						window.URL.revokeObjectURL(data)
					})
				})
			},
			destroyPlayer() {
				this.videoSrc = ''
				if (this.$refs.courseVideo) {
					this.$refs.courseVideo.pause()
					this.$refs.courseVideo.removeAttribute('src')
					this.$refs.courseVideo.load()
				}
			},
			setVideoFallback(title, message) {
				this.destroyPlayer()
				this.showVideoFallback = true
				this.videoFallbackTitle = title || '课程视频暂不可播放'
				this.videoFallbackMessage = message || '当前课程已提供课程简介与官方学习入口，可先阅读课程要点。'
			},
			getCourseOfficialLink() {
				if (!this.detail || !this.detail.kechengdagang) {
					return ''
				}
				const match = this.detail.kechengdagang.match(/href="([^"]+)"/i)
				return match ? match[1] : ''
			},
			resolveLocalCourseVideo() {
				const courseName = this.detail.kechengmingcheng || ''
				if (courseName && this.courseVideoMap[courseName]) {
					return this.courseVideoMap[courseName]
				}
				return ''
			},
			openCourseOfficialLink() {
				const link = this.courseOfficialLink
				if (!link) {
					this.$message.warning('当前课程未配置外部学习入口')
					return
				}
				window.open(link, '_blank')
			},
			handleVideoError() {
				this.setVideoFallback('课程视频加载失败', '已为当前课程保留课程简介与官方学习入口，可点击下方按钮继续学习。')
			},
			initDb(file) {
				this.destroyPlayer()
				const mappedVideo = this.resolveLocalCourseVideo()
				const finalVideo = mappedVideo || file
				if (!finalVideo) {
					this.setVideoFallback('当前课程未内置视频', '已保留课程要点与官方学习入口，可先阅读课程简介后继续学习。')
					return
				}
				this.showVideoFallback = false
				const token = localStorage.getItem('frontToken') || ''
				let url = finalVideo.substr(0,4) === 'http' ? finalVideo : this.baseUrl + finalVideo
				this.videoSrc = token && url.indexOf('?token=') === -1 ? `${url}?token=${token}` : url
			},
			saveDanmaku(content){
				this.addComments(content.text,2)
			},
			getDiscussList(page,type=1) {
				this.$http.get('discusskechengxinxi/list', {
					params: {
						page, 
						limit: this.pageSize, 
						refid: this.detail.id,
						sort: 'istop',
						order: 'desc',
					}
				}).then(res => {
					if (res.data.code == 0) {
						for(let x in res.data.data.list) {
							res.data.data.list[x].content = res.data.data.list[x].content.replace(/img src/gi,"img style=\"width:30%;\" src");
						}
						this.infoList = res.data.data.list;
						this.total = res.data.data.total;
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
						if(type==1) {
							this.initDb(this.detail.kechengshipin)
							if(this.$route.query.discussId&&this.$route.query.discussId!=0) {
								this.$nextTick(()=>{
									document.getElementById('discuss' + this.$route.query.discussId).scrollIntoView({
										behavior: 'smooth'
									});
								})
							}
						}
					}
				});
			},
			comzanChange(row){
				if(row.tuserids){
					let arr = row.tuserids.split(',')
					for(let x in arr){
						if(arr[x] == this.userid){
							return true
						}
					}
				}
				return false
			},
			comzanClick(row){
				if(!this.userid){
					return false
				}
				if(!this.comzanChange(row)){
					row.thumbsupnum++
					if(row.tuserids){
						row.tuserids = row.tuserids + ',' + this.userid
					}else {
						row.tuserids = String(this.userid)
					}
					this.$http.post('discusskechengxinxi/update',row).then(rs=>{
						this.$message.success('点赞成功')
					})
				}else {
					row.thumbsupnum--
					let arr = row.tuserids.split(',')
					for(let x in arr){
						if(arr[x] == this.userid){
							arr.splice(x,1)
						}
					}
					row.tuserids = arr.join(',')
					this.$http.post('discusskechengxinxi/update',row).then(rs=>{
						this.$message.success('取消成功')
					})
				}
			},
			comcaiChange(row){
				if(row.cuserids){
					let arr = row.cuserids.split(',')
					for(let x in arr){
						if(arr[x] == this.userid){
							return true
						}
					}
				}
				return false
			},
			comcaiClick(row){
				if(!this.userid){
					return false
				}
				if(!this.comcaiChange(row)){
					row.crazilynum++
					if(row.cuserids){
						row.cuserids = row.cuserids + ',' + this.userid
					}else {
						row.cuserids = String(this.userid)
					}
					this.$http.post('discusskechengxinxi/update',row).then(rs=>{
						this.$message.success('点踩成功')
					})
				}else {
					row.crazilynum--
					let arr = row.cuserids.split(',')
					for(let x in arr){
						if(arr[x] == this.userid){
							arr.splice(x,1)
						}
					}
					row.cuserids = arr.join(',')
					this.$http.post('discusskechengxinxi/update',row).then(rs=>{
						this.$message.success('取消成功')
					})
				}
			},
			discussEnter(index){
				this.showIndex = index
			},
			discussLeave(){
				this.showIndex = -1
			},
			discussDel(id){
				this.$confirm('是否删除此评论？').then(_ => {
					this.$http.post('discusskechengxinxi/delete',[id]).then(res=>{
						if(res.data&&res.data.code==0){
							this.addDiscussNum(1)
							this.$message({
								type: 'success',
								message: '删除成功!',
								duration: 1500,
								onClose: () => {
									this.getDiscussList(1);
								}
							});
						}
					})
				}).catch(_ => {});
			},
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.addComments()
					} else {
						return false;
					}
				});
			},
			addComments(content=null,type=1) {
				let data = JSON.parse(JSON.stringify(this.form))
				data.refid = this.detail.id;
				data.avatarurl = localStorage.getItem('frontHeadportrait')?localStorage.getItem('frontHeadportrait'):'';
				if(type==2) {
					data.content = content
				}
				this.$http.post('discusskechengxinxi/add', data).then(rs2 => {
					if (rs2.data.code == 0) {
						if(type==1) {
							this.form.content = '';
						}
						this.addDiscussNum(2)
						this.getDiscussList(1,type);
						this.$message({
							type: 'success',
							message: '评论成功!',
							duration: 1500,
						});
					}
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			},
			addDiscussNum(type){
				if(type==2){
					this.detail.discussnum++
				}else if(type==1){
					if(this.detail.discussnum!=0){
						this.detail.discussnum--
					}else{
						this.detail.discussnum = 0
					}
				}
				this.$http.post('kechengxinxi/update',this.detail).then(res=>{})
			},


			// 权限判断
			btnAuth(tableName,key){
				if(this.centerType){
					return this.isBackAuth(tableName,key)
				}else{
					return this.isAuth(tableName,key)
				}
			},
			// 修改
			editClick(){
				this.$router.push(`/index/kechengxinxiAdd?type=edit&&id=${this.detail.id}`);
			},
			// 删除
			async delClick(){
				await this.$confirm('是否删除此课程信息？') .then(_ => {
					this.$http.post('kechengxinxi/delete', [this.detail.id]).then(async res => {
						if (res.data.code == 0) {
							this.$http.get('storeup/list',{params:{
								page: 1,
								limit: 100,
								refid: this.detail.id,
								tablename: 'kechengxinxi',
							}}).then(async obj=>{
								if(obj.data&&obj.data.code==0){
									let arr = []
									for(let x in obj.data.data.list){
										arr.push(obj.data.data.list[x].id)
									}
									if(arr.length){
										await this.$http.post('storeup/delete',arr).then(()=>{})
									}
									this.$message({
										type: 'success',
										message: '删除成功!',
										duration: 1500,
										onClose: () => {
											history.back()
										}
									});
								}
							})
						}
					});
				}).catch(_ => {});
			},
			allchapterClick (){
				let params = {
					refid: this.detail.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/chapterkechengxinxi', query: params});
			},
			discussClick (){
				let params = {
					refid: this.detail.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/discusskechengxinxi', query: params});
			},
			onshelvesClick(){
				this.$confirm(`是否${this.detail.onshelves==1?'下架':'上架'}？`).then(async _ => {
					this.detail.onshelves = (this.detail.onshelves==1?0:1)
					await this.$http.post('kechengxinxi/update',this.detail).then(rs=>{
						this.$message.success('操作成功！')
					})
				}).catch(_ => {});
			},
			// 获取uuid
			getUUID() {
				return new Date().getTime();
			},
		},
		components: {
		},
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.detail-preview {
		padding-bottom: 44px;
		display: block !important;
	}

	.detail-preview .attr {
		display: block;
		margin-bottom: 0 !important;
	}

	.detail-preview .attr .info {
		margin: 0 !important;
	}

	.detail-preview .detail-main {
		display: grid;
		grid-template-columns: minmax(0, 1.35fr) minmax(360px, .85fr);
		gap: 18px;
		align-items: start;
	}

	.detail-preview .meta-column {
		min-width: 0;
	}

	.detail-preview .media-column {
		min-width: 0;
	}

	.detail-preview .attr .item {
		display: grid !important;
		grid-template-columns: 110px minmax(0, 1fr);
		column-gap: 12px;
		align-items: start;
	}

	.detail-preview .attr .item .text {
		line-height: 1.8 !important;
		word-break: break-word;
	}

	.detail-preview .el-carousel {
		margin-top: 0 !important;
		height: 100%;
		width: 100% !important;
	}

	.detail-preview ::v-deep .el-carousel__container {
		height: 560px !important;
	}

	.detail-preview ::v-deep .el-carousel__item {
		background: #f2f3f5;
	}
	.detail-preview ::v-deep .el-carousel__item .image {
		width: 100%;
		height: 100%;
		object-fit: contain;
		object-position: center;
		display: block;
	}

		.detail-preview .zancai {
			margin-top: 24px !important;
			display: flex !important;
			justify-content: center;
			gap: 24px;
		}

		.detail-preview .zancai > div {
			min-width: 170px;
			display: inline-flex;
			align-items: center;
			justify-content: center;
		}

	.detail-preview .video-wrapper {
		margin-top: 28px !important;
		padding: 12px;
		background: rgba(255, 255, 255, .88);
		width: min(1080px, 100%);
	}

	.detail-preview .detail-tabs {
		margin-top: 28px !important;
	}

	.detail-preview .detail-tabs ::v-deep .el-tabs__header {
		padding-left: 18px;
	}

	.detail-preview .detail-tabs ::v-deep .el-tabs__content {
		padding: 24px !important;
	}

	.detail-preview .commentForm {
		padding: 20px !important;
	}

	.detail-preview .commentBtn {
		padding-top: 18px;
	}

	@media (max-width: 1280px) {
		.detail-preview .detail-main {
			grid-template-columns: 1fr !important;
		}
	}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
	.detail-preview {
		padding: 0 10%;
		margin: 10px auto;
		display: flex;
		width: 100%;
		position: relative;
		flex-wrap: wrap;
		.attr {
			padding: 0;
			margin: 0 0 20px;
			background: none;
			flex: 1;
			display: flex;
			position: relative;
			order: 2;
			.info {
				border-radius: 8px;
				padding: 0px;
				margin: 0;
				background: #fff;
				flex: 1;
				.title-item {
					padding: 0;
					margin: 0 0 5px 0;
					background: none;
					display: flex;
					border-color: #ddd;
					border-width: 0 0 0px;
					justify-content: space-between;
					align-items: center;
					border-style: solid;
					.detail-title {
						padding: 0;
						color: #333;
						font-weight: 600;
						font-size: 18px;
						border-color: #009899;
						border-width: 0 0 0 0px;
						line-height: 1;
						border-style: solid;
					}
					.colectBtn {
						cursor: pointer;
						border: 0px solid #eee;
						border-radius: 30px;
						padding: 5px 15px;
						background: #fff;
						.icon {
							color: #666;
							font-size: 16px;
						}
						.text {
							color: #666;
							font-size: 16px;
						}
					}
					.colectBtnActive {
						border-radius: 30px;
						background: #f60;
						border-color: #f60;
						.icon {
							color: #fff;
							font-size: 16px;
						}
						.text {
							color: #fff;
							font-size: 16px;
						}
					}
					.colectBtn:hover {
						background: #f60;
						border-color: #f60;
						.icon {
							color: #fff;
						}
						.text {
							color: #fff;
						}
					}
					.colectBtnActive:hover {
						background: #f60;
						border-color: #f60;
						.icon {
							color: #fff;
						}
						.text {
							color: #fff;
						}
					}
				}
			.item {
					padding: 5px 0px;
					margin: 0 0 0px 0;
					background: none;
					display: inline-flex;
					width: 48%;
					justify-content: spaceBetween;
					.lable {
						padding: 0 10px 0 0;
						color: #333;
						white-space: nowrap;
						font-weight: 500;
						width: auto;
						font-size: 16px;
						line-height: 24px;
						text-align: right;
						height: auto;
					}
					.count-down {
						padding: 0 10px;
						color: #666;
						word-break: break-all;
						flex: 1;
						font-size: 14px;
						line-height: 24px;
						height: auto;
					}
					.text {
						padding: 0 10px;
						color: #666;
						word-break: break-all;
						flex: 1;
						font-size: 14px;
						line-height: 24px;
						height: auto;
					}
					.bold {
						font-weight: bold;
					}
					.link {
						cursor: pointer;
						text-decoration: underline;
					}
				}
				.item-price {
					.lable {
					}
					.text {
					}
				}
				.btn_box {
					padding: 10px 0;
					display: flex;
					flex-wrap: wrap;
				}
				.editBtn {
					border: 0;
					cursor: pointer;
					padding: 0 10px;
					margin: 0 0 0 10px;
					color: #fff;
					font-size: 14px;
					line-height: 40px;
					border-radius: 4px;
					outline: none;
					background: rgba(64, 158, 255, 1);
					width: auto;
					min-width: 80px;
					height: 40px;
				}
				.editBtn:hover {
					opacity: 0.7;
				}
				.delBtn {
					border: 0;
					cursor: pointer;
					padding: 0 10px;
					margin: 0 0 0 10px;
					color: #fff;
					font-size: 14px;
					line-height: 40px;
					border-radius: 4px;
					outline: none;
					background: rgba(255, 0, 0, 1.0);
					width: auto;
					min-width: 80px;
					height: 40px;
				}
				.delBtn:hover {
					opacity: 0.7;
				}
			}
			.outline-content {
				line-height: 1.8;
				::v-deep a {
					color: #0b6c88;
					font-weight: 600;
					text-decoration: underline;
				}
			}
		}
		.el-carousel {
			margin: 0 20px 0 0;
			width: 480px;
			position: relative;
			height: 480px;
			order: 2;
			::v-deep .el-carousel__indicator button {
				width: 0;
				height: 0;
				display: none;
			}
			::v-deep .el-carousel__container {
				.el-carousel__arrow--left {
					width: 36px;
					font-size: 12px;
					height: 36px;
				}
				.el-carousel__arrow--left:hover {
					background: #2ab685;
				}
				.el-carousel__arrow--right {
					width: 36px;
					font-size: 12px;
					height: 36px;
				}
				.el-carousel__arrow--right:hover {
					background: #2ab685;
				}
				.el-carousel__item {
					border-radius: 10px;
					width: 100%;
					height: 100%;
					background: #f2f3f5;
					img {
						object-fit: contain;
						object-position: center;
						width: 100%;
						height: 100%;
						display: block;
					}
				}
			}
		
			::v-deep .el-carousel__indicators {
				padding: 0;
				margin: 0 0 10px;
				z-index: 2;
				position: absolute;
				list-style: none;
				li {
					border-radius: 100%;
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 12px;
					opacity: 0.4;
					transition: 0.3s;
					height: 12px;
				}
				li:hover {
					border-radius: 100%;
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 12px;
					opacity: 0.7;
					height: 12px;
				}
				li.is-active {
					border-radius: 100%;
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 12px;
					opacity: 1;
					height: 12px;
				}
			}
		}
		.zancai {
			padding: 0;
			margin: 30px auto 20px;
			background: none;
			display: flex;
			width: 100%;
			justify-content: center;
			order: 3;
			.zan {
				cursor: pointer;
				border: 0px solid #eee;
				border-radius: 30px;
				padding: 10px 50px;
				margin: 0 100px 0 0;
				background: #f8f8f8;
				display: flex;
				width: auto;
				justify-content: center;
				align-items: center;
				.icon {
					margin: 0 3px;
					color: #666;
					font-size: 14px;
				}
				.text {
					margin: 0 3px;
					color: #666;
					font-size: 16px;
				}
			}
			.zan:hover {
				background: #263c44;
				border-color: #263c44;
				.icon {
					color: #fff;
				}
				.text {
					color: #fff;
				}
			}
			.zanActive {
				cursor: pointer;
				border: 1px solid rgba(107, 221, 184, .22);
				border-radius: 999px;
				padding: 10px 26px;
				margin: 0 20px 0 0;
				background: linear-gradient(135deg, #123247 0%, #24556a 100%);
				display: flex;
				width: auto;
				box-shadow: 0 10px 24px rgba(18, 50, 71, .16);
				justify-content: center;
				align-items: center;
				gap: 8px;
				.icon {
					color: #e8fff7;
					font-size: 18px;
				}
				.text {
					color: #f5fffb;
					font-size: 16px;
					font-weight: 600;
				}
			}
			.zanActive:hover {
				filter: brightness(1.05);
				transform: translateY(-1px);
			}
			
			.cai {
				cursor: pointer;
				border: 0px solid #eee;
				border-radius: 30px;
				padding: 10px 50px;
				margin: 0;
				background: #f8f8f8;
				display: flex;
				width: auto;
				justify-content: center;
				align-items: center;
				.icon {
					margin: 0 3px;
					color: #666;
					font-size: 14px;
				}
				.text {
					margin: 0 3px;
					color: #666;
					font-size: 16px;
				}
			}
			.cai:hover {
				background: #263c44;
				border-color: #263c44;
				.icon {
					color: #fff;
				}
				.text {
					color: #fff;
				}
			}
			.caiActive {
				cursor: pointer;
				border: 1px solid rgba(255, 164, 136, .28);
				border-radius: 999px;
				padding: 10px 26px;
				margin: 0;
				background: linear-gradient(135deg, #4a2b2a 0%, #7d4540 100%);
				display: flex;
				width: auto;
				justify-content: center;
				align-items: center;
				gap: 8px;
				box-shadow: 0 10px 24px rgba(92, 47, 42, .18);
				.icon {
					color: #ffe7df;
					font-size: 18px;
				}
				.text {
					color: #fff7f2;
					font-size: 16px;
					font-weight: 600;
				}
			}
			.caiActive:hover {
				filter: brightness(1.05);
				transform: translateY(-1px);
			}
		}
		.video-wrapper {
			border: 0;
			border-radius: 4px;
			margin: 10px auto;
			outline: none;
			display: block;
			width: 100%;
			min-height: 320px;
			order: 4;
			position: relative;
			background: #0d1b22;
			#dplayer {
				width: 100%;
				height: 100%;
			}
			.video-fallback {
				min-height: 320px;
				padding: 40px 32px;
				border-radius: 12px;
				background: linear-gradient(135deg, #18323b 0%, #274b56 100%);
				color: #fff;
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: flex-start;
				.fallback-title {
					font-size: 24px;
					font-weight: 600;
					line-height: 1.4;
				}
				.fallback-desc {
					margin-top: 12px;
					font-size: 15px;
					line-height: 1.8;
					max-width: 720px;
					color: rgba(255,255,255,.82);
				}
				.fallback-actions {
					margin-top: 18px;
				}
			}
		}
		video {
			border: 0;
			border-radius: 4px;
			margin: 10px auto;
			outline: none;
			display: block;
			width: 100%;
			order: 4;
		}
		.detail-tabs {
			border: 0px solid #DCDFE6;
			box-shadow: none;
			padding: 40px 0 0;
			margin: 20px auto;
			background: url(http://codegen.caihongy.cn/20251212/781734ff3c1449a98e8d232d05a13259.png) no-repeat left top,url(http://codegen.caihongy.cn/20251212/d4bbc6f0dfb24c919c7a1fa515a779f0.png) no-repeat right top,url(http://codegen.caihongy.cn/20251212/fb703d130fcb4c7aadb4bfdca3283a53.png) repeat-x center top;
			width: 100%;
			order: 5;
			& ::v-deep .el-tabs__header .el-tabs__nav-wrap {
				margin-bottom: 0;
			}
			::v-deep .el-tabs__header {
				padding: 8px 0;
				background: #fff;
				display: block;
				width: 100%;
				border-color: #ddd;
				border-width: 0 0 0px;
				line-height: 1.5;
				border-style: solid;
				text-align: center;
			}
			
			::v-deep .el-tabs__header .el-tabs__item {
				border: 0;
				padding: 0px;
				margin: 0 50px 0 0;
				color: #333;
				font-weight: 500;
				display: inline-block;
				font-size: 18px;
				border-color: #fff;
				line-height: 40px;
				border-radius: 0px 0 0 0;
				background: none;
				border-width: 0 0 4px;
				position: relative;
				border-style: solid;
				list-style: none;
				height: 40px;
			}
			
			::v-deep .el-tabs__header .el-tabs__item:hover {
				color: #000;
				background: none;
				border-color: #253b43;
			}
			
			::v-deep .el-tabs__header .el-tabs__item.is-active {
				border-radius: 0px 0 0 0;
				margin: 0 50px 0 0;
				color: #000;
				background: none;
				font-size: 18px;
				border-color: #253b43;
				border-width: 0 0 4px;
				border-style: solid;
			}
			
			::v-deep .el-tabs__content {
				padding: 15px 0;
				background: #fff;
				width: 100%;
				font-size: 16px;
			}
			.commentForm {
				box-shadow: none;
				padding: 0px;
				margin: 20px 0 20px;
				width: 100%;
				.item {
					display: block;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: auto;
					::v-deep .el-form-item__label {
						padding: 0 10px 0 0;
						color: #666;
						white-space: nowrap;
						width: 100%;
						font-size: 16px;
						line-height: 1;
						text-align: left;
					}
					.editor {
						border: 0px solid #ddd;
						border-radius: 4px;
						box-shadow: none;
						outline: none;
						margin: 30px 0 0 0;
						color: #333;
						width: 100%;
						clear: both;
						font-size: 14px;
						line-height: 32px;
						::v-deep .avatar-uploader {
							height: 0;
							line-height: 0;
						}
					}
					.editor ::v-deep.ql-toolbar {
						background: none;
					}
					.editor ::v-deep.ql-container {
						background: none;
						min-height: 180px;
					}
					.editor ::v-deep.ql-container .ql-blank::before {
						color: #999;
					}
				}
				.commentBtn {
					padding: 0;
					margin: 0 0 50px;
					width: 100%;
					text-align: center;
					height: auto;
					.submitBtn {
						border: 0;
						cursor: pointer;
						padding: 0 30px;
						margin: 0 20px 0 0;
						color: #fff;
						font-size: 15px;
						line-height: 45px;
						border-radius: 30px;
						outline: none;
						background: #263c44;
						width: auto;
						min-width: 180px;
						height: 45px;
					}
					.submitBtn:hover {
						opacity: 0.8;
					}
					.resetBtn {
						border: 0;
						cursor: pointer;
						padding: 0 30px;
						margin: 0 20px 0 0;
						color: #fff;
						font-size: 16px;
						line-height: 45px;
						border-radius: 30px;
						outline: none;
						background: #263c44;
						width: auto;
						min-width: 180px;
						height: 45px;
					}
					.resetBtn:hover {
						opacity: 0.8;
					}
				}
			}
			.comment-list {
				box-shadow: none;
				padding: 0px;
				margin: 30px 0 0 0;
				display: flex;
				width: 100%;
				justify-content: space-between;
				flex-wrap: wrap;
				.comment-item {
					border-radius: 10px;
					padding: 8px 10px;
					box-shadow: 0px 0px 6px #ddd;
					margin: 0 10px 20px;
					background: #f6f6f6;
					width: calc(33.33% - 20px);
					border-color: #eee;
					border-width: 0px;
					align-items: center;
					position: relative;
					border-style: solid;
					height: auto;
					.istop {
						cursor: pointer;
						box-shadow: none;
						top: 26px;
						background: none;
						position: absolute;
						right: 16px;
						.icon {
							color: #000;
							font-size: 24px;
						}
					}
					.user {
						padding: 10px;
						background: none;
						display: flex;
						width: 100%;
						border-color: #909090;
						border-width: 0 0 1px;
						align-items: center;
						border-style: solid;
						height: auto;
						.el-image {
							border-radius: 100%;
							margin: 0 10px 0 0;
							object-fit: cover;
							width: 40px;
							height: 40px;
						}
						.name {
							color: #333;
							font-weight: 500;
							font-size: 16px;
						}
					}
					.comment-time {
						padding: 0 10px;
						color: #666;
						width: 100%;
						text-align: right;
					}
					.comment-content-box {
						border-radius: 4px;
						padding: 0 8px;
						box-shadow: none;
						margin: 10px 0px 0px;
						word-wrap: break-word;
						color: #666;
						background: none;
						font-size: 14px;
						line-height: 24px;
						.zancai-box {
							margin: 8px 0 0 0;
							display: flex;
							width: 100%;
							justify-content: flex-end;
							align-items: center;
							height: 30px;
							.zan-item {
								margin: 0 10px 0 0;
								display: flex;
								align-items: center;
								.icon {
								font-size: 14px;
								}
								.label {
								display: none;
								font-size: 14px;
								}
								.num {
								font-size: 14px;
								}
							}
							.zan-item.active {
								margin: 0 10px 0 0;
								background: none;
								.icon {
								color: #263c44;
								font-size: 14px;
								}
								.label {
								color: #263c44;
								display: none;
								font-size: 14px;
								}
								.num {
								color: #263c44;
								font-size: 14px;
								}
							}
							.zan-item:hover {
								cursor: pointer;
								opacity: 0.8;
								.icon {
								color: #263c44;
								}
								.label {
								color: #263c44;
								}
								.num {
								color: #263c44;
								}
							}
							.cai-item {
								display: flex;
								align-items: center;
								.icon {
								font-size: 14px;
								}
								.label {
								display: none;
								font-size: 14px;
								}
								.num {
								font-size: 14px;
								}
							}
							.cai-item.active {
								background: none;
								.icon {
								color: #263c44;
								font-size: 14px;
								}
								.label {
								color: #263c44;
								display: none;
								font-size: 14px;
								}
								.num {
								color: #263c44;
								font-size: 14px;
								}
							}
							.cai-item:hover {
								cursor: pointer;
								opacity: 0.8;
								.icon {
								color: #263c44;
								}
								.label {
								color: #263c44;
								}
								.num {
								color: #263c44;
								}
							}
						}
						.comment-btn {
							margin: 8px 0 0 0;
							display: flex;
							width: 100%;
							justify-content: flex-end;
							align-items: center;
							height: 40px;
							.delBtn {
								border: 0;
								cursor: pointer;
								border-radius: 4px;
								padding: 0 20px;
								margin: 0 10px;
								outline: none;
								color: #fff;
								background: #ff0000;
								width: auto;
								font-size: 14px;
								line-height: 34px;
								height: 34px;
							}
						}
					}
				}
			}
		}
	}
</style>
