package com.gitee.pagesmanager.server.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ProjectAddParam {
    @NotBlank(message = "项目名称不能为空")
    @Length(max = 20, message = "项目名长度必须小于等于20")
    @ApiDocField(description = "项目名称", required = true, example = "第一个项目")
    // 项目名称
    private String name;

    @Length(max = 200, message = "本地Git项目路径长度必须小于等于200")
    @ApiDocField(description = "本地Git项目路径", required = true, example = "D:/project/myproject/")
    /**  数据库字段：local_git_path */
    private String localGitPath;

    @Length(max = 200, message = "GitUrl长度必须小于等于200")
    @ApiDocField(description = "GitUrl", required = true, example = "http://www.xxx/aaa.git")
    private String gitUrl;

    @ApiDocField(description = "git命令")
    /**  数据库字段：git_cmd */
    private String gitCmd;

}
