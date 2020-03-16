

我爱2B哥的个人博客

##### 首先感谢博主 ``ZHENFENG13`` ;本博客是在 [ZHENFENG13/My-Blog](https://github.com/ZHENFENG13/My-Blog) 博客基础上修改的,再熟悉的代码后自己敲了一边，并修复一些bug

##### 其次，如果您觉得不错可以给项目一个star;另外附上本人博客地址:[我爱2B哥の博客](http://blog.yeming.org.cn")
##### ``本人热烈欢迎跟各路大神友链,也请各路大神有空对博客内容进行纠错知道,非常感谢``

##### 《Docker + SpringBoot + JPA/H2 + Thymeleaf 搭建美观实用的个人博客》介绍
+ ![avatar](/initStaticFile/md-images/项目配置文件结构.png)
   - logs为本地运行日志目录，可自行修改
   - static,templates为前端Thymeleaf目录
   - 配置文件分三个:dev-开发测试环境,可以配置本地数据库;local-本地直接运行环境,数据库使用的是H2内存数据库;prod-即为需要发布的环境
+ 搭建环境
   - 配合DockerFile构建一个镜像,直接可以运行(前提初始化好数据库),运行脚本[build.sh](file://initStaticFile/md-images/build.sh)
   - 运行镜像:[run.sh](file://initStaticFile/md-images/run.sh)
   - 关闭镜像:[stop.sh](file://initStaticFile/md-images/stop.sh)
   - 相关静态资源可使用Nginx自行映射
+ 项目集成了Swagger、WebSocket
   
##### 效果预览

**博客首页**

- ![avatar](/initStaticFile/md-images/博客-首页.png)

**博客详情**

- ![avatar](/initStaticFile/md-images/博客-详情.png)

**博客友链**

- ![avatar](/initStaticFile/md-images/博客-友链.png)

**后台登录页**

- ![avatar](/initStaticFile/md-images/后台-登陆.png)

**后台首页**

- ![avatar](/initStaticFile/md-images/后台-首页.png)

**后台发布博客**

- ![avatar](/initStaticFile/md-images/后台-发布博客.png)

**后台博客管理**

- ![avatar](/initStaticFile/md-images/后台-博客管理.png)

**后台评论管理**

- ![avatar](/initStaticFile/md-images/后台-评论管理.png)

**后台分类管理**

- ![avatar](/initStaticFile/md-images/后台-分类管理.png)

**后台标签管理**

- ![avatar](/initStaticFile/md-images/后台-标签管理.png)

**后台友链管理**

- ![avatar](/initStaticFile/md-images/后台-友链管理.png)

**后台系统配置**

- ![avatar](/initStaticFile/md-images/后台-系统配置.png)

**后台修改密码**

- ![avatar](/initStaticFile/md-images/后台-修改密码.png)