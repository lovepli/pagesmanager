package com.gitee.pagesmanager.server.api.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tanghc
 */
@Data
public class PropertyGridParam {
    @NotNull(message = "field不能为空")
    private Integer id;

    @NotBlank(message = "field不能为空")
    private String field;

    @NotBlank(message = "值不能为空")
    private String value;

}
