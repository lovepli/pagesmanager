package com.gitee.pagesmanager.server.api.result;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class AdminUserInfoVO {
    /**
     * 数据库字段：id
     */
    private Long id;

    /**
     * 用户名, 数据库字段：username
     */
    private String username;

}