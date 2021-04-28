package com.gitee.pagesmanager.server.entity;

import com.gitee.fastmybatis.core.annotation.LogicDelete;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：doc
 */
@Data
@Table(name = "doc")
public class Doc {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Integer id;

    /**  数据库字段：name */
    private String name;

    /**  数据库字段：order_index */
    private Integer orderIndex;

    /**  数据库字段：parent_id */
    private Integer parentId;

    /**  数据库字段：project_id */
    private Integer projectId;

    /**  数据库字段：is_show */
    private Integer isShow;

    /**  数据库字段：is_deleted */
    @LogicDelete
    private Integer isDeleted;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gm_update */
    private Date gmtUpdate;

}
