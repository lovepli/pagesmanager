package com.gitee.pagesmanager.server.api.result;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class PropertygridRow {
    private String name;
    private String value;
    private String group;
    private String editor;
    private String field;

    public PropertygridRow(String name, Object value, String group, String editor) {
        this.name = name;
        this.value = value == null ? "" : String.valueOf(value);
        this.group = group;
        this.editor = editor;
    }

    public PropertygridRow(String field, String name, Object value, String group, String editor) {
        this.field = field;
        this.name = name;
        this.value = value == null ? "" : String.valueOf(value);
        this.group = group;
        this.editor = editor;
    }

}
