package com.gitee.pagesmanager.server.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DocUpdateParam {
    @NotNull(message = "id不能空")
    @ApiDocField(description = "文档id", required = true)
    private Integer id;

    @NotBlank(message = "文档名称不能空")
    @Length(min = 1, max = 50, message = "文档名称长度为1~50")
    @ApiDocField(description = "文档名称", required = true)
    // 文档名称
    private String name;

    @ApiDocField(description = "资源内容")
    private String content;
    
    @NotNull(message = "parentId不能空")
    @ApiDocField(description = "parentId", required = true)
    private Integer parentId;

    @ApiDocField(description = "是否显示")
    private Integer isShow = 1;

}
