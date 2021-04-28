--
-- 由SQLiteStudio v3.1.1 产生的文件 周五 十一月 2 15:36:39 2018
--
-- 文本编码：UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

CREATE TABLE admin_user_info (
   id         	INTEGER      	PRIMARY KEY AUTOINCREMENT,
   username 	varchar(64) 	NOT NULL ,
   password 	varchar(128) 	NOT NULL ,
   status 	INTEGER  NOT 	NULL ,
   gmt_create 	DATETIME     	NOT NULL,
   gmt_update 	DATETIME     	NOT NULL
);

-- 表：doc
CREATE TABLE doc (
    id          INTEGER      PRIMARY KEY AUTOINCREMENT,
    name        VARCHAR (100) NOT NULL,
    order_index INTEGER      NOT NULL,
    parent_id   INTEGER      NOT NULL,
    project_id  INTEGER      NOT NULL,
    is_show     INTEGER      NOT NULL,
    is_deleted  INTEGER      NOT NULL DEFAULT(0),
    gmt_create  DATETIME     NOT NULL,
    gm_update   DATETIME     NOT NULL
);


-- 表：doc_content
CREATE TABLE doc_content (
    id         INTEGER  PRIMARY KEY AUTOINCREMENT,
    content    TEXT     NOT NULL,
    doc_id     INTEGER  NOT NULL,
    gmt_create DATETIME NOT NULL,
    gmt_update DATETIME NOT NULL
);


-- 表：project
CREATE TABLE project (
    id         INTEGER      PRIMARY KEY AUTOINCREMENT,
    name       VARCHAR (100) NOT NULL,
    local_git_path VARCHAR (100),
    git_url        VARCHAR (200),
    git_cmd        VARCHAR (200),
    menu_expandall integer DEFAULT(1),
    gmt_create DATETIME     NOT NULL,
    gmt_update DATETIME     NOT NULL
);

CREATE TABLE template (
  id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
  name varchar(100),
  content text
);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;

INSERT INTO template ( name, content) VALUES
	('Api模板','**简要描述：**\n\n- 用户注册接口\n\n**请求URL：**\n- http://xx.com/api/user/register\n\n**请求方式：**\n- POST\n\n**参数：**\n\n|参数名|必选|类型|说明|\n|:----    |:---|:----- |-----   |\n|username |是  |string |用户名   |\n|password |是  |string | 密码    |\n|name     |否  |string | 昵称    |\n\n **返回示例**\n\n\n  {\n    "error_code": 0,\n    "data": {\n      "uid": "1",\n      "username": "12154545",\n      "name": "吴系挂",\n      "groupid": 2 ,\n      "reg_time": "1436864169",\n      "last_login_time": "0",\n    }\n  }\n\n\n **返回参数说明**\n\n|参数名|类型|说明|\n|:-----  |:-----|-----                           |\n|groupid |int   |用户组id，1：超级管理员；2：普通用户  |\n\n **备注**\n\n- 更多返回错误代码请看首页的错误代码描述\n');


INSERT INTO admin_user_info ( username, password, status, gmt_create, gmt_update) VALUES 
	('admin','14e1b600b1fd579f47433b88e8d85291',1,1547519025774,1547519025774);
