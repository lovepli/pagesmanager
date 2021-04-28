# Pages Manager

可本地管理Pages服务内容，一键生成漂亮的文档界面。[在线预览](https://durcframework.gitee.io/pages-doc)

简单、轻便，无需安装数据库。

- 框架：spring-boot
- 数据库：sqlite

## 效果图

![pagesmanager](https://images.gitee.com/uploads/images/2019/0130/161244_13fc59f8_332975.png "pagesmanager.png")

## 原理

1. 本地维护一组markdown文档
2. 将markdown文件上传到git
3. 项目开通Pages服务生成网页（基于[docsify](https://docsify.js.org/)）

## 使用方式

- 下载项目
- IDE导入maven工程
- 运行PagesmanagerSpringbootApplication.java启动
- 浏览器打开`http://localhost:8088`

## 创建新项目

- 首先在git上新建一个分支，这个分支仅针对pages文档，码云pages服务选择这个分支
- clone新建的分支到本地（使用Git下载指定分支命令为：git clone -b 分支名仓库地址）
- 启动服务，浏览器打开`http://localhost:8088`，点击右上方的`新建项目`
- 依次填入项目信息，点击保存。确保填写正确，不然无法推送到码云。
- 选择新建好的项目。

    1. 点击`添加目录`，新建一个父节点
    2. 点击父节点的`添加页面`，输入内容
    3. 点击`发布文档`
    
- 转到码云，开通Pages服务，选择分支，部署目录填`pagesmanager-dest/docs`

后续只需编辑文档，然后点击发布即可。

**注意**：Pages部署目录一定要填`pagesmanager-dest/docs`不然无法查看文档。

## 工程说明

- pagesmanager-server：服务端
- pagesmanager-vue：前端vue界面

## 问题交流

Q群：328419269
