/**
 * 静态资源与拼接图片地址用 baseUrl，须与后端 server.port、context-path 一致。
 * 开发：若用 npm run serve（8082），可改为与页同源走代理；直连后端则写 8088。
 */
export default {
	baseUrl: 'http://localhost:8088/springboot76r81c0r/',
	name: '/springboot76r81c0r',
	indexNav: [
		{
			name: '课程信息',
			url: '/index/kechengxinxi',
		},
		{
			name: '公告',
			url: '/index/news',
		},
		{
			name: '在线测试',
			url: '/index/examPaper',
		},
		{
			name: 'AI辅助学习',
			url: '/index/aiStudy',
		},
	],
	cateList: [
		{
			name: '课程信息',
			refTable: 'kechengleixing',
			refColumn: 'kechengleixing',
		},
	],
	payList: [],
}
