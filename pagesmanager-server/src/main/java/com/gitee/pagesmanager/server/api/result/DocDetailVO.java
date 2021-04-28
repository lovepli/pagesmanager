package com.gitee.pagesmanager.server.api.result;

import lombok.Data;

@Data
public class DocDetailVO {
    private Integer id;
    private String name;
    private String content;
    private Integer parentId;
    private String parentName;
    private Integer isShow;
}