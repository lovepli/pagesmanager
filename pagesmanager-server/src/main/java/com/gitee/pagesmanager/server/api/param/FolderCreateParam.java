package com.gitee.pagesmanager.server.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FolderCreateParam {
    @NotBlank(message = "目录名称不能空")
    @Length(min = 1, max = 50, message = "目录名称长度为1~50")
    @ApiDocField(description = "目录名称", required = true, example = "目录1")
    private String name;

    @NotNull(message = "所属项目不能空")
    @ApiDocField(description = "所属项目", required = true, example = "0")
    private Integer projectId;

}
