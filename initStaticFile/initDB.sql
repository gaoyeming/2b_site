-- 若没有新建一个
CREATE DATABASE my_site;

-- 使用数据库
use my_site;

-- 系统操作日志表
CREATE TABLE `sys_operation_log` (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  create_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
  update_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
  oper_user varchar(50) NOT NULL COMMENT '操作用户',
  oper_ip varchar(25) NOT NULL DEFAULT '' COMMENT '操作人ip',
  oper_url varchar(200) NOT NULL DEFAULT '' COMMENT '操作路径',
  oper_title varchar(100) NOT NULL DEFAULT '' COMMENT '操作标题',
  oper_method varchar(200) NOT NULL DEFAULT '' COMMENT '操作方法',
  oper_params varchar(1024) NOT NULL DEFAULT '' COMMENT '入参',
  oper_result varchar(1024) NOT NULL DEFAULT '' COMMENT '出参',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志表';

-- 系统参数配置表
CREATE TABLE `sys_config` (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  create_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
  update_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
  config_name varchar(100) NOT NULL DEFAULT '' COMMENT '配置项的名称',
  config_value varchar(200) NOT NULL DEFAULT '' COMMENT '配置项的值',
  config_desc varchar(200) NOT NULL DEFAULT '' COMMENT '配置项说明',
  is_deleted tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_config_name` (`config_name`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统参数配置表';

insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('footerAbout','2BB personal blog to keep','底部About');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('footerCopyRight','2020 2BB','底部Copy Right');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('footerICP','浙ICP备1※※※※※※※号-※','底部备案号');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('footerPoweredBy','https://github.com/gaoyeming','底部Powered By');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('footerPoweredByURL','https://github.com/gaoyeming','底部Powered By URL');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('websiteDescription','personal blog是SpringBoot2+Thymeleaf+JPA建造的个人博客网站.SpringBoot实战博客源码.个人博客搭建','站点描述');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('websiteIcon','/admin/dist/img/favicon.png','favicon.ico');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('websiteLogo','/admin/dist/img/logo2.png','站点Logo');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('websiteName','2BB BLOG','站点名称');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('yourAvatar','/admin/dist/img/personal.jpg','个人头像');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('yourEmail','yeming.gao@aliyun.com','个人邮箱');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('yourName','我爱2B哥','个人名称');

-- 后台登陆用户信息表
CREATE TABLE `backstage_user` (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  create_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
  update_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
  login_user varchar(50) NOT NULL DEFAULT '' COMMENT '管理员登陆名称',
  login_password varchar(50) NOT NULL DEFAULT '' COMMENT '管理员登陆密码',
  nick_name varchar(50) NOT NULL DEFAULT '' COMMENT '管理员显示昵称',
  locked tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  is_deleted tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_login_user` (`login_user`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='后台登陆用户信息表';

-- 初始化数据
INSERT INTO `backstage_user`(`login_user`, `login_password`, `nick_name`, `locked`)
VALUES ('admin', 'D89D/OvFrItmxI9ct8JbAg==', '管理员', 0);

-- 分类表
CREATE TABLE `backstage_category` (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  create_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
  update_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
  category_name varchar(50) NOT NULL DEFAULT '' COMMENT '分类的名称',
  category_icon varchar(200) NOT NULL DEFAULT '' COMMENT '分类的图标',
  category_rank int(11) NOT NULL DEFAULT 0 COMMENT '分类的排序值 被使用的越多数值越大',
  is_deleted tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_category_name` (`category_name`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

-- 标签表
CREATE TABLE `backstage_tag` (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  create_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
  update_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
  tag_name varchar(50) NOT NULL DEFAULT '' COMMENT '标签名称',
  is_deleted tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  PRIMARY KEY (id),
  UNIQUE KEY `uq_tag_name` (`tag_name`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- 博客表
CREATE TABLE `backstage_blog` (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  create_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
  update_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
  blog_title varchar(200) NOT NULL DEFAULT '' COMMENT '博客标题',
  blog_sub_url varchar(200) NOT NULL DEFAULT '' COMMENT '博客自定义路径url',
  blog_cover_image varchar(200) NOT NULL DEFAULT '' COMMENT '博客封面图',
  blog_content mediumtext NOT NULL COMMENT '博客内容',
  blog_category_id int(11) NOT NULL DEFAULT 0 COMMENT '博客分类id',
  blog_status tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-草稿 1-发布',
  blog_views bigint(20) NOT NULL DEFAULT 0 COMMENT '阅读量',
  enable_comment tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-允许评论 1-不允许评论',
  is_deleted tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0=否 1=是',
  PRIMARY KEY (id),
  UNIQUE KEY `uq_blog_title` (`blog_title`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='博客表';

-- 博客标签关联表
CREATE TABLE `backstage_blog_tag_relation` (
  id int NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  create_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
  update_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
  blog_id int(11) NOT NULL DEFAULT -1 COMMENT '关联的blog主键',
  tag_id int(11) NOT NULL DEFAULT -1 COMMENT '关联的tag主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_blog_id_tag_id` (`blog_id`,`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='博客标签关联表';

-- 博客评论表
CREATE TABLE `backstage_blog_comment` (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  create_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
  update_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
  blog_id int(11) NOT NULL DEFAULT -1 COMMENT '关联的blog主键',
  commentator varchar(50) NOT NULL DEFAULT '' COMMENT '评论者名称',
  email varchar(100) NOT NULL DEFAULT '' COMMENT '评论人的邮箱',
  website_url varchar(50) NOT NULL DEFAULT '' COMMENT '网址',
  comment_body varchar(200) NOT NULL DEFAULT '' COMMENT '评论内容',
  commentator_ip varchar(20) NOT NULL DEFAULT '' COMMENT '评论时的ip地址',
  is_replyed tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否汇付 0-未回复 1-已回复',
  reply_body varchar(200) NOT NULL DEFAULT '' COMMENT '回复内容',
  reply_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  comment_status tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否审核通过 0-未审核 1-审核通过',
  is_deleted tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='博客评论表';

-- 友链表
CREATE TABLE `backstage_link` (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  create_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
  update_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
  link_type tinyint(4) NOT NULL DEFAULT 0 COMMENT '友链类别 0-友链 1-推荐 2-个人网站',
  link_name varchar(50) NOT NULL DEFAULT '' COMMENT '网站名称',
  link_url varchar(100) NOT NULL DEFAULT '' COMMENT '网站链接',
  link_description varchar(100) NOT NULL DEFAULT '' COMMENT '网站描述',
  link_rank int(11) NOT NULL DEFAULT 0 COMMENT '用于列表排序',
  is_deleted tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (id),
  UNIQUE KEY `uq_link_url` (`link_url`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='友链表';

-- 留言信息表
CREATE TABLE `leave_message` (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '自增物理主键',
  create_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间，应用不用维护',
  update_time datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间，应用不用维护',
  name varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  email varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱',
  message varchar(200) NOT NULL DEFAULT '' COMMENT '留言',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='留言信息表';
