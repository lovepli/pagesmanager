package com.gitee.pagesmanager.server.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tanghc
 */
@Data
public class IdParam {

    @ApiDocField(description = "id", required = true, example = "1")
    @NotNull(message = "id不能为空")
    private Integer id;
}
