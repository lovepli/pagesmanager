package com.gitee.pagesmanager.server.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DocCreateParam {
    @NotBlank(message = "文档名称不能空")
    @Length(min = 1, max = 50, message = "文档名称长度为1~50")
    @ApiDocField(description = "文档名称", required = true, example = "文档1")
    private String name;

    @NotNull(message = "所属项目不能空")
    @ApiDocField(description = "所属项目", required = true, example = "0")
    private Integer projectId;

    @ApiDocField(description = "文档内容", required = true, example = "内容。。")
    private String content;

    @ApiDocField(description = "排序索引", example = "100000")
    private Integer orderIndex;

    private Integer parentId;

    @ApiDocField(description = "是否显示")
    private Integer isShow = 1;

}
