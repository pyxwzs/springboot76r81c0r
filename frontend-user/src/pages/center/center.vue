<template>
	<div class="center-preview">
		<div class="center-title">{{ title }}</div>
		<div class="center-info">
			<div class="center-info-title">个人信息</div>

			<div class="img-box" v-if="userTableName=='yonghu'">
				<img :src="sessionForm.touxiang?baseUrl + sessionForm.touxiang:require('@/assets/avator.png')">
			</div>
			<div class="info-item1" v-if="userTableName=='yonghu'">
				<span class="icon iconfont icon-geren14"></span>
				<div class="label">账号：</div>
				<div class="text">{{sessionForm.zhanghao}}</div>
			</div>
			<div class="info-item2" v-if="userTableName=='yonghu'">
				<span class="icon iconfont icon-fenxiang"></span>
				<div class="label">姓名：</div>
				<div class="text">{{sessionForm.xingming}}</div>
			</div>
			<div class="info-item3" v-if="userTableName=='yonghu'">
				<span class="icon iconfont icon-shijian16"></span>
				<div class="label">性别：</div>
				<div class="text">{{sessionForm.xingbie}}</div>
			</div>
			<div class="info-item4" v-if="userTableName=='yonghu'">
				<span class="icon iconfont icon-qianshuxieyi"></span>
				<div class="label">手机号码：</div>
				<div class="text">{{sessionForm.shoujihaoma}}</div>
			</div>
		
		</div>
	
		<div class="center-box">
			<div class="center-tab-view">
				<div class="center-tab" :class="activeName==title2?'is-active':''" @click="handleClick(title2)">{{title2}}</div>
				<div class="center-tab" :class="activeName=='修改密码'?'is-active':''" @click="handleClick('修改密码')">修改密码</div>
				<div class="center-tab" v-if="hasBack(item.menu,item.child[0].tableName)" v-for="(item,index) in menuList" :key="index" @mouseenter="centerTabEnter(item.child[0].tableName)" @mouseleave="centerTabEnter('')" @click="menuClick(item.child[0],item.child.length)">
					<template v-if="item.child.length==1">
						{{item.child[0].menu}}
					</template>
					<template v-else>
						{{item.menu}}
						<transition name="el-fade-in-linear">
							<div class="center-second-tab-view" v-if="showActive=='show' + item.child[0].tableName">
								<div class="center-second-tab" v-for="(items,indexs) in item.child" :key="indexs" @click="menuClick(items)">
									{{items.menu}}
								</div>
							</div>
						</transition>
					</template>
				</div>
				<div v-if="isBackAuth('examrecord','查看')" class="center-tab" :class="activeName=='考试记录'?'is-active':''" @click="handleClick('考试记录')">考试记录</div>
				<div v-if="isBackAuth('examfailrecord','查看')" class="center-tab" :class="activeName=='错题本'?'is-active':''" @click="handleClick('错题本')">错题本</div>


			</div>
			<div class="center-content-box">
				<div class="center-content-view" v-show="activeName=='个人中心'">
					<el-form class="center-preview-pv" ref="sessionForm" :model="sessionForm" :rules="rules" label-width="180px">
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="账号" prop="zhanghao">
							<el-input v-model="sessionForm.zhanghao" placeholder="账号" :disabled="ro.zhanghao"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="姓名" prop="xingming">
							<el-input v-model="sessionForm.xingming" placeholder="姓名" :disabled="ro.xingming"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="性别" prop="xingbie">
							<el-select filterable v-model="sessionForm.xingbie" placeholder="请选择性别" :disabled="ro.xingbie">
								<el-option v-for="(item, index) in dynamicProp.xingbie" :key="index" :label="item" :value="item"></el-option>
							</el-select>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="手机号码" prop="shoujihaoma">
							<el-input v-model="sessionForm.shoujihaoma" placeholder="手机号码" :disabled="ro.shoujihaoma"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="头像" prop="touxiang">
							<file-upload
								tip="点击上传头像"
								action="file/upload"
								:limit="1"
								:multiple="true"
								:fileUrls="sessionForm.touxiang?sessionForm.touxiang:''"
								@change="yonghutouxiangHandleAvatarSuccess"
								></file-upload>
						</el-form-item>
						<el-form-item class="center-btn-item">
							<div class="updateBtn" type="primary" @click="onSubmit('sessionForm')">
								<span class="icon iconfont icon-xiugai13"></span>
								<span class="text">保存信息</span>
							</div>
							<div class="closeBtn" type="danger" @click="logout">
								<span class="icon iconfont icon-shanchu7"></span>
								<span class="text">退出登录</span>
							</div>
						</el-form-item>
					</el-form>
				</div>
				<div class="center-content-view" v-show="activeName=='修改密码'">
					<el-form v-if="psdType==1" class="center-preview-pv" ref="passwordForm" :model="passwordForm" :rules="passwordRules" label-width="180px">
						<el-form-item class="center-item" label="原密码" prop="password">
							<el-input type="password" v-model="passwordForm.password" placeholder="原密码"></el-input>
						</el-form-item>
						<el-form-item class="center-item" label="新密码" prop="newpassword">
							<el-input type="password" v-model="passwordForm.newpassword" placeholder="新密码"></el-input>
						</el-form-item>
						<el-form-item class="center-item" label="确认密码" prop="repassword">
							<el-input type="password" v-model="passwordForm.repassword" placeholder="确认密码"></el-input>
						</el-form-item>
						<el-form-item class="center-btn-item">
							<div class="updateBtn" type="primary" @click="updatePassword">
								<span class="icon iconfont icon-xiugai13"></span>
								<span class="text">修改密码</span>
							</div>
						</el-form-item>
					</el-form>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import config from '@/config/config';
	import menu from '@/config/menu';
	import Vue from 'vue';
	export default {
		//数据集合
		data() {
			return {
				title: '个人中心',
				title2: '个人中心',
				showActive: '',
				activeName: '个人中心',
				baseUrl: config.baseUrl,
				sessionForm: {},
				ro: {},
				passwordForm: {},
				psdType: '1',
				passwordRules: {
					password: [
						{
							required: true,
							message: "密码不能为空",
							trigger: "blur"
						}
					],
					newpassword: [
						{
							required: true,
							message: "新密码不能为空",
							trigger: "blur"
						}
					],
					repassword: [
						{
							required: true,
							message: "确认密码不能为空",
							trigger: "blur"
						}
					]
				},
				rules: {},
				menuList: [],
				disabled: false,
				uploadUrl: config.baseUrl + 'file/upload',
				imageUrl: '',
				headers: {Token: localStorage.getItem('frontToken')},
				userTableName: localStorage.getItem('UserTableName'),
				dynamicProp: {},
			}
		},
		async created() {
			let menus = menu.list()
			for(let x in menus){
				if(menus[x].tableName == this.userTableName){
					for(let i in menus[x].backMenu){
						if(menus[x].backMenu[i].child&&menus[x].backMenu[i].child.length&&menus[x].backMenu[i].child[0].tableName.indexOf('exam')!=-1){
							menus[x].backMenu.splice(i,1)
						}
					}
					this.menuList = menus[x].backMenu
				}
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'zhanghao', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'mima', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'xingming', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'xingbie', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'shoujihaoma', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'touxiang', null);
			}

			if ('yonghu' == this.userTableName) {
				if (this.rules['zhanghao']){
					this.rules['zhanghao'].push({ required: true, message: '请输入账号', trigger: 'blur' })
				}else if(!this.rules['zhanghao']) {
					this.$set(this.rules, 'zhanghao', [{ required: true, message: '请输入账号', trigger: 'blur' }]);
				}
				if (this.rules['mima']){
					this.rules['mima'].push({ required: true, message: '请输入密码', trigger: 'blur' })
				}else if(!this.rules['mima']) {
					this.$set(this.rules, 'mima', [{ required: true, message: '请输入密码', trigger: 'blur' }]);
				}
				if (this.rules['xingming']){
					this.rules['xingming'].push({ required: true, message: '请输入姓名', trigger: 'blur' })
				}else if(!this.rules['xingming']) {
					this.$set(this.rules, 'xingming', [{ required: true, message: '请输入姓名', trigger: 'blur' }]);
				}
				this.$set(this.rules, 'shoujihaoma', [{ required: false, validator: this.$validate.isMobile, trigger: 'blur' }]);
				this.ro = {
					zhanghao: true,
					mima: false,
					xingming: false,
					xingbie: false,
					shoujihaoma: false,
					touxiang: false,
				}
			}

			this.init();
			await this.$http.get(`${localStorage.getItem('UserTableName')}/session`, {emulateJSON: true}).then(async res => {
				if (res.data.code == 0) {
					this.sessionForm = res.data.data
				}
			});
		},
		//方法集合
		methods: {
			init() {
				if ('yonghu' == this.userTableName) {
					this.dynamicProp.xingbie = '男,女'.split(',');
				}
			},
			setSession(){
				localStorage.setItem('sessionForm',JSON.stringify(this.sessionForm))
			},
			onSubmit(formName) {
				if(`yonghu` == this.userTableName && this.sessionForm.touxiang!=null){
					this.sessionForm.touxiang = this.sessionForm.touxiang.replace(new RegExp(this.$config.baseUrl,"g"),"");
				}
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$http.post(this.userTableName + '/update', this.sessionForm).then(res => {
							if (res.data.code == 0) {
								this.setSession()
								this.$message({
									message: '更新成功',
									type: 'success',
									duration: 1500
								});
							}
						});
					} else {
						return false;
					}
				});
			},
			yonghutouxiangHandleAvatarSuccess(fileUrls) {
				this.sessionForm.touxiang = fileUrls;
			},
			handleClick(name) {
				switch(name) {
					case '个人中心':
						this.activeName = name
						this.$router.push('/index/center');
						break;
					case '修改密码':
						this.activeName = name
						this.passwordForm = {
							password: '',
							newpassword: '',
							repassword: '',
						}
						this.psdType = '1'
						this.$forceUpdate()
						break;
					case '考试记录':
						this.$router.push('/index/examList');
						break;
					case '错题本':
						this.$router.push('/index/examRecord/0');
						break;
					case '我的点赞':
						localStorage.setItem('storeupType', 21);
						this.$router.push('/index/storeup');
						break;
					case '我的收藏':
						localStorage.setItem('storeupType', 1);
						this.$router.push('/index/storeup');
						break;
					case '我的评论':
						localStorage.setItem('storeupType', 81);
						this.$router.push('/index/storeup');
						break;
				}

				this.title = event.target.outerText;
			},
			async updatePassword(){
				this.$refs["passwordForm"].validate(async valid => {
					if (valid) {
						var password = "";
						if (this.sessionForm.mima) {
							password = this.sessionForm.mima;
						} else if (this.sessionForm.password) {
							password = this.sessionForm.password;
						}
						if (this.userTableName == 'yonghu') {
						}
						if (this.passwordForm.password != password) {
							this.$message.error("原密码错误");
							return;
						}
						if (this.passwordForm.newpassword != this.passwordForm.repassword) {
							this.$message.error("两次密码输入不一致");
							return;
						}
						if (this.passwordForm.newpassword == this.passwordForm.password) {
							this.$message.error("新密码与原密码相同！");
							return;
						}
						this.sessionForm.password = this.passwordForm.newpassword;
						this.sessionForm.mima = this.passwordForm.newpassword;
						this.$http.post(`${this.userTableName}/update`,this.sessionForm).then(({data})=>{
							if (data && data.code === 0) {
								this.$message({
									message: "修改密码成功,下次登录系统生效",
									type: "success",
									duration: 1500,
									onClose: () => {
									}
								});
								this.setSession()
							} else {
								this.$message.error(data.msg);
							}
						});
					}
				})
			},
			logout() {
				localStorage.clear();
				Vue.http.headers.common['Token'] = "";
				this.$router.push('/index/home');
				this.activeIndex = '0'
				localStorage.setItem('keyPath', this.activeIndex)
				this.$forceUpdate()
				this.$message({
					message: '登出成功',
					type: 'success',
					duration: 1500,
				});
			},
			hasBack(name,tablename){
				if(name=='试题库管理') {
					return false
				}
				if(name=='试题管理') {
					return false
				}
				if(tablename=='exampaperlist') {
					return false
				}
				if(name=='考试管理') {
					return false
				}
				if(tablename=='examrecord') {
					return false
				}
				if(tablename=='examfailrecord') {
					return false
				}
				if(name.indexOf('章节')!=-1&&tablename.substring(0,7)=='chapter') {
					return false
				}
				return true
			},
			menuClick(row,length=1) {
				if(length==1) {
					if(row.tableName=='storeup') {
						localStorage.setItem('storeupType', row.menuJump);
						this.$router.push('/index/storeup');
						return false
					}
					this.$router.push(`/index/${row.tableName}?centerType=1`);
				}
			},
			centerTabEnter(name){
				this.showActive = name?('show' + name):''
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.center-preview {
		padding: 0;
		margin: 10px auto 30px;
		background: none;
		display: flex;
		width: 100%;
		justify-content: flex-start;
		align-items: flex-start;
		position: relative;
		flex-wrap: wrap;
		.center-title {
			padding: 15px 10% 0 10%;
			margin: 0 0 20px;
			color: #000;
			background: none;
			font-weight: 500;
			display: block;
			width: 100%;
			font-size: 32px;
			line-height: 30px;
			text-align: center;
		}
		.center-info {
			padding: 50px 10% 70px calc(10% + 550px);
			margin: 0 auto;
			color: #555;
			font-size: 16px;
			border-color: #263c4430;
			min-height: 500px;
			border-radius: 0px;
			box-shadow: none;
			background: #fff;
			width: 80%;
			border-width: 2px 0;
			position: relative;
			border-style: dashed;
			height: auto;
			.center-info-title {
				padding: 0;
				margin: 20px 0 10px;
				color: #333;
				background: none;
				font-weight: 600;
				width: 100%;
				font-size: 26px;
				border-color: #efefef;
				border-width: 0px 0;
				line-height: 50px;
				border-style: solid;
				height: auto;
			}
			.img-box {
				top: 50px;
				left: 10%;
				width: 500px;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				position: absolute;
				border-style: solid;
				height: 300px;
				img {
					border-radius: 0;
					margin: 10px auto;
					object-fit: cover;
					display: block;
					width: 500px;
					border-color: #efefef;
					border-width: 0 0 0px 0;
					border-style: solid;
					height: 400px;
				}
			}
			.info-item1 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item2 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item3 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item4 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item5 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item6 {
				padding: 0 0px;
				display: inline-block;
				width: 100%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
		}
		.center-box {
			border-radius: 10px;
			padding: 0 10%;
			margin: 20px 0 0;
			background: none;
			width: 100%;
			.center-tab-view {
				padding: 10px 20px;
				background: none;
				display: inline-block;
				width: 100%;
				min-height: 66px;
				border-color: #f6f6f6;
				border-width: 0px;
				line-height: 1.5;
				border-style: groove;
				text-align: center;
			}
			.center-tab-view .center-tab {
				border: 0;
				padding: 0 0px;
				margin: 0 10px 10px 0;
				color: #000;
				font-weight: 500;
				display: inline-block;
				font-size: 16px;
				line-height: 45px;
				float: left;
				border-radius: 30px;
				background: none;
				position: relative;
				list-style: none;
				min-width: 100px;
				height: 45px;
				.center-second-tab-view {
					padding: 0 10px;
					z-index: 999;
					background: #fff;
					width: 100%;
					position: relative;
					.center-second-tab {
						color: #666;
						width: 100%;
						font-size: 15px;
						border-color: #eee;
						border-width: 0 0 1px;
						border-style: solid;
					}
					.center-second-tab:hover {
						cursor: pointer;
						color: #009899;
					}
				}
			}
			.center-tab-view .center-tab:hover {
				cursor: pointer;
				padding: 0 0px;
				color: #fff;
				background: #263c44;
				font-weight: 500;
				font-size: 16px;
				line-height: 45px;
				position: relative;
				text-align: center;
				height: 45px;
			}
			.center-tab-view .center-tab.is-active {
				cursor: pointer;
				padding: 0 0px;
				margin: 0 10px 10px 0;
				color: #fff;
				font-weight: 500;
				font-size: 16px;
				line-height: 45px;
				float: left;
				border-radius: 30px;
				background: #263c44;
				position: relative;
				text-align: center;
				min-width: 100px;
				height: 45px;
			}
			.center-content-box {
				padding: 30px 15% 30px 3%;
				overflow: hidden;
				background: #fff;
				width: 100%;
				clear: both;
			}
			.center-content-view {
				width: 100%;
			}
			.center-preview-pv {
				.center-item.el-form-item {
					padding: 0px;
					margin: 0 0 24px;
					background: none;
					::v-deep .el-form-item__label {
						padding: 0 10px 0 0;
						color: #666;
						white-space: nowrap;
						font-weight: 500;
						width: 180px;
						font-size: 16px;
						line-height: 40px;
						text-align: right;
					}
					.el-form-item__content {
						margin-left: 180px;
					}
					.el-input {
						width: 100%;
					}
					.el-input ::v-deep .el-input__inner {
						border: 1px solid #999;
						border-radius: 0px 0 0 0;
						padding: 0 12px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-input ::v-deep .el-input__inner[readonly="readonly"] {
						border: 0px solid #ddd;
						cursor: not-allowed;
						border-radius: 0px;
						padding: 0 12px;
						box-shadow: none;
						outline: none;
						color: rgba(85, 85, 127, 1.0);
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-select {
						width: 100%;
					}
					.el-select ::v-deep .el-input__inner {
						border: 1px solid #999;
						border-radius: 0px 0 0 0;
						padding: 0 10px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-select ::v-deep .is-disabled .el-input__inner {
						border: 0px solid #ddd;
						cursor: not-allowed;
						border-radius: 0px;
						padding: 0 10px;
						box-shadow: none;
						outline: none;
						color: #333;
						background: #eee;
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-date-editor {
						width: 100%;
					}
					.el-date-editor ::v-deep .el-input__inner {
						border: 1px solid #999;
						border-radius: 0px 0 0 0;
						padding: 0 10px 0 30px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-date-editor ::v-deep .el-input__inner[readonly="readonly"] {
						border: 0;
						cursor: not-allowed;
						border-radius: 0px;
						padding: 0 10px 0 30px;
						box-shadow: none;
						outline: none;
						color: #333;
						background: #eee;
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					::v-deep .el-upload--picture-card {
						background: transparent;
						border: 0;
						border-radius: 0;
						width: auto;
						height: auto;
						line-height: initial;
						vertical-align: middle;
					}
					::v-deep .upload .upload-img {
						border: 1px solid #999;
						cursor: pointer;
						border-radius: 0px 0 0 0;
						color: #999;
						width: 80px;
						font-size: 26px;
						line-height: 80px;
						text-align: center;
						height: 80px;
					}
					::v-deep .el-upload-list .el-upload-list__item {
						border: 1px solid #999;
						cursor: pointer;
						border-radius: 0px 0 0 0;
						color: #999;
						width: 80px;
						font-size: 26px;
						line-height: 80px;
						text-align: center;
						height: 80px;
						font-size: 14px;
						line-height: 1.8;
					}
					::v-deep .el-upload .el-icon-plus {
						border: 1px solid #999;
						cursor: pointer;
						border-radius: 0px 0 0 0;
						color: #999;
						width: 80px;
						font-size: 26px;
						line-height: 80px;
						text-align: center;
						height: 80px;
					}
					::v-deep .el-upload__tip {
						color: #666;
						font-size: 15px;
					}
					::v-deep .el-input__inner::placeholder {
						color: #123;
						font-size: 16px;
					}
					.editor {
						border: 0px solid #ddd;
						border-radius: 4px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 14px;
						line-height: 32px;
					}
					.editor ::v-deep.ql-toolbar {
						border: 0px solid #ddd;
						background: none;
						border-width: 1px 1px 0;
					}
					.editor ::v-deep.ql-container {
						border: 1px solid #ddd;
						background: none;
						min-height: 180px;
					}
					.editor ::v-deep.ql-container .ql-blank::before {
						color: #000;
					}
				}
				.center-btn-item {
					padding: 0;
					margin: 40px auto 0;
					background: none;
					width: 100%;
					text-align: center;
					.updateBtn {
						border: 0;
						cursor: pointer;
						border-radius: 30px;
						padding: 0 30px;
						margin: 0 30px 0 0;
						outline: none;
						background: #263c41;
						display: inline-block;
						width: auto;
						font-size: 16px;
						line-height: 40px;
						height: 40px;
						.icon {
							color: #000;
							display: none;
						}
						.text {
							color: #fff;
						}
					}
					.updateBtn:hover {
						opacity: 0.7;
						.icon {
							color: #000;
						}
						.text {
							color: #fff;
						}
					}
					.closeBtn {
						border: 0;
						cursor: pointer;
						border-radius: 30px;
						padding: 0 30px;
						margin: 0 30px 0 0;
						outline: none;
						background: #263c41;
						display: inline-block;
						width: auto;
						font-size: 16px;
						line-height: 40px;
						height: 40px;
						.icon {
							color: #333;
							display: none;
						}
						.text {
							color: #fff;
						}
					}
					.closeBtn:hover {
						opacity: 0.7;
						.icon {
						}
						.text {
						}
					}
				}
				.el-date-editor.el-input {
					width: auto;
				}
			}
		}
	}

	.center-preview {
		padding: 0 8% 48px;

		.center-title {
			padding: 8px 0 0 !important;
			margin-bottom: 24px !important;
			font-size: 38px !important;
			font-weight: 700 !important;
			letter-spacing: .04em;
		}

		.center-info {
			padding: 44px 48px 44px 48px !important;
			width: 100% !important;
			min-height: auto !important;
			border: 1px solid rgba(120, 203, 255, .16) !important;
			border-radius: 28px !important;
			background: rgba(255,255,255,.8) !important;
			box-shadow: 0 20px 44px rgba(8, 31, 54, .08);
			display: grid;
			grid-template-columns: 240px 1fr;
			column-gap: 36px;

			.center-info-title {
				grid-column: 2;
				margin: 0 0 18px !important;
				font-size: 28px !important;
			}

			.img-box {
				top: auto !important;
				left: auto !important;
				width: 100% !important;
				height: auto !important;
				position: relative !important;
				grid-row: 1 / span 6;

				img {
					width: 100% !important;
					height: 300px !important;
					border-radius: 24px !important;
					box-shadow: 0 18px 34px rgba(8, 31, 54, .14);
				}
			}

			[class^="info-item"] {
				padding: 12px 16px !important;
				border: 1px solid rgba(120, 203, 255, .12);
				border-radius: 18px;
				background: rgba(247, 251, 255, .78);
				line-height: 1.7 !important;
			}
		}

		.center-box {
			width: 100%;
			margin-top: 26px;
			border-radius: 28px;
			overflow: hidden;
		}

		.center-tab-view {
			padding: 16px !important;
			display: flex;
			flex-wrap: wrap;
			gap: 12px;
			background: rgba(255,255,255,.74);
			border-radius: 24px;
			box-shadow: 0 18px 44px rgba(8, 31, 54, .08);
		}

		.center-tab {
			padding: 0 22px !important;
			line-height: 46px !important;
			height: 46px !important;
			border-radius: 16px !important;
			border: 1px solid rgba(120, 203, 255, .12);
			background: rgba(247, 251, 255, .74);
		}

		.center-tab.is-active,
		.center-tab:hover {
			background: linear-gradient(135deg, rgba(82,208,255,.18), rgba(126,245,212,.14)) !important;
			color: #11314f !important;
			transform: translateY(-2px);
		}

		.center-content-box {
			margin-top: 18px;
			padding: 30px !important;
			border-radius: 24px;
		}

		.center-preview-pv {
			padding: 14px !important;
		}
	}

	@media (max-width: 1200px) {
		.center-preview .center-info {
			grid-template-columns: 1fr !important;
		}

		.center-preview .center-info .center-info-title {
			grid-column: auto !important;
		}

		.center-preview .center-info .img-box {
			grid-row: auto !important;
		}
	}
</style>
