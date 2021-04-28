# Pages Manager vue项目

## 效果图

![pagesmanager](https://images.gitee.com/uploads/images/2019/0130/161244_13fc59f8_332975.png "pagesmanager.png")

1. 启动服务端程序
2. 打开`config/dev.env.js`，修改`BASE_API`参数(如果没有改动服务端端口，不用修改此项)
3. 构建并启动vue项目

用户名密码：admin/123456

> 数据库存放的密码 = md5(md5(123456))

```
# 建议不要用cnpm  安装有各种诡异的bug 可以通过如下操作解决npm速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# Serve with hot reload at localhost:9528
npm run dev

# Build for production with minification
npm run build

# Build for production and view the bundle analyzer report
npm run build --report
```

- 修改端口号：打开config/index.js，找到`port`属性

## 打包放入到服务端步骤

如果想要把vue打包放到服务端，步骤如下：

- 打开`config/index.js`，找到`build`下的`assetsPublicPath`参数，设置成'./'
- 打开`config/prod.env.js`，配置`BASE_API`参数，设置成'/api'
- 执行`npm run build`进行打包，结果在dest下
- 打包完成后，把dest中的所有文件，放到`pagesmanager-server/src/main/resources/public`下