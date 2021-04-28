package com.gitee.pagesmanager.server.service;

import com.gitee.easyopen.bean.Consts;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.Sort;
import com.gitee.fastmybatis.core.util.MyBeanUtil;
import com.gitee.pagesmanager.server.common.DocBean;
import com.gitee.pagesmanager.server.common.ReleaseContext;
import com.gitee.pagesmanager.server.entity.Doc;
import com.gitee.pagesmanager.server.entity.DocContent;
import com.gitee.pagesmanager.server.mapper.DocContentMapper;
import com.gitee.pagesmanager.server.mapper.DocMapper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author tanghc
 */
@Service
public class DocCreateService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private static final String LINE = "\n";
    private static final String SIDEBAR_FILENAME = "_sidebar.md";

    private static final String MODULE_TPL = LINE + LINE + "* %s" + LINE + LINE;
    // files/hello.md
    private static final String FILE_NAME_TPL = "%s.md";
    //  *[hello](files/hello.md)
    private static final String FILE_TPL = "  * [%s](%s)" + LINE;

    private static final String FILE_FOLDER_NAME = "files";

    @Autowired
    DocMapper docMapper;
    @Autowired
    DocContentMapper docContentMapper;

    /**
     * 生成markdown文档
     * @param releaseContext
     * @throws IOException
     */
    public void createMarkdownDoc(ReleaseContext releaseContext) throws IOException {
        List<DocBean> docList = this.listAllDoc(releaseContext.getId());
        List<DocBean> docTree = buildTreeData(docList);
        // 项目根目录
        String destPath = releaseContext.getDestPath();
        String docsPath = destPath + File.separator + "docs";
        // 存放markdown目录，project/docs/files
        String docFolderPath = docsPath + File.separator + FILE_FOLDER_NAME;
        logger.info("生成markdown文档文件，保存路径：{}", destPath);
        
        this.createSidebar(docTree, docsPath);
        this.removeDocFiles(docFolderPath);

        for (DocBean docBean : docTree) {
            List<DocBean> children = docBean.getChildren();
            for (DocBean item : children) {
                this.createDocFile(item, docFolderPath);
            }
        }
    }

    /**
     * 删除本地文档
     * @param docFolderPath
     */
    private void removeDocFiles(String docFolderPath) {
        FileUtils.deleteQuietly(new File(docFolderPath));
    }

    /**
     * 生成侧边菜单
     *
     * @param docTree
     */
    protected void createSidebar(Collection<DocBean> docTree, String docsPath) throws IOException {
        StringBuilder sidebarContent = new StringBuilder();
        for (DocBean docBean : docTree) {
            // * Getting started 一级
            sidebarContent.append(String.format(MODULE_TPL, docBean.getName()));
            List<DocBean> children = docBean.getChildren();
            for (DocBean child : children) {
                // files/quickstart.md
                String filename = FILE_FOLDER_NAME + File.separator + getFilename(child) + "?q=" + System.currentTimeMillis();
                sidebarContent.append(String.format(FILE_TPL, child.getName(), filename));
            }
        }
        String siderbarFilePath = docsPath + File.separator +  SIDEBAR_FILENAME;
        FileUtils.write(new File(siderbarFilePath), sidebarContent.toString(), Consts.UTF8);
    }

    protected void createDocFile(DocBean docBean, String docFolderPath) throws IOException {
        // docFolderPath/files/hello.md
        String filepath = docFolderPath + File.separator + getFilename(docBean);
        String fileContent = docBean.getContent();
        FileUtils.write(new File(filepath), fileContent, Consts.UTF8);
    }

    private static String getFilename(DocBean docBean) {
        String filename = String.format(FILE_NAME_TPL, docBean.getName());
        return formatFileName(filename);
    }

    private static String formatFileName(String name) {
        name = StringUtils.trimWhitespace(name);
        name = name.replaceAll("\\s+", "\\-");
        return name;
    }

    private List<DocBean> listAllDoc(int projectId) {
        Query query = new Query();
        query.eq("project_id", projectId)
                .eq("is_show", 1)
                .orderby("order_index", Sort.ASC).orderby("id", Sort.ASC);

        List<Doc> docList = docMapper.list(query);
        List<DocBean> ret = new ArrayList<>(docList.size());
        for (Doc doc : docList) {
            DocBean docBean = new DocBean();
            MyBeanUtil.copyProperties(doc, docBean);
            if(doc.getParentId() != 0) {
                DocContent docContent = docContentMapper.getByColumn("doc_id", doc.getId());
                docBean.setContent(docContent.getContent());
            }
            ret.add(docBean);
        }
        return ret;
    }

    /**
     * 构建树形菜单
     * @param list
     * @return
     */
    public static List<DocBean> buildTreeData(List<DocBean> list) {
        List<DocBean> menu = new ArrayList<DocBean>();
        resolveMenuTree(list, 0, menu);
        return menu;
    }

    public static int resolveMenuTree(List<DocBean> menus, int parentMenuId,
                                      List<DocBean> nodes) {

        int count = 0;
        for (DocBean menu : menus) {
            if (menu.getParentId() == parentMenuId) {
                DocBean node = new DocBean();

                nodes.add(node);
                node.setName(menu.getName());
                node.setContent(menu.getContent());
                node.setParentId(menu.getParentId());
                node.setChildren(new ArrayList<DocBean>());

                resolveMenuTree(menus, menu.getId(), node.getChildren());
                count++;
            }
        }
        return count;
    }


}
