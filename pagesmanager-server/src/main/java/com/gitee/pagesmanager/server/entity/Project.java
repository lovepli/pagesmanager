package com.gitee.pagesmanager.server.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：project
 */
@Data
@Table(name = "project")
public class Project {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Integer id;

    /**  数据库字段：name */
    private String name;

    /**  数据库字段：local_git_path */
    private String localGitPath;

    /**  数据库字段：git_cmd */
    private String gitCmd;

    /**  数据库字段：git_url */
    private String gitUrl;

    /**  数据库字段：menu_expandall */
    private Integer menuExpandall;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_update */
    private Date gmtUpdate;

}
