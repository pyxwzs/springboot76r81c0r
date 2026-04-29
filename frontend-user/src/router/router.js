import VueRouter from 'vue-router'
//引入组件
import Index from '../pages'
import Home from '../pages/home/home'
import Login from '../pages/login/login'
import Register from '../pages/register/register'
import Center from '../pages/center/center'
import ExamPaper from '../pages/exam/examPaper'
import Exam from '../pages/exam/exam'
import ExamList from '../pages/exam/examList'
import ExamRecord from '../pages/exam/examRecord'
import AiStudy from '../pages/aiStudy/index'
import Storeup from '../pages/storeup/list'
import payList from '../pages/pay'

import yonghuList from '../pages/yonghu/list'
import yonghuDetail from '../pages/yonghu/detail'
import yonghuAdd from '../pages/yonghu/add'
import kechengleixingList from '../pages/kechengleixing/list'
import kechengleixingDetail from '../pages/kechengleixing/detail'
import kechengleixingAdd from '../pages/kechengleixing/add'
import kechengxinxiList from '../pages/kechengxinxi/list'
import kechengxinxiDetail from '../pages/kechengxinxi/detail'
import kechengxinxiAdd from '../pages/kechengxinxi/add'
import xuexijiluList from '../pages/xuexijilu/list'
import xuexijiluDetail from '../pages/xuexijilu/detail'
import xuexijiluAdd from '../pages/xuexijilu/add'
import yinhuanshangbaoList from '../pages/yinhuanshangbao/list'
import yinhuanshangbaoDetail from '../pages/yinhuanshangbao/detail'
import yinhuanshangbaoAdd from '../pages/yinhuanshangbao/add'
import chuzhifankuiList from '../pages/chuzhifankui/list'
import chuzhifankuiDetail from '../pages/chuzhifankui/detail'
import chuzhifankuiAdd from '../pages/chuzhifankui/add'
import News from '../pages/news/news-list'
import NewsDetail from '../pages/news/news-detail'
import examstatisticsList from '../pages/examstatistics/list'
import examstatisticsDetail from '../pages/examstatistics/detail'
import examstatisticsAdd from '../pages/examstatistics/add'
import systemintroList from '../pages/systemintro/list'
import systemintroDetail from '../pages/systemintro/detail'
import systemintroAdd from '../pages/systemintro/add'
import discusskechengxinxiList from '../pages/discusskechengxinxi/list'
import discusskechengxinxiDetail from '../pages/discusskechengxinxi/detail'
import discusskechengxinxiAdd from '../pages/discusskechengxinxi/add'

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}

//配置路由
export default new VueRouter({
	routes:[
		{
      path: '/',
      redirect: '/index/home'
    },
		{
			path: '/index',
			component: Index,
			children:[
				{
					path: 'home',
					component: Home
				},
				{
					path: 'center',
					component: Center,
				},
				{
					path: 'pay',
					component: payList,
				},
				{
					path: 'examPaper',
					component: ExamPaper
				},
				{
					path: 'examList',
					component:ExamList
				},
				{
					path: 'examRecord/:type',
					component:ExamRecord
				},
				{
					path: 'aiStudy',
					component: AiStudy
				},
				{
					path: 'storeup',
					component: Storeup
				},
				{
					path: 'yonghu',
					component: yonghuList
				},
				{
					path: 'yonghuDetail',
					component: yonghuDetail
				},
				{
					path: 'yonghuAdd',
					component: yonghuAdd
				},
				{
					path: 'kechengleixing',
					component: kechengleixingList
				},
				{
					path: 'kechengleixingDetail',
					component: kechengleixingDetail
				},
				{
					path: 'kechengleixingAdd',
					component: kechengleixingAdd
				},
				{
					path: 'kechengxinxi',
					component: kechengxinxiList
				},
				{
					path: 'kechengxinxiDetail',
					component: kechengxinxiDetail
				},
				{
					path: 'kechengxinxiAdd',
					component: kechengxinxiAdd
				},
				{
					path: 'xuexijilu',
					component: xuexijiluList
				},
				{
					path: 'xuexijiluDetail',
					component: xuexijiluDetail
				},
				{
					path: 'xuexijiluAdd',
					component: xuexijiluAdd
				},
				{
					path: 'yinhuanshangbao',
					component: yinhuanshangbaoList
				},
				{
					path: 'yinhuanshangbaoDetail',
					component: yinhuanshangbaoDetail
				},
				{
					path: 'yinhuanshangbaoAdd',
					component: yinhuanshangbaoAdd
				},
				{
					path: 'chuzhifankui',
					component: chuzhifankuiList
				},
				{
					path: 'chuzhifankuiDetail',
					component: chuzhifankuiDetail
				},
				{
					path: 'chuzhifankuiAdd',
					component: chuzhifankuiAdd
				},
				{
					path: 'news',
					component: News
				},
				{
					path: 'newsDetail',
					component: NewsDetail
				},
				{
					path: 'examstatistics',
					component: examstatisticsList
				},
				{
					path: 'examstatisticsDetail',
					component: examstatisticsDetail
				},
				{
					path: 'examstatisticsAdd',
					component: examstatisticsAdd
				},
				{
					path: 'systemintro',
					component: systemintroList
				},
				{
					path: 'systemintroDetail',
					component: systemintroDetail
				},
				{
					path: 'systemintroAdd',
					component: systemintroAdd
				},
				{
					path: 'discusskechengxinxi',
					component: discusskechengxinxiList
				},
				{
					path: 'discusskechengxinxiDetail',
					component: discusskechengxinxiDetail
				},
				{
					path: 'discusskechengxinxiAdd',
					component: discusskechengxinxiAdd
				},
			]
		},
		{
			path: '/login',
			component: Login
		},
		{
			path: '/register',
			component: Register
		},
		{
			path: '/exam',
			component: Exam
		}
	]
})
