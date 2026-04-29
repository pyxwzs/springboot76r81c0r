






















<template>
	<div class="add-update-preview">
		<el-form
			class="add-update-form"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="200px"
			>
			<el-form-item class="add-item" label="隐患编号" prop="yinhuanbianhao">
				<el-input v-model="ruleForm.yinhuanbianhao" placeholder="隐患编号" disabled></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="隐患名称" prop="yinhuanmingcheng">
				<el-input v-model="ruleForm.yinhuanmingcheng" 
					placeholder="隐患名称" clearable :readonly="ro.yinhuanmingcheng"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="隐患类型" prop="yinhuanleixing">
				<el-input v-model="ruleForm.yinhuanleixing" 
					placeholder="隐患类型" clearable :readonly="ro.yinhuanleixing"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="隐患位置" prop="yinhuanweizhi">
				<el-input v-model="ruleForm.yinhuanweizhi" 
					placeholder="隐患位置" clearable :readonly="ro.yinhuanweizhi"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="隐患等级" prop="yinhuandengji">
				<el-input v-model="ruleForm.yinhuandengji" 
					placeholder="隐患等级" clearable :readonly="ro.yinhuandengji"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="上报日期" prop="shangbaoriqi">
				<el-date-picker
					:disabled="ro.shangbaoriqi"
					format="yyyy 年 MM 月 dd 日"
					value-format="yyyy-MM-dd"
					v-model="ruleForm.shangbaoriqi" 
					type="date"
					placeholder="上报日期">
				</el-date-picker> 
			</el-form-item>
			<el-form-item class="add-item"  label="隐患状态" prop="yinhuanzhuangtai">
				<el-select v-model="ruleForm.yinhuanzhuangtai" placeholder="请选择隐患状态" :disabled="ro.yinhuanzhuangtai"  filterable>
					<el-option
						v-for="(item,index) in yinhuanzhuangtaiOptions"
						:key="index"
						:label="item"
						:value="item">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item class="add-item" label="账号" prop="zhanghao">
				<el-input v-model="ruleForm.zhanghao" 
					placeholder="账号" clearable :readonly="ro.zhanghao"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="姓名" prop="xingming">
				<el-input v-model="ruleForm.xingming" 
					placeholder="姓名" clearable :readonly="ro.xingming"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="手机号码" prop="shoujihaoma">
				<el-input v-model="ruleForm.shoujihaoma" 
					placeholder="手机号码" clearable :readonly="ro.shoujihaoma"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="隐患内容" prop="yinhuanneirong">
				<editor 
					v-model="ruleForm.yinhuanneirong" 
					class="editor" 
					myQuillEditor="yinhuanneirong"
					action="file/upload">
				</editor>
			</el-form-item>

			<el-form-item class="add-btn-item">
				<el-button class="submitBtn"  type="primary" @click="onSubmit(null)">
					<span class="icon iconfont icon-xiugai13"></span>
					<span class="text">提交信息</span>
				</el-button>
				<el-button class="closeBtn" @click="back()">
					<span class="icon iconfont icon-shanchu8"></span>
					<span class="text">取消</span>
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				id: '',
				baseUrl: '',
				ro:{
					yinhuanbianhao : false,
					yinhuanmingcheng : false,
					yinhuanleixing : false,
					yinhuanweizhi : false,
					yinhuandengji : false,
					yinhuanneirong : false,
					shangbaoriqi : false,
					yinhuanzhuangtai : false,
					zhanghao : false,
					xingming : false,
					shoujihaoma : false,
				},
				type: '',
				userTableName: localStorage.getItem('UserTableName'),
				ruleForm: {
					yinhuanbianhao: this.getUUID(),
					yinhuanmingcheng: '',
					yinhuanleixing: '',
					yinhuanweizhi: '',
					yinhuandengji: '四级' ,
					yinhuanneirong: '',
					shangbaoriqi: '',
					yinhuanzhuangtai: '',
					zhanghao: '',
					xingming: '',
					shoujihaoma: '',
				},
				yinhuanzhuangtaiOptions: [],

				rules: {
					yinhuanbianhao: [
					],
					yinhuanmingcheng: [
					],
					yinhuanleixing: [
					],
					yinhuanweizhi: [
					],
					yinhuandengji: [
					],
					yinhuanneirong: [
					],
					shangbaoriqi: [
					],
					yinhuanzhuangtai: [
					],
					zhanghao: [
					],
					xingming: [
					],
					shoujihaoma: [
						{ validator: this.$validate.isMobile, trigger: 'blur' },
					],
				},
				centerType: false,
			};
		},
		computed: {
			sessionForm() {
				return JSON.parse(localStorage.getItem('sessionForm'))
			},



		},
		components: {
		},
		created() {
			if(this.$route.query.centerType){
				this.centerType = true
			}
			//this.bg();
			let type = this.$route.query.type ? this.$route.query.type : '';
			this.init(type);
			this.baseUrl = this.$config.baseUrl;
			this.ruleForm.shangbaoriqi = this.getCurDate()
		},
		methods: {
			getMakeZero(s) {
				return s < 10 ? '0' + s : s;
			},
			// 下载
			download(file ){
				window.open(`${file}`)
			},
			// 初始化
			init(type) {
				this.type = type;
				if(type=='cross'){
					var obj = JSON.parse(localStorage.getItem('crossObj'));
					for (var o in obj){
						if(o=='yinhuanbianhao'){
							this.ruleForm.yinhuanbianhao = obj[o];
							this.ro.yinhuanbianhao = true;
							continue;
						}
						if(o=='yinhuanmingcheng'){
							this.ruleForm.yinhuanmingcheng = obj[o];
							this.ro.yinhuanmingcheng = true;
							continue;
						}
						if(o=='yinhuanleixing'){
							this.ruleForm.yinhuanleixing = obj[o];
							this.ro.yinhuanleixing = true;
							continue;
						}
						if(o=='yinhuanweizhi'){
							this.ruleForm.yinhuanweizhi = obj[o];
							this.ro.yinhuanweizhi = true;
							continue;
						}
						if(o=='yinhuandengji'){
							this.ruleForm.yinhuandengji = obj[o];
							this.ro.yinhuandengji = true;
							continue;
						}
						if(o=='yinhuanneirong'){
							this.ruleForm.yinhuanneirong = obj[o];
							this.ro.yinhuanneirong = true;
							continue;
						}
						if(o=='shangbaoriqi'){
							this.ruleForm.shangbaoriqi = obj[o];
							this.ro.shangbaoriqi = true;
							continue;
						}
						if(o=='yinhuanzhuangtai'){
							this.ruleForm.yinhuanzhuangtai = obj[o];
							this.ro.yinhuanzhuangtai = true;
							continue;
						}
						if(o=='zhanghao'){
							this.ruleForm.zhanghao = obj[o];
							this.ro.zhanghao = true;
							continue;
						}
						if(o=='xingming'){
							this.ruleForm.xingming = obj[o];
							this.ro.xingming = true;
							continue;
						}
						if(o=='shoujihaoma'){
							this.ruleForm.shoujihaoma = obj[o];
							this.ro.shoujihaoma = true;
							continue;
						}
					}
					this.ruleForm.yinhuandengji = '四级'; 				}else if(type=='edit'){
					this.info()
				}
				// 获取用户信息
				this.$http.get(this.userTableName + '/session', {emulateJSON: true}).then(res => {
					if (res.data.code == 0) {
						var json = res.data.data;
						if((json.zhanghao!=''&&json.zhanghao) || json.zhanghao==0){
							this.ruleForm.zhanghao = json.zhanghao;
							this.ro.zhanghao = true;
						}
						if((json.xingming!=''&&json.xingming) || json.xingming==0){
							this.ruleForm.xingming = json.xingming;
							this.ro.xingming = true;
						}
						if((json.shoujihaoma!=''&&json.shoujihaoma) || json.shoujihaoma==0){
							this.ruleForm.shoujihaoma = json.shoujihaoma;
							this.ro.shoujihaoma = true;
						}
					}
				});
				this.yinhuanzhuangtaiOptions = "已处理,待处理".split(',')

				if (localStorage.getItem('raffleType') && localStorage.getItem('raffleType') != null) {
					localStorage.removeItem('raffleType')
					setTimeout(() => {
						this.onSubmit(null)
					}, 300)
				}
			},

			// 多级联动参数
			// 多级联动参数
			async info() {
				await this.$http.get(`yinhuanshangbao/detail/${this.$route.query.id}`, {emulateJSON: true}).then(res => {
					if (res.data.code == 0) {
						this.ruleForm = res.data.data;
					}
				});
			},
			// 提交
			async onSubmit(subMitType=null) {
				if(this.ruleForm.yinhuanbianhao){
					this.ruleForm.yinhuanbianhao = String(this.ruleForm.yinhuanbianhao)
				}
				if(!this.ruleForm.id) {
					delete this.ruleForm.userid
				}
				await this.$refs["ruleForm"].validate(async valid => {
					if(valid) {
						if(this.type=='cross'){
							var statusColumnName = localStorage.getItem('statusColumnName');
							var statusColumnValue = localStorage.getItem('statusColumnValue');
							if(statusColumnName && statusColumnName!='') {
								var obj = JSON.parse(localStorage.getItem('crossObj'));
								if(!statusColumnName.startsWith("[")) {
									for (var o in obj){
										if(o==statusColumnName){
											obj[o] = statusColumnValue;
										}
									}
									var table = localStorage.getItem('crossTable');
									await this.$http.post(table+'/update', obj).then(res => {});
								}
							}
						}

						await this.$http.post(`yinhuanshangbao/${this.ruleForm.id?'update':this.centerType?'save':'add'}`, this.ruleForm).then(async res => {
							if (res.data.code == 0) {
								await this.$message({
									message: '操作成功',
									type: 'success',
									duration: 1500,
									onClose: () => {
										this.$router.go(-1);
										
									}
								});
							} else {
								this.$message({
									message: res.data.msg,
									type: 'error',
									duration: 1500
								});
							}
						});
					}
				});
			},
			// 获取uuid
			getUUID () {
				return new Date().getTime();
			},
			// 返回
			back() {
				this.$router.go(-1);
			},
		}
	};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.add-update-preview {
		padding: 20px 10% 40px;
		margin: 10px auto;
		background: none;
		width: 100%;
		position: relative;
		.add-update-form {
			border: 0px solid #eee;
			border-radius: 0;
			padding: 40px 20% 20px 10%;
			background: #fff;
			width: 100%;
			position: relative;
			.add-item.el-form-item {
				padding: 0;
				margin: 0 0 24px;
				background: none;
				::v-deep .el-form-item__label {
					padding: 0 10px 0 0;
					color: #333;
					white-space: nowrap;
					font-weight: 500;
					width: 200px;
					font-size: 16px;
					line-height: 40px;
					text-align: right;
				}
				::v-deep .el-form-item__content {
					margin-left: 200px;
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
					color: #666;
					width: 100%;
					font-size: 16px;
					height: 40px;
				}
				.el-input ::v-deep .el-input__inner[readonly="readonly"] {
					border: 0;
					cursor: not-allowed;
					border-radius: 0px;
					padding: 0 12px;
					box-shadow: none;
					outline: none;
					color: #999;
					width: 100%;
					font-size: 16px;
					height: 40px;
				}
				.el-input-number ::v-deep .el-input__inner {
					text-align: left;
					border: 1px solid #999;
					border-radius: 0px 0 0 0;
					padding: 0 12px;
					box-shadow: none;
					outline: none;
					color: #666;
					width: 100%;
					font-size: 16px;
					height: 40px;
				}
				.el-input-number ::v-deep .is-disabled .el-input__inner {
					text-align: left;
					border: 0;
					cursor: not-allowed;
					border-radius: 0px;
					padding: 0 12px;
					box-shadow: none;
					outline: none;
					color: #999;
					width: 100%;
					font-size: 16px;
					height: 40px;
				}
				.el-input-number ::v-deep .el-input-number__decrease {
					display: none;
				}
				.el-input-number ::v-deep .el-input-number__increase {
					display: none;
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
					font-size: 14px;
					height: 40px;
				}
				.el-select ::v-deep .is-disabled .el-input__inner {
					border: 0;
					cursor: not-allowed;
					border-radius: 0px 0 0 0;
					padding: 0 10px;
					box-shadow: none;
					outline: none;
					color: #999;
					background: #f6f6f6;
					width: 100%;
					font-size: 14px;
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
					color: #666;
					width: 100%;
					font-size: 16px;
					height: 40px;
				}
				.el-date-editor ::v-deep .el-input__inner[readonly="readonly"] {
					border: 0;
					cursor: not-allowed;
					border-radius: 0px 0 0 0;
					padding: 0 10px 0 30px;
					box-shadow: none;
					outline: none;
					color: #999;
					background: #f6f6f6;
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
					font-size: 16px;
				}
				.el-textarea ::v-deep .el-textarea__inner {
					border: 1px solid #999;
					border-radius: 0px 0 0 0;
					padding: 12px;
					box-shadow: none;
					outline: none;
					color: #666;
					width: 100%;
					font-size: 16px;
					height: auto;
				}
				.el-textarea ::v-deep .el-textarea__inner[readonly="readonly"] {
					border: 0;
					cursor: not-allowed;
					border-radius: 0px;
					padding: 12px;
					box-shadow: none;
					outline: none;
					color: #999;
					width: 100%;
					font-size: 16px;
					height: auto;
				}
				::v-deep .el-input__inner::placeholder {
					color: #999;
					font-size: 16px;
				}
				::v-deep textarea::placeholder {
					color: #999;
					font-size: 16px;
				}
				.editor {
					background-color: #fff;
					border-radius: 0;
					padding: 0;
					box-shadow: none;
					margin: 0;
					width: 100%;
					border-color: #ccc;
					border-width: 0;
					border-style: solid;
					height: auto;
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
					color: #999;
				}
				.upload-img {
					border-radius: 4px;
					object-fit: cover;
					width: 120px;
					height: 120px;
				}
				.viewBtn {
					border: 0;
					cursor: pointer;
					padding: 0 30px;
					margin: 0;
					color: #fff;
					display: inline-block;
					font-size: 16px;
					line-height: 36px;
					border-radius: 0px 0 0 0;
					outline: none;
					background: #253b43;
					width: auto;
					text-align: center;
					min-width: 120px;
					height: 36px;
				}
				.viewBtn:hover {
					opacity: 0.7;
				}
				.unviewBtn {
					border: 0;
					cursor: pointer;
					padding: 0 20px;
					margin: 0;
					color: #000;
					display: inline-block;
					font-size: 16px;
					line-height: 36px;
					border-radius: 0px 0 0 0;
					outline: none;
					background: #f6f6f6;
					width: auto;
					min-width: 120px;
					height: 36px;
				}
				.unviewBtn:hover {
					opacity: 0.8;
				}
			}
			.add-btn-item {
				padding: 30px 200px 0 0;
				margin: 40px auto;
				background: none;
				width: 100%;
				border-color: #808080;
				border-width: 2px 0 0;
				border-style: solid;
				text-align: center;
				.submitBtn {
					border: 0;
					cursor: pointer;
					padding: 0 30px;
					margin: 0 20px 0 0;
					display: inline-block;
					font-size: 14px;
					line-height: 45px;
					border-radius: 30px;
					outline: none;
					background: #263c44;
					width: auto;
					min-width: 180px;
					height: 45px;
					.icon {
						color: #000;
						display: none;
					}
					.text {
						color: #fff;
						font-size: 16px;
					}
				}
				.submitBtn:hover {
					opacity: 0.8;
					.icon {
						color: #fff;
					}
					.text {
						color: #fff;
					}
				}
				.closeBtn {
					border: 0;
					cursor: pointer;
					padding: 0 30px;
					margin: 0 20px 0 0;
					display: inline-block;
					font-size: 16px;
					line-height: 40px;
					border-radius: 30px;
					outline: none;
					background: #263c44;
					width: auto;
					min-width: 180px;
					height: 40px;
					.icon {
						color: #fff;
						display: none;
						font-size: 18px;
					}
					.text {
						color: #fff;
						font-size: 16px;
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
		}
	}
	.el-date-editor.el-input {
		width: auto;
	}
</style>
