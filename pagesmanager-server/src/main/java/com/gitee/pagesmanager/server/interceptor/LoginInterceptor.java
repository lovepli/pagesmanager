package com.gitee.pagesmanager.server.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.ApiMeta;
import com.gitee.easyopen.interceptor.ApiInterceptorAdapter;
import com.gitee.pagesmanager.server.common.AdminErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录拦截器，验证用户是否登录
 *
 * @author tanghc
 */
public class LoginInterceptor extends ApiInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu)
            throws Exception {
        Map<String, Claim> jwtData = ApiContext.getJwtData();
        if (jwtData != null) {
            return true;
        } else {
            throw AdminErrors.NO_LOGIN.getException();
        }
    }

    @Override
    public boolean match(ApiMeta apiMeta) {
        String name = apiMeta.getName();
        if (name.startsWith("nologin.")) { // 以‘nologin.’开头的接口不拦截
            return false;
        } else {
            return true;
        }
    }

}
