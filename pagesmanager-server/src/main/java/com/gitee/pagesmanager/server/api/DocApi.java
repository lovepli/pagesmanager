package com.gitee.pagesmanager.server.api;

import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.support.PageEasyui;
import com.gitee.fastmybatis.core.util.MapperUtil;
import com.gitee.fastmybatis.core.util.MyBeanUtil;
import com.gitee.pagesmanager.server.api.param.*;
import com.gitee.pagesmanager.server.api.result.DocDetailVO;
import com.gitee.pagesmanager.server.api.result.DocVO;
import com.gitee.pagesmanager.server.api.result.PageTreeGrid;
import com.gitee.pagesmanager.server.common.FrontException;
import com.gitee.pagesmanager.server.entity.Doc;
import com.gitee.pagesmanager.server.entity.DocContent;
import com.gitee.pagesmanager.server.entity.Project;
import com.gitee.pagesmanager.server.mapper.DocContentMapper;
import com.gitee.pagesmanager.server.mapper.DocMapper;
import com.gitee.pagesmanager.server.mapper.ProjectMapper;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tanghc
 */
@ApiService
@ApiDoc("文档")
public class DocApi {

    private static final int SHOW = 1;
    private static final int ROOT_PARENT = 0;
    private static final int NOT_DEL = 0;
    private static final int BASE_ORDER_INDEX = 100000;

    @Autowired
    DocMapper docMapper;
    @Autowired
    DocContentMapper docContentMapper;
    @Autowired
    ProjectMapper projectMapper;


    @Api(name = "doc.folder.create")
    @ApiDocMethod(description = "添加目录")
    public void createFolder(FolderCreateParam param) {
        Project project = projectMapper.getById(param.getProjectId());
        Assert.notNull(project, "项目不存在");

        Doc doc = new Doc();
        MyBeanUtil.copyProperties(param, doc);

        doc.setProjectId(project.getId());
        doc.setIsShow(SHOW);
        doc.setParentId(ROOT_PARENT);
        doc.setIsDeleted(NOT_DEL);

        Query query = new Query().eq("parent_id", 0);
        long count = docMapper.getCount(query);
        long orderIndex = ++count * BASE_ORDER_INDEX;
        doc.setOrderIndex((int)orderIndex);

        docMapper.save(doc);
    }

    /**
     * 添加文档
     *
     * @param param
     */
    @Api(name = "doc.page.create")
    @ApiDocMethod(description = "添加文档")
    @Transactional(rollbackFor = Exception.class)
    public void create(DocCreateParam param) {
        Project project = projectMapper.getById(param.getProjectId());
        Assert.notNull(project, "项目不存在");

        Doc doc = new Doc();
        MyBeanUtil.copyProperties(param, doc);

        doc.setProjectId(project.getId());
        doc.setIsShow(SHOW);

        int parentId = 0;
        int orderIndex = param.getOrderIndex() == null ? BASE_ORDER_INDEX : param.getOrderIndex();

        if (param.getParentId() != null) {
            Doc parentDoc = docMapper.getById(param.getParentId());
            if (parentDoc != null) {
                parentId = parentDoc.getId();
                orderIndex = parentDoc.getOrderIndex() / 10;
            }
        }
        doc.setParentId(parentId);
        doc.setOrderIndex(orderIndex);
        doc.setIsDeleted(NOT_DEL);

        int i = docMapper.save(doc);

        if (i > 0) {
            DocContent content = new DocContent();
            content.setContent(param.getContent());
            content.setDocId(doc.getId());
            docContentMapper.save(content);
        }
    }

    /**
     * 修改文档
     *
     * @param param
     */
    @Api(name = "doc.page.update")
    @ApiDocMethod(description = "修改文档")
    public void update(DocUpdateParam param) {
        Doc doc = docMapper.getById(param.getId());
        Assert.notNull(doc, "doc不能为null");
        MyBeanUtil.copyProperties(param, doc);
        docMapper.update(doc);

        DocContent content = docContentMapper.getByColumn("doc_id", doc.getId());
        if (content != null) {
            content.setContent(param.getContent());
            docContentMapper.update(content);
        } else {
            DocContent contentSave = new DocContent();
            contentSave.setContent(param.getContent());
            contentSave.setDocId(doc.getId());
            docContentMapper.save(contentSave);
        }
    }


    @Api(name = "doc.treegrid.page")
    @ApiDocMethod(description = "查询文档")
    public PageEasyui<DocVO> pageDoc(DocSearchParam param) {
        Query query = param.toQuery();
        query.orderby("order_index", Sort.ASC)
                .orderby("id", Sort.ASC)
                .setQueryAll(true);
        PageEasyui<DocVO> pageInfo = MapperUtil.queryForEasyuiDatagrid(docMapper, query, DocVO.class);
        return pageInfo;
    }

    @Api(name = "nologin.doc.treegrid.page")
    public PageTreeGrid pageDocNologin(DocSearchParam param) {
        PageEasyui<DocVO> pageEasyui = this.pageDoc(param);
        PageTreeGrid pageTreeGrid = new PageTreeGrid();
        pageTreeGrid.setPageEasyui(pageEasyui);
        Map<String, Object> map = new HashMap<>(16);
        Project project = projectMapper.getById(param.getProjectId());
        map.put("expandAll", BooleanUtils.toBoolean(project.getMenuExpandall()));
        pageTreeGrid.setProperties(map);
        return pageTreeGrid;
    }

    @Api(name = "doc.detail.get")
    @ApiDocMethod(description = "文档详情")
    public DocDetailVO getRes(IdParam param) {

        DocDetailVO vo = new DocDetailVO();

        Doc doc = docMapper.getById(param.getId());
        if (doc == null) {
            throw new FrontException("文档不存在");
        }
        MyBeanUtil.copyProperties(doc, vo);

        DocContent content = docContentMapper.getByColumn("doc_id", doc.getId());
        if (content != null) {
            vo.setContent(content.getContent());
        }

        Integer parentId = doc.getParentId();
        if (parentId != null && parentId > 0) {
            Doc parent = docMapper.getById(parentId);
            vo.setParentName(parent.getName());
        }

        return vo;
    }

    @Api(name = "nologin.doc.detail.get")
    public DocDetailVO getResNologin(IdParam param) {
        return this.getRes(param);
    }

    @Api(name = "doc.delete")
    @ApiDocMethod(description = "删除文档")
    void delDoc(IdParam param) {
        docMapper.deleteById(param.getId());
    }

}
