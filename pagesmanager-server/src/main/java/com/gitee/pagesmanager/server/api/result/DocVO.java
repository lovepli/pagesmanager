package com.gitee.pagesmanager.server.api.result;

import lombok.Data;

import java.util.Date;

@Data
public class DocVO {
    /**  数据库字段：id */
    private Integer id;

    /**  数据库字段：name */
    private String name;

    /**  数据库字段：order_index */
    private Integer orderIndex;

    /**  数据库字段：parent_id */
    private Integer parentId;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gm_update */
    private Date gmtUpdate;

    private Integer isShow;

}
