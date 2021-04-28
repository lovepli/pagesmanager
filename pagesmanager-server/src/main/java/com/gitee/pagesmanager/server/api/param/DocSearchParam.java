package com.gitee.pagesmanager.server.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.fastmybatis.core.support.EasyuiDatagridParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author tanghc
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DocSearchParam extends EasyuiDatagridParam {
    @ApiDocField(description = "项目id", required = true)
    @NotNull(message = "projectId不能为空")
    private Integer projectId;
}
