<template>
	<div>
		<div class="login-container" :style="{'backgroundImage': indexBgUrl?`url(${$config.baseUrl + indexBgUrl})`:''}">
			<el-form ref="loginForm" :model="loginForm" :rules="rules" class="login_form animate__animated animate__">
				<div class="login_form2">
					<div class="login-title">校园安全教育管理平台</div>
					<div v-if="loginType==1" class="list-item" prop="username">
						<div class="lable">
							账号：
						</div>
						<input v-model="loginForm.username" placeholder="请输入账号：">
					</div>
					<div v-if="loginType==1" class="list-item" prop="password">
						<div class="lable">
							密码：
						</div>
						<div class="password-box">
							<input v-model="loginForm.password" placeholder="请输入密码：" :type="showPassword?'text':'password'">
							<span class="icon iconfont" :class="showPassword?'icon-liulan13':'icon-liulan17'" @click="showPassword=!showPassword"></span>
						</div>
					</div>

					<div class="list-item" v-if="roles.length>1&&loginType<=2">
						<div class="lable">
							角色：
						</div>
						<div class="list-type" prop="role">
							<el-radio v-model="loginForm.tableName" :label="item.tableName" v-for="(item, index) in roles" :key="index" @change.native="getCurrentRow(item)">{{item.roleName}}</el-radio>
						</div>
					</div>

			
					<div class="list-btn">
						<el-button class="login_btn" v-if="loginType==1||loginType==3||loginType==4" @click="submitForm('loginForm')">登录</el-button>

						<div class="list-btn2">
							<router-link class="register_btn" :to="{path: '/register', query: {role: item.tableName,pageFlag:'register'}}" v-if="item.hasFrontRegister=='是'" v-for="(item, index) in roles2" :key="index">注册{{item.roleName.replace('注册','')}}</router-link>
						</div>
					</div>
				</div>
				<div class="idea1"></div>
				<div class="idea2"></div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import 'animate.css';
import menu from '@/config/menu'
export default {
	//数据集合
	data() {
		return {
            baseUrl: this.$config.baseUrl,
            loginType: 1,
			roleMenus: [],
			loginForm: {
				username: '',
				password: '',
				tableName: '',
			},
			role: '',
			roles: [],
			roles2: [],
			rules: {
				username: [
					{ required: true, message: '请输入账号', trigger: 'blur' }
				],
				password: [
					{ required: true, message: '请输入密码', trigger: 'blur' }
				]
			},
			codes: [{
				num: 1,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}, {
				num: 2,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}, {
				num: 3,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}, {
				num: 4,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}],
			flag: false,
			verifyCheck2: false,
			showPassword: false,
			indexBgUrl: ''
		}
	},
	components: {
	},
	created() {
		this.$http.get('config/info?name=fLoginBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
		this.roleMenus = menu.list()
		for(let item in this.roleMenus) {
			if(this.roleMenus[item].hasFrontLogin=='是') {
				this.roles.push(this.roleMenus[item]);
			}
			if(this.roleMenus[item].hasFrontRegister=='是') {
				this.roles2.push(this.roleMenus[item]);
			}
		}
		
	},
	mounted() {
	},
	//方法集合
	methods: {
		randomString() {
			var len = 4;
			var chars = [
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
				'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
				'3', '4', '5', '6', '7', '8', '9'
			]
			var colors = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f']
			var sizes = ['14', '15', '16', '17', '18']
			
			var output = []
			for (var i = 0; i < len; i++) {
				// 随机验证码
				var key = Math.floor(Math.random() * chars.length)
				this.codes[i].num = chars[key]
				// 随机验证码颜色
				var code = '#'
				for (var j = 0; j < 6; j++) {
					var key = Math.floor(Math.random() * colors.length)
					code += colors[key]
				}
				this.codes[i].color = code
				// 随机验证码方向
				var rotate = Math.floor(Math.random() * 45)
				var plus = Math.floor(Math.random() * 2)
				if (plus == 1) rotate = '-' + rotate
				this.codes[i].rotate = 'rotate(' + rotate + 'deg)'
				// 随机验证码字体大小
				var size = Math.floor(Math.random() * sizes.length)
				this.codes[i].size = sizes[size] + 'px'
			}
		},
		getCurrentRow(row) {
			this.role = row.roleName;
		},
		submitForm(formName) {
			if(this.loginType==1) {
				if (this.roles.length!=1) {
					if (!this.role) {
						this.$message.error("请选择登录用户类型");
						return false;
					}
				} else {
					this.role = this.roles[0].roleName;
					this.loginForm.tableName = this.roles[0].tableName;
				}
				if (!this.loginForm.username) {
					this.$message.error("请输入用户名");
					return;
				}
				if (!this.loginForm.password) {
					this.$message.error("请输入密码");
					return;
				}
			}

			this.loginPost(formName)
		},
		loginPost(formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					this.$http.get(`${this.loginForm.tableName}/login`, {params: this.loginForm}).then(res => {
						if (res.data.code === 0) {
							localStorage.setItem('frontToken', res.data.token);
							localStorage.setItem('UserTableName', this.loginForm.tableName);
							localStorage.setItem('username', this.loginForm.username);
							localStorage.setItem('frontSessionTable', this.loginForm.tableName);
							localStorage.setItem('frontRole', this.role);
							localStorage.setItem('keyPath', 0);
							this.$router.push('/');
							this.$message({
								message: '登录成功',
								type: 'success',
								duration: 1500,
							});
						} 
						else {
							this.$message.error(res.data.msg);
						}
					});
				} else {
					return false;
				}
			});
		},
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.login-container {
		background-repeat: no-repeat;
		background-size: cover !important;
		display: flex;
		width: 100%;
		min-height: 100vh;
		justify-content: center;
		align-items: center;
		background-position: center center;
		position: relative;
		background-image:
			linear-gradient(135deg, rgba(4, 16, 30, .78), rgba(6, 34, 56, .56)),
			url(~@/assets/chapter.jpg);
		overflow: hidden;

		&::before,
		&::after {
			content: '';
			position: absolute;
			border-radius: 999px;
			filter: blur(8px);
			pointer-events: none;
		}

		&::before {
			width: 320px;
			height: 320px;
			top: 10%;
			left: -120px;
			background: radial-gradient(circle, rgba(82, 208, 255, .35), rgba(82, 208, 255, 0));
			animation: floatOrb 10s ease-in-out infinite;
		}

		&::after {
			width: 360px;
			height: 360px;
			right: -120px;
			bottom: 8%;
			background: radial-gradient(circle, rgba(126, 245, 212, .26), rgba(126, 245, 212, 0));
			animation: floatOrb 12s ease-in-out infinite reverse;
		}

		.login_form {
			border: 1px solid rgba(120, 203, 255, .18);
			border-radius: 30px;
			padding: 46px 44px;
			box-shadow: 0 30px 60px rgba(5, 17, 31, .28);
			margin: 0 auto;
			z-index: 10;
			background: linear-gradient(145deg, rgba(10,27,46,.92) 0%, rgba(14,42,63,.72) 100%);
			backdrop-filter: blur(18px);
			width: 720px;
			height: auto;
			animation: riseIn .75s ease both;
			.login_form2 {
				width: 100%;
				text-align: center;
				.login-title {
					padding: 10px 0 24px;
					margin: 0 0 16px 0;
					text-shadow: none;
					color: #fff;
					background: none;
					width: 100%;
					font-size: 34px;
					font-weight: 700;
					letter-spacing: .08em;
					line-height: 1.5;
					text-align: center;
				}
				.list-item {
					margin: 0 auto 26px;
					display: flex;
					width: 100%;
					align-items: center;
					.lable {
						color: rgba(232, 245, 255, .88);
						width: 110px;
						font-size: 16px;
						font-weight: 500;
						line-height: 48px;
						text-align: right;
					}
					input {
						border: 1px solid rgba(130, 189, 235, .14);
						border-radius: 16px;
						padding: 0 16px;
						box-shadow: inset 0 1px 0 rgba(255,255,255,.08);
						color: #fff;
						flex: 1;
						background: rgba(255,255,255,.12);
						width: calc(100% - 100px);
						font-size: 16px;
						transition: border-color .24s ease, box-shadow .24s ease, transform .24s ease;
						height: 48px;
					}
					input:focus {
						border-color: rgba(126, 245, 212, .52);
						box-shadow: 0 0 0 4px rgba(82, 208, 255, .12);
						transform: translateY(-1px);
					}
					.password-box {
						flex: 1;
						display: flex;
						width: calc(100% - 100px);
						position: relative;
						align-items: center;
						input {
							padding-right: 44px;
						}
						input:focus {
							border-color: rgba(126, 245, 212, .52);
							box-shadow: 0 0 0 4px rgba(82, 208, 255, .12);
						}
						.iconfont {
							cursor: pointer;
							z-index: 1;
							color: rgba(255, 255, 255, .78);
							top: 0;
							font-size: 16px;
							line-height: 48px;
							position: absolute;
							right: 15px;
						}
					}
					input::placeholder {
						color: rgba(236, 246, 255, .56);
						font-size: 16px;
					}
				}
				.list-type {
					flex: 1;
					width: 100%;
					text-align: left;
					::v-deep .el-radio__input .el-radio__inner {
						background: rgba(53, 53, 53, 0);
						border-color: rgba(180, 214, 239, .48);
					}
					::v-deep .el-radio__input.is-checked .el-radio__inner {
						background: #7ef5d4;
						border-color: #7ef5d4;
					}
					::v-deep .el-radio__label {
						color: rgba(221, 239, 250, .72);
						font-size: 16px;
					}
					::v-deep .el-radio__input.is-checked+.el-radio__label {
						color: #fff;
						font-size: 16px;
						font-weight: 600;
					}
				}
				.list-btn {
					padding: 0;
					margin: 16px 0 0;
					display: flex;
					width: 100%;
					flex-wrap: wrap;
					justify-content: center;
					align-items: center;
					gap: 12px;
					text-align: center;
					.login_btn {
						cursor: pointer;
						padding: 0 20px;
						margin: 0;
						color: #fff;
						font-size: 18px;
						font-weight: 600;
						letter-spacing: .08em;
						transition: transform .24s ease, box-shadow .24s ease, filter .24s ease;
						border-radius: 18px;
						outline: none;
						background: linear-gradient(135deg, #32cfff 0%, #1a9ef2 54%, #6b7dff 100%);
						width: auto;
						min-width: 220px;
						height: 52px;
						box-shadow: 0 18px 34px rgba(50, 160, 242, .32);
					}
					.login_btn:hover {
						transform: translateY(-2px);
						filter: saturate(1.06);
					}
					.list-btn2 {
						padding: 0;
						margin: 0;
						display: flex;
						width: auto;
						justify-content: center;
						flex-wrap: wrap;
						gap: 10px;
						text-align: center;
						.register_btn {
							cursor: pointer;
							padding: 0 18px;
							margin: 0;
							color: #dff6ff;
							background: rgba(255,255,255,.08);
							border: 1px solid rgba(120, 203, 255, .14);
							border-radius: 14px;
							display: inline-block;
							text-decoration: none;
							font-size: 16px;
							line-height: 44px;
							height: 44px;
							transition: transform .24s ease, background .24s ease;
						}
						.register_btn:hover {
							transform: translateY(-2px);
							background: rgba(255,255,255,.14);
						}
						.resetpwd_btn {
							cursor: pointer;
							margin: 0;
							color: #999;
							background: none;
							text-decoration: none;
							font-size: 16px;
						}
						.resetpwd_btn:hover {
							opacity: 0.5;
						}
					}
				}
			}
			.idea1 {
				position: absolute;
				top: -40px;
				right: -20px;
				display: block;
				width: 140px;
				height: 140px;
				border-radius: 36px;
				background: linear-gradient(135deg, rgba(82,208,255,.18), rgba(126,245,212,.02));
				transform: rotate(22deg);
			}
			.idea2 {
				position: absolute;
				left: -30px;
				bottom: -30px;
				display: block;
				width: 180px;
				height: 180px;
				border-radius: 50%;
				background: radial-gradient(circle, rgba(106,124,255,.2), rgba(106,124,255,0));
			}
		}
	}

	@keyframes floatOrb {
		0%, 100% {
			transform: translateY(0);
		}
		50% {
			transform: translateY(-18px);
		}
	}

	@keyframes riseIn {
		from {
			opacity: 0;
			transform: translateY(28px) scale(.98);
		}
		to {
			opacity: 1;
			transform: translateY(0) scale(1);
		}
	}
</style>
