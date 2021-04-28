package com.gitee.pagesmanager.server.api;

import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.pagesmanager.server.entity.Template;
import com.gitee.pagesmanager.server.mapper.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 模板
 * @author tanghc
 */
@ApiService
@ApiDoc("模板")
public class TemplateApi {

    @Autowired
    TemplateMapper templateMapper;

    @Api(name = "template.listall")
    @ApiDocMethod(description = "模板列表")
    List<Template> listAll(){
        Query query = new Query();
        query.orderby("id", Sort.ASC);
        return templateMapper.list(query);
    }

}
