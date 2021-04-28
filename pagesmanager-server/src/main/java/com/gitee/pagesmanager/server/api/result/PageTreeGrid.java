package com.gitee.pagesmanager.server.api.result;

import com.gitee.fastmybatis.core.support.PageEasyui;

import java.util.Map;

/**
 * @author tanghc
 */
public class PageTreeGrid {
    private PageEasyui<DocVO> pageEasyui;
    private Map<String, Object> properties;

    public PageEasyui<DocVO> getPageEasyui() {
        return pageEasyui;
    }

    public void setPageEasyui(PageEasyui<DocVO> pageEasyui) {
        this.pageEasyui = pageEasyui;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
