# 服务端

启动服务，访问：http://localhost:8088

## 打包成可执行文件

- 前提：安装JDK8,MAVEN3
- cd到本目录，执行命令`mvn clean package`，在target目录生成jar文件
- 将jar文件拷贝出来，将pagesmanager-demo.db文件拷贝出来，跟jar文件放在一起
- 执行`java -jar pagesmanager-server.jar`启动