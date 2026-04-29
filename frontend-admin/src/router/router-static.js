	import Vue from 'vue';
//配置路由
	import VueRouter from 'vue-router'
	Vue.use(VueRouter);
//1.创建组件
	import Index from '@/views/index'
	import Home from '@/views/home'
	import Login from '@/views/login'
	import NotFound from '@/views/404'
	import UpdatePassword from '@/views/update-password'
	import pay from '@/views/pay'
	import register from '@/views/register'
	import center from '@/views/center'
	import adminexam from '@/views/modules/exampaperlist/exam'
	import exampaperlist from '@/views/modules/exampaperlist/list'
	import yonghu from '@/views/modules/yonghu/list'
	import kechengleixing from '@/views/modules/kechengleixing/list'
	import kechengxinxi from '@/views/modules/kechengxinxi/list'
	import xuexijilu from '@/views/modules/xuexijilu/list'
	import yinhuanshangbao from '@/views/modules/yinhuanshangbao/list'
	import chuzhifankui from '@/views/modules/chuzhifankui/list'
	import news from '@/views/modules/news/list'
	import exampaper from '@/views/modules/exampaper/list'
	import examquestion from '@/views/modules/examquestion/list'
	import examrecord from '@/views/modules/examrecord/list'
	import examstatistics from '@/views/modules/examstatistics/list'
	import syslog from '@/views/modules/syslog/list'
	import systemintro from '@/views/modules/systemintro/list'
	import users from '@/views/modules/users/list'
	import discusskechengxinxi from '@/views/modules/discusskechengxinxi/list'
	import examfailrecord from '@/views/modules/examfailrecord/list'
import config from '@/views/modules/config/list'

//2.配置路由   注意：名字
export const routes = [{
	path: '/',
	name: '系统首页',
	component: Index,
	children: [{
		// 这里不设置值，是把main作为默认页面
		path: '/',
		name: '系统首页',
		component: Home,
		meta: {icon:'', title:'center', affix: true}
	}, {
		path: '/updatePassword',
		name: '修改密码',
		component: UpdatePassword,
		meta: {icon:'', title:'updatePassword'}
	}, {
		path: '/pay',
		name: '支付',
		component: pay,
		meta: {icon:'', title:'pay'}
	}, {
		path: '/center',
		name: '个人信息',
		component: center,
		meta: {icon:'', title:'center'}
	}
	,{
		path: '/yonghu',
		name: '用户',
		component: yonghu
	}
	,{
		path: '/kechengleixing',
		name: '课程类型',
		component: kechengleixing
	}
	,{
		path: '/kechengxinxi',
		name: '课程信息',
		component: kechengxinxi
	}
	,{
		path: '/xuexijilu',
		name: '学习记录',
		component: xuexijilu
	}
	,{
		path: '/xuexijilustat',
		name: '学习记录统计',
		component: xuexijilu
	}
	,{
		path: '/yinhuanshangbao',
		name: '隐患上报',
		component: yinhuanshangbao
	}
	,{
		path: '/chuzhifankui',
		name: '处置反馈',
		component: chuzhifankui
	}
	,{
		path: '/news',
		name: '公告',
		component: news
	}
	,{
		path: '/exampaper',
		name: '在线测试',
		component: exampaper
	}
	,{
		path: '/examquestion',
		name: '试题',
		component: examquestion
	}
	,{
		path: '/examrecord',
		name: '测试记录',
		component: examrecord
	}
	,{
		path: '/examstatistics',
		name: '测试统计',
		component: examstatistics
	}
	,{
		path: '/syslog',
		name: '系统日志',
		component: syslog
	}
	,{
		path: '/systemintro',
		name: '系统简介',
		component: systemintro
	}
	,{
		path: '/users',
		name: '管理员',
		component: users
	}
	,{
		path: '/discusskechengxinxi',
		name: '课程信息评论',
		component: discusskechengxinxi
	}
	,{
		path: '/config/:type',
		name: '配置管理',
		component: config
	}
	,{
		path: '/examfailrecord',
		name: '错题本',
		component: examfailrecord
	}
	,{
		path: '/exampaperlist',
		name: '试卷列表',
		component: exampaperlist
	}
	]
	},
	{
		path: '/adminexam',
		name: 'adminexam',
		component: adminexam,
		meta: {icon:'', title:'adminexam'}
	},
	{
		path: '/login',
		name: 'login',
		component: Login,
		meta: {icon:'', title:'login'}
	},
	{
		path: '/register',
		name: 'register',
		component: register,
		meta: {icon:'', title:'register'}
	},
	{
		path: '*',
		component: NotFound
	}
]
//3.实例化VueRouter  注意：名字
const router = new VueRouter({
	mode: 'hash',
	/*hash模式改为history*/
	routes // （缩写）相当于 routes: routes
})
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}
export default router;
