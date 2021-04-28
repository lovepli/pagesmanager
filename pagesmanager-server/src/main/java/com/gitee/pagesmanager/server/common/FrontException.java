package com.gitee.pagesmanager.server.common;

import com.gitee.easyopen.exception.ApiException;

/**
 * @author tanghc
 */
public class FrontException extends ApiException {

    private static final String COMMON_ERROR_CODE = "500";

    public FrontException(String msg, String code) {
        super(msg, code);
    }

    public FrontException(String msg) {
        super(msg, COMMON_ERROR_CODE);
    }
}
