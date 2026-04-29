const base = {
	get() {
		return {
			// 与 application.yml 中 server.port 一致（默认 8088）；开发时也可用当前 origin 走 8081 代理
			url: typeof window !== 'undefined' && window.location && window.location.origin
				? `${window.location.origin}/springboot76r81c0r/`
				: 'http://localhost:8088/springboot76r81c0r/',
			name: "springboot76r81c0r",
			// 退出到首页链接
			indexUrl: 'http://localhost:8082'
		};
	},
	getProjectName(){
		return {
			projectName: "校园安全教育管理平台"
		} 
	},
	getUsersName(){
		return {
			usersname: '管理员'
		}
	},
	getBgName() {
		return {
			bgNameList: [
				{
					name: '后台登录页',
					value: 'bLoginBackgroundImg'
				},
				{
					name: '后台注册页',
					value: 'bRegisterBackgroundImg'
				},
				{
					name: '后台首页',
					value: 'bIndexBackgroundImg'
				},
				{
					name: '后台头部LOGO',
					value: 'bTopLogo'
				},
				{
					name: '后台首页LOGO',
					value: 'bHomeLogo'
				},
				{
					name: '前台登录页',
					value: 'fLoginBackgroundImg'
				},
				{
					name: '前台注册页',
					value: 'fRegisterBackgroudImg'
				},
				{
					name: '前台头部LOGO',
					value: 'fTopLogo'
				},
				{
					name: 'APP登录页',
					value: 'appLoginBackgroundImg'
				},
				{
					name: 'APP注册页',
					value: 'appRegisterBackgroudImg'
				},
				{
					name: 'APP登录页LOGO',
					value: 'appLoginLogo'
				},
				{
					name: 'APP注册页LOGO',
					value: 'appRegLogo'
				},
			]
		}
	}
}
export default base
