USE `springboot76r81c0r`;

SET FOREIGN_KEY_CHECKS=0;

DELETE FROM `discusskechengxinxi`;
DELETE FROM `storeup` WHERE `tablename`='kechengxinxi';
DELETE FROM `xuexijilu`;
DELETE FROM `examrecord`;
DELETE FROM `examstatistics`;
DELETE FROM `examquestion`;
DELETE FROM `exampaper`;
DELETE FROM `news`;
DELETE FROM `systemintro`;
DELETE FROM `kechengxinxi`;
DELETE FROM `kechengleixing`;

INSERT INTO `kechengleixing` (`id`,`addtime`,`kechengleixing`) VALUES
(1,NOW(),'安全通识教育'),
(2,NOW(),'国家安全教育'),
(3,NOW(),'消防与应急教育'),
(4,NOW(),'实验室与网络安全'),
(5,NOW(),'反诈与交通安全'),
(6,NOW(),'心理与生活安全');

INSERT INTO `kechengxinxi` (`id`,`addtime`,`kechengmingcheng`,`tupian`,`kechengleixing`,`suoshuxueke`,`xuefen`,`zongkeshi`,`shoukejiaoshi`,`shoukeshijian`,`shoukedidian`,`kechengdagang`,`kechengshipin`,`onshelves`,`thumbsupnum`,`crazilynum`,`clicktime`,`clicknum`,`discussnum`,`storeupnum`) VALUES
(1,NOW(),'大学生安全教育','upload/security_course_1.svg','安全通识教育','公共安全素养','2','16学时','中国海洋大学课程团队','自主学习','国家高等教育智慧教育平台 / 智慧树','<p>围绕宿舍安全、实验室规范、校园风险识别、突发事件报告等核心内容展开，帮助学生建立入校后的第一层安全防线。</p><p><strong>页面内置：</strong>课程导学短片，可直接在详情页播放。</p><p><strong>官方课程入口：</strong><a href="https://higher.smartedu.cn/course/6260b084f29a9e60d0f2518c" target="_blank">点击进入大学生安全教育</a></p>','upload/security_demo_a.mp4',1,18,0,NOW(),126,2,5),
(2,NOW(),'大学生国家安全教育','upload/security_course_2.svg','国家安全教育','国家安全教育','1','12学时','国际关系学院课程团队','自主学习','国家高等教育智慧教育平台 / 学堂在线','<p>聚焦总体国家安全观、保密纪律、涉外交流风险、网络空间国家安全等内容，帮助学生理解国家安全与个人责任的关系。</p><p><strong>页面内置：</strong>课程导学短片，可直接在详情页播放。</p><p><strong>官方课程入口：</strong><a href="https://higher.smartedu.cn/course/66a03196711dc30c34ab7795" target="_blank">点击进入大学生国家安全教育</a></p>','upload/security_demo_b.mp4',1,25,0,NOW(),168,1,8),
(3,NOW(),'灾难逃生与自救','upload/security_course_3.svg','消防与应急教育','应急管理与生命教育','2','18学时','同济大学课程团队','自主学习','中国大学 MOOC','<p>面向地震、火灾、踩踏、洪涝等灾害场景，系统介绍预警识别、疏散路线、自救互救和避险动作。</p><p><strong>页面内置：</strong>课程导学短片，可直接在详情页播放。</p><p><strong>官方课程入口：</strong><a href="https://www.icourse163.org/course/TONGJI-1001909011" target="_blank">点击进入灾难逃生与自救</a></p>','upload/security_demo_a.mp4',1,31,0,NOW(),205,3,10),
(4,NOW(),'高校学生消防安全教育','upload/security_course_4.svg','消防与应急教育','校园消防治理','1','10学时','中国消防新媒体矩阵','专题学习','国家消防救援局官网 / 中国消防','<p>重点覆盖宿舍火灾预防、消防设施识别、初起火灾扑救、逃生疏散和消防法律常识，适合高校日常消防教育使用。</p><p><strong>页面内置：</strong>课程导学短片，可直接在详情页播放。</p><p><strong>官方课程入口：</strong><a href="https://www.119.gov.cn/kp/hzyf/xx/2025/48800.shtml" target="_blank">点击进入高校学生消防安全教育</a></p>','upload/security_demo_b.mp4',1,34,0,NOW(),238,4,12),
(5,NOW(),'走进消防','upload/security_course_5.svg','消防与应急教育','消防认知与实践','1','8学时','国家消防救援局宣传资源','专题学习','国家消防救援局官网','<p>以消防常识普及、典型案例分析、火场避险技能和社会消防责任为主线，帮助学生建立“会预防、会报警、会逃生”的消防意识。</p><p><strong>页面内置：</strong>课程导学短片，可直接在详情页播放。</p><p><strong>官方课程入口：</strong><a href="https://www.119.gov.cn/qmxfxw/expd/index.shtml" target="_blank">点击进入走进消防</a></p>','upload/security_demo_a.mp4',1,15,0,NOW(),102,0,4),
(6,NOW(),'实验室安全教育','upload/security_course_6.svg','实验室与网络安全','实验教学安全','1','12学时','大连理工大学课程团队','自主学习','国家高等教育智慧教育平台','<p>围绕实验室分级管理、危化品使用、个人防护装备、仪器操作规范以及事故应急处置展开，适合实验类专业学生系统学习。</p><p><strong>页面内置：</strong>课程导学短片，可直接在详情页播放。</p><p><strong>官方课程入口：</strong><a href="https://higher.smartedu.cn/h5/course/622ac9fcbee70ef79f41d841" target="_blank">点击进入实验室安全教育</a></p>','upload/security_demo_b.mp4',1,22,0,NOW(),146,0,6),
(7,NOW(),'大学生网络安全与防范','upload/security_course_7.svg','实验室与网络安全','网络空间安全','1','10学时','公安部网安局专题资源','专题学习','中国大学生在线','<p>内容覆盖账号口令保护、钓鱼网站识别、个人隐私保护、网络谣言辨别和社交媒体安全，强调大学生日常高频风险防范。</p><p><strong>页面内置：</strong>课程导学短片，可直接在详情页播放。</p><p><strong>官方课程入口：</strong><a href="https://dxs.moe.gov.cn/zx/hd/wlaqxcz/2025ngjwlaqxcz/2025ngjwlaqxczgxwlsyxxzyk/" target="_blank">点击进入大学生网络安全与防范</a></p>','upload/security_demo_a.mp4',1,27,0,NOW(),187,0,9),
(8,NOW(),'大学生防电信网络诈骗','upload/security_course_8.svg','反诈与交通安全','反诈教育','1','8学时','公安部刑侦局 / 国家反诈中心','专题学习','央视频','<p>围绕刷单返利、冒充客服、网络交友、虚假投资、校园贷等典型骗局展开，强调转账核验和止损求助流程。</p><p><strong>页面内置：</strong>课程导学短片，可直接在详情页播放。</p><p><strong>官方课程入口：</strong><a href="https://w.yangshipin.cn/video?type=2&vid=2028353601&pid=600207719" target="_blank">点击进入大学生防电信网络诈骗</a></p>','upload/security_demo_b.mp4',1,40,0,NOW(),268,0,16),
(9,NOW(),'大学生交通安全教育','upload/security_course_9.svg','反诈与交通安全','交通文明与出行安全','1','8学时','吉林警察学院课程团队','自主学习','国家高等教育智慧教育平台 / 智慧树','<p>聚焦步行、骑行、乘车、夜间出行等校园周边常见场景，帮助学生建立规则意识、风险预判意识和事故避险能力。</p><p><strong>页面内置：</strong>课程导学短片，可直接在详情页播放。</p><p><strong>官方课程入口：</strong><a href="https://www.chinaooc.com.cn/course/6536f84495ac7bc6588c6a3a" target="_blank">点击进入大学生交通安全教育</a></p>','upload/security_demo_a.mp4',1,19,0,NOW(),132,0,7);

INSERT INTO `news` (`id`,`addtime`,`title`,`introduction`,`picture`,`content`,`name`,`headportrait`) VALUES
(1,NOW(),'开学季校园安全提醒','围绕宿舍管理、夜间出行、证件保管和突发情况上报等事项开展集中提醒。','upload/security_news_1.svg','<p>为帮助新学期顺利开局，学校面向全体学生发布校园安全提醒：</p><p>1. 宿舍内严禁私拉乱接电线、使用大功率违规电器。</p><p>2. 夜间返校请结伴而行，注意核对门禁与照明情况。</p><p>3. 个人证件、门禁卡、银行卡与验证码不得外借或泄露。</p><p>4. 遇到纠纷、可疑人员或异常事件，请第一时间联系辅导员和保卫部门。</p>','安全教育办公室','upload/news_headportrait1.jpg'),
(2,NOW(),'宿舍消防安全检查周启动','重点检查违规电器、堵塞通道、灭火器状态和宿舍安全用电问题。','upload/security_news_2.svg','<p>本周将开展宿舍消防安全专项检查。</p><p>检查内容包括：消防通道是否畅通、插线板是否串联使用、违规电器是否存在、灭火器材是否完好有效。</p><p>请各宿舍提前完成自查，做到人走断电、阳台与门口不堆放杂物，共同维护住宿环境安全。</p>','保卫处','upload/news_headportrait2.jpg'),
(3,NOW(),'实验室危化品使用规范提示','针对化学类与工科实验课程，明确危化品领用、标识、回收和应急要求。','upload/security_news_3.svg','<p>实验教学期间，请严格执行实验室准入制度和危化品使用规范：</p><p>1. 未经培训不得擅自操作危险仪器。</p><p>2. 危化品必须双人核验、规范标识、按要求回收。</p><p>3. 实验过程中必须佩戴防护镜、实验服和手套。</p><p>4. 发现泄漏、异味、异常发热等情况应立即停机并报告指导教师。</p>','实验教学中心','upload/news_headportrait3.jpg'),
(4,NOW(),'国家安全教育主题周安排公布','将开展主题讲座、案例学习、线上答题和班级研讨等系列活动。','upload/security_news_1.svg','<p>学校本月将组织国家安全教育主题周活动，围绕保密意识、涉外交流规范、数据安全和网络舆情识别开展专题学习。</p><p>各学院需组织学生完成主题班会和线上学习任务，推动总体国家安全观进课堂、进宿舍、进头脑。</p>','学生工作部','upload/news_headportrait4.jpg'),
(5,NOW(),'反诈专题讲座报名开启','邀请公安部门专家解析校园高发骗局，提升识骗、防骗和止损能力。','upload/security_news_3.svg','<p>近期电信网络诈骗案件中，刷单返利、冒充客服、网络兼职和游戏交易诈骗在学生群体中较为高发。</p><p>学校将举办反诈专题讲座，重点讲解诈骗话术拆解、风险识别、转账冷静期和报警求助流程，欢迎同学们积极报名参加。</p>','安全教育办公室','upload/news_headportrait5.jpg'),
(6,NOW(),'网络安全与个人信息保护提示','提醒师生保护账号口令、谨慎扫码授权，避免隐私泄露和账号被盗。','upload/security_news_1.svg','<p>请广大师生增强网络安全意识，不使用同一密码登录多个平台，不点击陌生邮件和短信中的可疑链接，不随意扫码授权下载来路不明的应用程序。</p><p>涉及学籍、成绩、奖助、实习等敏感信息时，请以学校官方渠道为准。</p>','信息化中心','upload/news_headportrait6.jpg'),
(7,NOW(),'校园交通文明出行倡议','倡导步行守规则、骑行戴头盔、机动车礼让行人，降低校园道路风险。','upload/security_news_3.svg','<p>校园道路人车混行情况较多，请同学们严格遵守交通规则：</p><p>步行不低头看手机，骑行佩戴头盔，机动车减速慢行礼让行人，夜间出行提高可见度，共同营造安全有序的校园交通环境。</p>','后勤保障部','upload/news_headportrait7.jpg'),
(8,NOW(),'食品安全与春季健康防护提醒','提示学生关注食堂就餐卫生、外卖来源和春季常见传染病预防。','upload/security_news_2.svg','<p>春季是肠道疾病和呼吸道疾病的高发期，请同学们注意饮食卫生，不食用来源不明食品，不购买无冷链保障的高风险外卖，做到勤洗手、勤通风、早就医。</p>','后勤保障部','upload/news_headportrait1.jpg'),
(9,NOW(),'应急救护培训与演练通知','开展心肺复苏、AED 使用和创伤包扎等实操培训，提高自救互救能力。','upload/security_news_2.svg','<p>学校将联合红十字志愿服务团队开展应急救护培训，内容包括心肺复苏、AED 使用、气道异物梗阻处置和创伤包扎。</p><p>欢迎有兴趣的同学报名参加，提升在紧急情况下的现场救护能力。</p>','校医院','upload/news_headportrait2.jpg');

INSERT INTO `systemintro` (`id`,`addtime`,`title`,`subtitle`,`content`,`picture1`,`picture2`,`picture3`) VALUES
(1,NOW(),'校园安全教育管理平台','CAMPUS SAFETY EDUCATION','<p>本平台面向校园安全教育场景建设，聚焦<strong>安全课程学习、公告发布、隐患上报、学习记录、在线测试</strong>等核心流程，帮助学校形成“宣传教育 + 风险提醒 + 学习留痕 + 能力测评”的闭环。</p><p>平台课程内容围绕国家安全、消防安全、实验室安全、网络安全、反诈教育、交通安全、应急救护等专题展开，便于学生根据实际场景进行针对性学习。</p><p>在前台，学生可以查看课程、阅读公告、记录学习过程、参与在线测试；在后台，教师和管理人员可以维护课程资源、更新安全公告、跟踪学习情况并持续优化安全教育内容。</p><p>系统目标不是简单陈列信息，而是让安全教育真正融入学生日常学习与校园生活。</p>','upload/security_system_1.svg','upload/security_system_2.svg','upload/security_system_3.svg');

INSERT INTO `exampaper` (`id`,`addtime`,`name`,`time`,`status`,`examnum`) VALUES
(1,NOW(),'大学生安全教育综合测试',45,'1',3),
(2,NOW(),'消防与应急处置专项测试',30,'1',3);

INSERT INTO `examquestion` (`id`,`addtime`,`paperid`,`papername`,`questionname`,`options`,`score`,`answer`,`analysis`,`type`,`sequence`) VALUES
(1,NOW(),1,'大学生安全教育综合测试','<p>宿舍内发现插线板串联、使用大功率电器时，最妥当的做法是（）。</p>','[{\"text\":\"A. 只要不断电就继续使用\",\"code\":\"A\"},{\"text\":\"B. 立即停止使用并整改\",\"code\":\"B\"},{\"text\":\"C. 仅在老师检查时收起来\",\"code\":\"C\"},{\"text\":\"D. 借给其他宿舍继续使用\",\"code\":\"D\"}]',10,'B','宿舍违规电器和插线板串联容易引发过载与火灾，应立即停止使用并整改。',0,10),
(2,NOW(),1,'大学生安全教育综合测试','<p>以下哪些做法属于防范电信网络诈骗的有效措施（）。</p>','[{\"text\":\"A. 涉及转账时通过官方渠道二次核验\",\"code\":\"A\"},{\"text\":\"B. 对陌生二维码和链接保持警惕\",\"code\":\"B\"},{\"text\":\"C. 将短信验证码告诉自称客服的人\",\"code\":\"C\"},{\"text\":\"D. 安装国家反诈类官方应用并开启预警\",\"code\":\"D\"}]',10,'A,B,D','转账核验、谨慎扫码和使用官方反诈工具都是有效措施，验证码绝不能泄露。',1,9),
(3,NOW(),1,'大学生安全教育综合测试','<p>国家安全与大学生日常生活无关，只需要相关专业学生学习。</p>','[{\"text\":\"A. 对\",\"code\":\"A\"},{\"text\":\"B. 错\",\"code\":\"B\"}]',10,'B','国家安全涉及网络、数据、保密、涉外交流等多个方面，与每位大学生都有关。',2,8),
(4,NOW(),1,'大学生安全教育综合测试','<p>遇到陌生人以“奖助学金补发”为由要求提供验证码时，应先联系（）。</p>','[]',10,'学校官方老师或辅导员','涉及奖助学金、学籍、缴费等事项，应通过学校官方渠道核验，不轻信陌生来电。',3,7),
(5,NOW(),1,'大学生安全教育综合测试','<p>请简述至少两项日常保护个人信息安全的做法。</p>','[]',10,'','可从强密码、双重验证、不随意扫码授权、不泄露证件照片、不连接陌生公共设备等方面作答。',4,6),
(6,NOW(),2,'消防与应急处置专项测试','<p>发现宿舍楼内浓烟较大时，正确的逃生方式是（）。</p>','[{\"text\":\"A. 乘电梯迅速下楼\",\"code\":\"A\"},{\"text\":\"B. 弯腰低姿、沿疏散指示撤离\",\"code\":\"B\"},{\"text\":\"C. 返回宿舍收拾贵重物品\",\"code\":\"C\"},{\"text\":\"D. 躲进卫生间反锁等待\",\"code\":\"D\"}]',10,'B','火灾逃生应避免乘坐电梯，沿疏散路线低姿前进并尽快撤离。',0,10),
(7,NOW(),2,'消防与应急处置专项测试','<p>以下哪些属于实验室发生异常情况后的正确处置（）。</p>','[{\"text\":\"A. 立即停止实验并切断相关设备\",\"code\":\"A\"},{\"text\":\"B. 按规定报告指导教师或管理员\",\"code\":\"B\"},{\"text\":\"C. 为避免麻烦自行离开现场\",\"code\":\"C\"},{\"text\":\"D. 根据预案使用相应应急设施\",\"code\":\"D\"}]',10,'A,B,D','实验室异常处置要遵守预案，及时停机、报告并采取对应的安全措施。',1,9),
(8,NOW(),2,'消防与应急处置专项测试','<p>灭火器只要外观没有明显损坏，就可以长期放在宿舍内不检查。</p>','[{\"text\":\"A. 对\",\"code\":\"A\"},{\"text\":\"B. 错\",\"code\":\"B\"}]',10,'B','灭火器需要定期检查压力表、有效期和摆放状态，不能长期不检查。',2,8),
(9,NOW(),2,'消防与应急处置专项测试','<p>心肺复苏现场处置中，发现患者无反应、无正常呼吸后，应尽快呼叫他人并取来（）。</p>','[]',10,'AED','心肺复苏应尽快呼救、启动急救流程，并尽早获取 AED 实施电除颤。',3,7),
(10,NOW(),2,'消防与应急处置专项测试','<p>请结合校园场景，简述火灾报警后个人应如何组织自救与互救。</p>','[]',10,'','可从报警、断电、疏散、帮助他人、听从指挥、集合清点等方面作答。',4,6);

INSERT INTO `examstatistics` (`id`,`addtime`,`username`,`paperid`,`papername`,`examno`,`totalscore`,`userid`) VALUES
(900001,NOW(),'张三',1,'大学生安全教育综合测试','SAFE-DEMO-002',40,1770220589488);

INSERT INTO `examrecord` (`id`,`addtime`,`userid`,`username`,`paperid`,`papername`,`questionid`,`questionname`,`options`,`score`,`answer`,`analysis`,`myscore`,`myanswer`,`type`,`ismark`,`examno`) VALUES
(900001,NOW(),1770220589488,'张三',1,'大学生安全教育综合测试',1,'<p>宿舍内发现插线板串联、使用大功率电器时，最妥当的做法是（）。</p>','[{\"text\":\"A. 只要不断电就继续使用\",\"code\":\"A\"},{\"text\":\"B. 立即停止使用并整改\",\"code\":\"B\"},{\"text\":\"C. 仅在老师检查时收起来\",\"code\":\"C\"},{\"text\":\"D. 借给其他宿舍继续使用\",\"code\":\"D\"}]',10,'B','宿舍违规电器和插线板串联容易引发过载与火灾，应立即停止使用并整改。',10,'B',0,0,'SAFE-DEMO-002'),
(900002,NOW(),1770220589488,'张三',1,'大学生安全教育综合测试',2,'<p>以下哪些做法属于防范电信网络诈骗的有效措施（）。</p>','[{\"text\":\"A. 涉及转账时通过官方渠道二次核验\",\"code\":\"A\"},{\"text\":\"B. 对陌生二维码和链接保持警惕\",\"code\":\"B\"},{\"text\":\"C. 将短信验证码告诉自称客服的人\",\"code\":\"C\"},{\"text\":\"D. 安装国家反诈类官方应用并开启预警\",\"code\":\"D\"}]',10,'A,B,D','转账核验、谨慎扫码和使用官方反诈工具都是有效措施，验证码绝不能泄露。',10,'A,B,D',1,0,'SAFE-DEMO-002'),
(900003,NOW(),1770220589488,'张三',1,'大学生安全教育综合测试',3,'<p>国家安全与大学生日常生活无关，只需要相关专业学生学习。</p>','[{\"text\":\"A. 对\",\"code\":\"A\"},{\"text\":\"B. 错\",\"code\":\"B\"}]',10,'B','国家安全涉及网络、数据、保密、涉外交流等多个方面，与每位大学生都有关。',10,'B',2,0,'SAFE-DEMO-002'),
(900004,NOW(),1770220589488,'张三',1,'大学生安全教育综合测试',4,'<p>遇到陌生人以“奖助学金补发”为由要求提供验证码时，应先联系（）。</p>','[]',10,'学校官方老师或辅导员','涉及奖助学金、学籍、缴费等事项，应通过学校官方渠道核验，不轻信陌生来电。',10,'学校官方老师或辅导员',3,0,'SAFE-DEMO-002'),
(900005,NOW(),1770220589488,'张三',1,'大学生安全教育综合测试',5,'<p>请简述至少两项日常保护个人信息安全的做法。</p>','[]',10,'','可从强密码、双重验证、不随意扫码授权、不泄露证件照片、不连接陌生公共设备等方面作答。',0,'设置强密码并开启双重验证；不随意泄露证件和验证码。',4,1,'SAFE-DEMO-002');

INSERT INTO `xuexijilu` (`id`,`addtime`,`jilubianhao`,`kechengmingcheng`,`tupian`,`jilushijian`,`xuexishizhang`,`xuexibiji`,`wanchengqingkuang`,`zhanghao`,`xingming`) VALUES
(1,NOW(),'SAFE2026001','大学生安全教育','upload/security_course_1.svg',NOW(),45,'完成宿舍安全、夜间出行和风险识别模块学习，整理了宿舍违规电器排查清单。','已完成','001','陈浩'),
(2,NOW(),'SAFE2026002','大学生国家安全教育','upload/security_course_2.svg',NOW(),38,'记录了总体国家安全观的核心要点，并结合校园网络使用场景做了保密风险反思。','学习中','003','欧阳晨'),
(3,NOW(),'SAFE2026003','灾难逃生与自救','upload/security_course_3.svg',NOW(),52,'重点复习了火灾逃生和地震避险动作，补充了宿舍楼疏散路线图。','已完成','004','李静'),
(4,NOW(),'SAFE2026004','大学生防电信网络诈骗','upload/security_course_8.svg',NOW(),40,'归纳了刷单返利、冒充客服和虚假投资三类骗局的话术特征。','已完成','008','赵阳');

INSERT INTO `discusskechengxinxi` (`id`,`addtime`,`refid`,`userid`,`avatarurl`,`nickname`,`content`,`reply`,`thumbsupnum`,`crazilynum`,`istop`,`tuserids`,`cuserids`) VALUES
(1,NOW(),1,1770220589488,'upload/yonghu_touxiang1.jpg','张三','<p>这门课把宿舍安全、实验室安全和校园突发事件处理讲得比较系统，适合作为新生入学第一课。</p>','<p>建议结合学院实际再做一次线下安全演练，学习效果会更好。</p>',6,0,1,'',''),
(2,NOW(),8,1770220589488,'upload/yonghu_touxiang1.jpg','张三','<p>反诈课程里的案例都很贴近学生生活，尤其是兼职刷单和冒充客服部分，很有警示作用。</p>',NULL,4,0,0,'','');

SET FOREIGN_KEY_CHECKS=1;
