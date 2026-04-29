# 项目配置文档（从 0 开始）

本文档用于把本项目在一台全新机器上**从 0 配置到可运行**。内容覆盖：技术栈、环境准备、数据库初始化、后端/前端启动、关键配置项说明与常见问题排查。

> 注意：仓库里已有 `RUN.md`，但其中后端端口写的是 `8080`；以实际配置文件 `src/main/resources/application.yml` 为准（后端端口是 **8088**）。

---

## 1. 项目结构与访问地址

- **后端（Spring Boot）**：项目根目录
- **前端-管理端（Vue2）**：`frontend-admin`
- **前端-用户端（Vue2）**：`frontend-user`
- **数据库脚本**：`db/`

默认本地开发访问：

- **后端接口根地址**：`http://localhost:8088/springboot76r81c0r`
- **管理端前端**：`http://localhost:8081/`
- **用户端前端**：`http://localhost:8082/`

前端通过开发代理把 `http://localhost:8081|8082/springboot76r81c0r/...` 转发到后端。

---

## 2. 技术栈（按模块）

### 2.1 后端

- **语言/运行时**：Java 8
- **框架**：Spring Boot `2.2.2.RELEASE`
- **构建**：Maven（提供 `mvnw`/`mvnw.cmd`）
- **Web**：Spring MVC（`spring-boot-starter-web`）
- **数据库访问**：
  - MySQL 驱动（`mysql-connector-java`）
  - MyBatis Spring Boot Starter `2.1.1`
  - MyBatis-Plus `2.3` + `mybatisplus-spring-boot-starter 1.0.5`
- **WebSocket**：`spring-boot-starter-websocket`
- **常用工具/库（pom.xml）**：Lombok、Fastjson、Gson、Hutool、Apache HttpClient、Apache POI、Unirest 等
- **AI/NLP/数据分析相关依赖（pom.xml）**：HanLP、Weka、JFreeChart、百度 AIP Java SDK 等

### 2.2 前端

- **框架**：Vue 2
- **工程化**：Vue CLI 4
- **UI**：Element-UI
- **网络请求**：Axios
- **可视化**：ECharts（管理端/用户端均有）
- **构建注意**：`package.json` 的脚本已加入 `NODE_OPTIONS=--openssl-legacy-provider`（用于兼容较新 Node 版本的 OpenSSL 变更）

### 2.3 数据库

- **MySQL**：脚本头部显示以 MySQL `5.7.x` 导出，开发环境建议 MySQL 5.7/8.0 均可（注意字符集与时区）。

---

## 3. 环境准备（全新机器）

### 3.1 必装软件

- **JDK 8**（必须）
- **Maven**（可选；项目自带 `mvnw`，没有 Maven 也能跑）
- **MySQL 5.7/8.0**
- **Node.js + npm**（用于前端）

建议版本（更稳妥）：

- **Node.js 16 LTS**（优先推荐）；如果使用 Node 17+，脚本已加 `--openssl-legacy-provider`，通常也可运行。

### 3.2 端口占用检查

请确保以下端口可用：

- **8088**（后端）
- **8081**（管理端前端）
- **8082**（用户端前端）
- **3306**（MySQL）

---

## 4. 数据库初始化（从 0）

### 4.1 创建并导入数据

项目数据库名：`springboot76r81c0r`

默认数据库连接（来自 `src/main/resources/application.yml`）：

- 用户名：`root`
- 密码：`12345678`
- 地址：`127.0.0.1:3306`

初始化 SQL 在：`db/springboot76r81c0r.sql`（含建库、建表、初始数据）

执行导入（示例）：

```bash
mysql -h127.0.0.1 -P3306 -uroot -p12345678 -e "source db/springboot76r81c0r.sql"
```

如果你本机 root 密码不同，或不想使用 root，请在导入前自行创建用户并授权，然后同步修改 `application.yml` 的 `spring.datasource.*`。

---

## 5. 后端配置与启动

### 5.1 关键配置文件

- **主配置**：`src/main/resources/application.yml`
- **Maven 本地仓库位置**：`settings-local.xml`（将本地仓库固定到 `/Users/xiangzai/.m2/repository`）

后端关键配置（以当前文件为准）：

- **端口**：`server.port = 8088`
- **上下文路径**：`server.servlet.context-path = /springboot76r81c0r`
- **数据库**：`spring.datasource.url/username/password`
- **上传限制**：`spring.servlet.multipart.max-file-size = 100MB` 等
- **静态资源目录**：`spring.resources.static-locations = classpath:static/,file:static/`

### 5.2 DeepSeek 配置（可选/按需）

`application.yml` 中存在：

- `deepseek.api-url`
- `deepseek.api-key`
- `deepseek.model`

其中 `api-key` 使用了环境变量兜底写法：

- `DEEPSEEK_API_KEY`：若环境变量存在则优先使用
- 否则会使用配置文件内的默认值

建议本地开发/部署时使用环境变量注入，避免把真实 Key 固化在配置里：

```bash
export DEEPSEEK_API_KEY="你的真实Key"
```

### 5.3 启动命令

推荐（与 `RUN.md` 一致）：

```bash
mvn -s settings-local.xml -gs settings-local.xml -DskipTests spring-boot:run
```

也可以使用 Maven Wrapper（不依赖本机 Maven 安装）：

```bash
./mvnw -s settings-local.xml -gs settings-local.xml -DskipTests spring-boot:run
```

启动成功后，接口根地址应可访问：

- `http://localhost:8088/springboot76r81c0r`

---

## 6. 前端配置与启动（管理端 + 用户端）

### 6.1 关键配置（开发代理）

两套前端都在各自的 `vue.config.js` 配置了代理：

- 代理前缀：`/springboot76r81c0r`
- 代理目标：`http://localhost:8088/springboot76r81c0r/`
- 重写规则：把 `^/springboot76r81c0r` 重写为空（最终打到后端的仍是同一个 context-path）

也就是说，前端请求一般写成：

- `/springboot76r81c0r/xxx/yyy`

就能在开发环境下自动转发到后端。

### 6.2 安装依赖

在两个前端目录分别安装依赖：

```bash
cd frontend-user && npm install --no-package-lock
cd ../frontend-admin && npm install --no-package-lock
```

> 如果依赖安装出现 Node 版本/openssl 报错，优先切换到 Node 16 LTS 再重试。

### 6.3 启动开发环境

```bash
cd frontend-user && npm run serve
cd ../frontend-admin && npm run serve
```

启动后访问：

- 用户端：`http://localhost:8082/`
- 管理端：`http://localhost:8081/`

---

## 7. 默认账号

`RUN.md` 给出的默认后台账号：

- 账号：`admin`
- 密码：`admin`

（若登录失败，通常是数据库未正确导入或数据被修改；请重新导入 `db/springboot76r81c0r.sql` 验证。）

---

## 8. 常见问题（排查顺序）

### 8.1 前端请求 404 / 接口不通

- 确认后端是否在 **8088** 启动成功（不是 8080）
- 确认后端 context-path 是 **`/springboot76r81c0r`**
- 确认前端 `vue.config.js` 的 `proxy.target` 指向 `http://localhost:8088/springboot76r81c0r/`

### 8.2 后端启动报数据库连接失败

- 确认 MySQL 正在运行，端口 `3306` 可连
- 确认已执行 `db/springboot76r81c0r.sql`，库存在
- 确认 `application.yml` 的用户名/密码与本机一致

### 8.3 Maven 下载依赖很慢/失败

- 首次启动需要联网拉取依赖
- 如需使用镜像源，可在本机 Maven `settings.xml` 配置 mirror（本项目的 `settings-local.xml` 目前仅固定了 `localRepository`）

### 8.4 前端启动报 OpenSSL / crypto 相关错误

项目脚本已加入 `NODE_OPTIONS=--openssl-legacy-provider`，仍报错时：

- 优先切换 Node 到 **16 LTS**
- 删除并重装依赖（可先删 `node_modules` 再 `npm install`）

