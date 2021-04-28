DROP TABLE IF EXISTS `admin_user_info`;
CREATE TABLE admin_user_info (
   `id` integer AUTO_INCREMENT,
   username 	varchar(64) 	NOT NULL ,
   password 	varchar(128) 	NOT NULL ,
   status 	INTEGER  NOT 	NULL ,
   gmt_create 	DATETIME     	NOT NULL,
   gmt_update 	DATETIME     	NOT NULL,
   PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `doc`;
CREATE TABLE `doc` (
  `id` integer AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `order_index` integer NOT NULL,
  `parent_id` integer NOT NULL,
  `project_id` integer NOT NULL,
  `is_show` integer NOT NULL,
  `is_deleted` integer NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE IF EXISTS `doc_content`;
CREATE TABLE `doc_content` (
  `id` integer AUTO_INCREMENT,
  `content` text NOT NULL,
  `doc_id` integer NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` integer AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `local_git_path` varchar(100),
  `git_url` varchar(200),
  `git_cmd` varchar(200),
  `menu_expandall` int(11) DEFAULT '1',
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `id` integer NOT NULL AUTO_INCREMENT,
  `name` varchar(100),
  `content` MEDIUMTEXT,
  PRIMARY KEY (`id`)
);

INSERT INTO `template` ( `name`, `content`) VALUES
	('Api模板','**简要描述：**\n\n- 用户注册接口\n\n**请求URL：**\n- `http://xx.com/api/user/register`\n\n**请求方式：**\n- POST\n\n**参数：**\n\n|参数名|必选|类型|说明|\n|:----    |:---|:----- |-----   |\n|username |是  |string |用户名   |\n|password |是  |string | 密码    |\n|name     |否  |string | 昵称    |\n\n **返回示例**\n\n```\n  {\n    "error_code": 0,\n    "data": {\n      "uid": "1",\n      "username": "12154545",\n      "name": "吴系挂",\n      "groupid": 2 ,\n      "reg_time": "1436864169",\n      "last_login_time": "0",\n    }\n  }\n```\n\n **返回参数说明**\n\n|参数名|类型|说明|\n|:-----  |:-----|-----                           |\n|groupid |int   |用户组id，1：超级管理员；2：普通用户  |\n\n **备注**\n\n- 更多返回错误代码请看首页的错误代码描述\n');

