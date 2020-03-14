-- 初始化数据
INSERT INTO `backstage_user`(`login_user`, `login_password`, `nick_name`, `locked`)
VALUES ('admin', 'D89D/OvFrItmxI9ct8JbAg==', '管理员', 0);

INSERT INTO `backstage_category`(`category_name`, `category_icon`, `category_rank`, `is_deleted`)
VALUES ('日常随笔', 'http://127.0.0.1:8080/admin/dist/img/category/06.png', 0, 0);
INSERT INTO `backstage_category`(`category_name`, `category_icon`, `category_rank`, `is_deleted`)
VALUES ('工作记录', 'http://127.0.0.1:8080/admin/dist/img/category/09.png', 0, 0);
INSERT INTO `backstage_category`(`category_name`, `category_icon`, `category_rank`, `is_deleted`)
VALUES ('技术分享', 'http://127.0.0.1:8080/admin/dist/img/category/02.png', 0, 0);

INSERT INTO `backstage_tag`(`tag_name`, `is_deleted`)
VALUES ('Java', 0);
INSERT INTO `backstage_tag`(`tag_name`, `is_deleted`)
VALUES ('Spring', 0);
INSERT INTO `backstage_tag`(`tag_name`, `is_deleted`)
VALUES ('Jpa', 0);


INSERT INTO `backstage_link`(`link_type`, `link_name`, `link_url`, `link_description`, `link_rank`, `is_deleted`)
VALUES (2, '个人网站', 'http://www.liufang.xn--6qq986b3xl/', '个人网站', 0, 0);
INSERT INTO `backstage_link`(`link_type`, `link_name`, `link_url`, `link_description`, `link_rank`, `is_deleted`)
VALUES (0, '浪人组博客', 'http://zjay.xn--6qq986b3xl:8888/', '浪人组博客', 2, 0);

insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('footerAbout','your personal blog. have fun.','底部About');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('footerCopyRight','2019 十三','底部Copy Right');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('footerICP','浙ICP备17008806号-3','底部备案号');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('footerPoweredBy','https://github.com/gaoyeming','底部Powered By');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('footerPoweredByURL','https://github.com/gaoyeming','底部Powered By URL');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('websiteDescription','personal blog是SpringBoot2+Thymeleaf+Mybatis建造的个人博客网站.SpringBoot实战博客源码.个人博客搭建','站点描述');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('websiteIcon','/admin/dist/img/favicon.png','favicon.ico');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('websiteLogo','/admin/dist/img/logo2.png','站点Logo');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('websiteName','personal blog','站点名称');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('yourAvatar','/admin/dist/img/personal.jpg','个人头像');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('yourEmail','1312510670@qq.com','个人邮箱');
insert  into `sys_config`(`config_name`,`config_value`,`config_desc`)
values ('yourName','我爱2B哥','个人名称');


