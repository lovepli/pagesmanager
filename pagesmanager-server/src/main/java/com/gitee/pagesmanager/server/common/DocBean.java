package com.gitee.pagesmanager.server.common;

import java.util.List;

/**
 * @author tanghc
 */
public class DocBean {
    /**  数据库字段：id */
    private Integer id;

    /**  数据库字段：name */
    private String name;

    private String content;

    /**  数据库字段：parent_id */
    private Integer parentId;

    /**  数据库字段：project_id */
    private Integer projectId;

    private List<DocBean> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<DocBean> getChildren() {
        return children;
    }

    public void setChildren(List<DocBean> children) {
        this.children = children;
    }
}
