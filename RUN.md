# 运行说明

## 1. 数据库

项目数据库名：`springboot76r81c0r`

后端已配置：

- 用户名：`root`
- 密码：`12345678`

初始化 SQL：

```bash
mysql -h127.0.0.1 -P3306 -uroot -p12345678 springboot76r81c0r -e "source db/springboot76r81c0r.sql"
```

## 2. 后端

后端目录就是项目根目录，接口地址：

- `http://localhost:8080/springboot76r81c0r`

建议启动命令：

```bash
mvn -s settings-local.xml -gs settings-local.xml -DskipTests spring-boot:run
```

如果当前机器没有缓存完整 Maven 插件，需要联网下载一次依赖。

## 3. 前端

已拆分为两个独立 Vue2 工程：

- 用户端：`frontend-user`
- 管理端：`frontend-admin`

端口：

- 用户端：`8082`
- 管理端：`8081`

安装依赖：

```bash
cd frontend-user && npm install --no-package-lock
cd frontend-admin && npm install --no-package-lock
```

启动开发环境：

```bash
cd frontend-user && npm run serve
cd frontend-admin && npm run serve
```

## 4. 默认账号

后台：

- 账号：`admin`
- 密码：`admin`
