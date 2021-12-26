# 代码模板——组织架构同步

## 1. 背景介绍

组织架构数据同步到钉钉组织架构


## 2. 项目结构

**fronted**：前端模块，使用vue构建做页面展示。

**backend**：后端模块，使用springboot构建，主要功能有：使用authCode获取access_token、创建部门、创建员工信息等。

## 3. 开发环境准备

1. 需要有一个钉钉注册企业，如果没有可以创建：https://oa.dingtalk.com/register_new.htm#/

2. 成为钉钉开发者，参考文档：https://open.dingtalk.com/document/org/become-a-dingtalk-developer

3. 登录钉钉开放平台后台创建一个H5应用： https://open-dev.dingtalk.com/#/index

4. 配置应用：

① 配置开发管理，参考文档：https://open.dingtalk.com/document/org/configure-orgapp

**此处配置“应用首页地址”需公网地址，若无公网ip，可使用钉钉内网穿透工具：** https://open.dingtalk.com/document/resourcedownload/http-intranet-penetration

![img](https://img.alicdn.com/imgextra/i4/O1CN01QGY87t1lOZN65XHqR_!!6000000004809-2-tps-2870-1070.png)

② 配置相关权限，参考文档：https://open.dingtalk.com/document/orgapp-server/address-book-permissions

本demo使用接口权限：

- 通讯录部门信息读权限
- 维护通讯录的接口访问权限
- 成员信息读权限
- 通讯录部门成员读权限

![img](https://img.alicdn.com/imgextra/i2/O1CN01n0QZM321k7rcBwfsr_!!6000000007022-2-tps-2822-1080.png)

## 4. 启动项目

### 4.1 脚本启动（推荐）

脚本启动，只需执行一条命令即可启动项目，方便快速体验。

#### 4.1.1 脚本说明

① 脚本启动前置条件：

- 安装配置了java开发环境（JDK、maven）
    - jdk、maven安装配置参考文档：https://help.aliyun.com/document_detail/133192.html

- 安装配置了git工具
    - git下载地址：https://git-scm.com/downloads

② 脚本类型如下：

```shell
dingBoot-linux.sh     # linux版本
dingBoot-mac.sh       # mac版本
dingBoot-windows.bat  # windows版本
```

#### 4.1.2 下载项目

```shell
git clone https://github.com/open-dingtalk/h5app-org-sync-demo.git
```

#### 4.1.3 启动脚本

执行时，将下列命令中示例参数替换为**应用参数**，在后端模块目录（脚本同级目录）下运行命令。

① **脚本运行命令：**

- **linux系统**

```shell
./dingBoot-linux.sh start {项目名} {端口号} {appKey} {appSecret} {agentId} {corpId}
```

- **mac系统（使用终端运行，mac m1芯片暂不支持）**

```shell
./dingBoot-mac.sh start {项目名} {端口号} {appKey} {appSecret} {agentId} {corpId}
```

- **windows系统 使用cmd命令行启动**

```shell
./dingBoot-windows.bat {项目名} {端口号} {appKey} {appSecret} {agentId} {corpId}
```

- **示例（linux脚本执行）**

```sh
 ./dingBoot-linux.sh start h5-demo 8080 ding1jmkwa4o19bxxxx ua2qNVhleIx14ld6xgoZqtg84EE94sbizRvCimfXrIqYCeyj7b8QvqYxxx 122549400 ding9f50b15bccd1000
```

② **参数获取方法：**

- 项目名、端口号可自行输入

- 获取corpId——开发者后台首页：https://open-dev.dingtalk.com/#/index ![](https://img.alicdn.com/imgextra/i2/O1CN01amtWue1l5nAYRc2hd_!!6000000004768-2-tps-1414-321.png)

- 获取appKey、appSecret、agentId——开发者后台 -> 应用开发 -> 企业内部开发 -> 进入应用 -> 基础信息![](https://img.alicdn.com/imgextra/i3/O1CN01Rpfg001aSjEIczA85_!!6000000003329-2-tps-905-464.png)

#### 4.1.4 启动后配置

① **配置访问地址**

脚本启动后会自动生成临时域名，配置方法：复制域名 -> 进入开发者后台 -> 进入应用 -> 开发管理 -> 粘贴到“应用首页地址”、“PC端首页地址”

- 生成的域名： ![](https://img.alicdn.com/imgextra/i3/O1CN01lN8Myr1XIFJmlDSWf_!!6000000002900-2-tps-898-510.png)

- 配置地址： ![](https://img.alicdn.com/imgextra/i1/O1CN01IWleEp1Kw0hX9suby_!!6000000001227-2-tps-1408-489.png)

② **发布应用**

配置好地址后进入“版本管理与发布页面”，发布应用，发布后即可在PC钉钉或移动钉钉工作台访问应用

- 发布应用： ![](https://img.alicdn.com/imgextra/i4/O1CN01DTtp4E1qAtfDeGORj_!!6000000005456-2-tps-1414-479.png)

### 4.2 手动启动（与脚本启动二选一）

如果想快速体验demo，建议使用脚本启动。手动启动所需开发环境同上一致，推荐使用ide来启动和调试。

#### 4.2.1 下载项目

```shell
git clone https://github.com/open-dingtalk/h5app-org-sync-demo.git
```

#### 4.2.2 配置应用参数

获取到以下参数，修改后端application.yaml

```yaml
 server.port:  ${port} app_key:      ${appKey}   app_secret:   ${appSecret}   agent_id:     ${agentId}   corp_id:      ${corpId}
```

参数获取方法：登录开发者后台

- port自行设置

- 获取corpId——开发者后台首页：https://open-dev.dingtalk.com/#/index ![](https://img.alicdn.com/imgextra/i2/O1CN01amtWue1l5nAYRc2hd_!!6000000004768-2-tps-1414-321.png)

- 获取appKey、appSecret、agentId——开发者后台 -> 应用开发 -> 企业内部开发 -> 进入应用 -> 基础信息 ![](https://img.alicdn.com/imgextra/i3/O1CN01Rpfg001aSjEIczA85_!!6000000003329-2-tps-905-464.png)

#### 4.2.3 修改前端页面（非必要步骤）

此demo中，前端项目已经编译成静态资源加入到后端模块中，可直接启动体验，如不修改页面，此步骤可跳过。

① 前端开发环境：

如需修改前端页面，请安装node.js环境，参考文档：https://help.aliyun.com/document_detail/139207.html

② 编译命令：

修改完页面后，命令行中（前端项目目录下）执行以下命令，编译打包生成静态资源文件

```shell
npm installnpm run build
```

③ 将编译好的静态资源放入后端：

![image-20210706173224172](https://img.alicdn.com/imgextra/i2/O1CN01QLp1Qw1TCVrPddfjZ_!!6000000002346-2-tps-322-521.png)

#### 4.2.4 启动项目

- 启动SpringBoot（运行启动类Application.java）
- 启动内网穿透工具
    - 钉钉内网穿透工具，下载地址：https://open.dingtalk.com/document/resourcedownload/http-intranet-penetration
    - 配置应用访问链接、发布应用（参考上方4.1.4步骤 -> “启动后配置”）
- 移动端/PC端钉钉点击工作台，找到应用，进入应用体验demo

## 5. 页面展示

主页面

![](https://img.alicdn.com/imgextra/i1/O1CN01lrCvHs1tShuqjEZQc_!!6000000005901-2-tps-450-292.png)

oa查看组织信息

![](https://img.alicdn.com/imgextra/i4/O1CN01dMANZ91FKqU9YLqnb_!!6000000000469-2-tps-1096-581.png)

## 6. 参考文档

1. 同步通讯录权限申请，文档链接：https://open.dingtalk.com/document/orgapp-server/address-book-permissions
2. 创建用户：https://open.dingtalk.com/document/orgapp-server/user-information-creation
3. 更新用户信息：https://open.dingtalk.com/document/orgapp-server/user-information-update
4. 删除用户：https://open.dingtalk.com/document/orgapp-server/delete-a-user
5. 根据手机号获取userId：https://open.dingtalk.com/document/orgapp-server/query-users-by-phone-number
6. 创建部门：https://open.dingtalk.com/document/orgapp-server/create-a-department-v2
7. 更新部门：https://open.dingtalk.com/document/orgapp-server/update-a-department-v2

## 7.免责声明

- 此代码示例仅用于学习参考，请勿直接用作生产线上代码，如线上使用出现故障损失，后果自行承担。
- 此示例中“内网穿透工具”仅用于开发测试阶段，请勿用于生成线上环境，如出现稳定性问题，后果自行承担。

## **8.常见问题**

Q：为什么脚本启动不成功？

A：请检查是否安装Java开发环境，请核对启动命令参数是否正确，检查命令执行目录是否正确。

-----

Q：为什么启动后页面功能报错？

A：请检查相关权限是否申请。

-----

Q：为什么应用打开是错误页面？

A：请检查是否启动内网穿透工具并配置了应用首页地址。

