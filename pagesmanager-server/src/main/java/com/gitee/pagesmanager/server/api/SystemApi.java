package com.gitee.pagesmanager.server.api;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.pagesmanager.server.api.param.LoginForm;
import com.gitee.pagesmanager.server.api.result.AdminUserInfoVO;
import com.gitee.pagesmanager.server.common.AdminErrors;
import com.gitee.pagesmanager.server.common.WebContext;
import com.gitee.pagesmanager.server.entity.AdminUserInfo;
import com.gitee.pagesmanager.server.mapper.AdminUserInfoMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tanghc
 */
@ApiService
@ApiDoc("系统接口")
public class SystemApi {


    public static final int STATUS_FORBIDDEN = 2;
    @Autowired
    AdminUserInfoMapper adminUserInfoMapper;

    @Api(name = "nologin.admin.login")
    @ApiDocMethod(description = "用户登录")
    String adminLogin(LoginForm param) {
        String username = param.getUsername();
        String password = param.getPassword();
        password = DigestUtils.md5Hex(password);

        Query query = new Query()
                .eq("username", username)
                .eq("password", password);
        AdminUserInfo user = adminUserInfoMapper.getByQuery(query);

        if (user == null) {
            throw AdminErrors.ERROR_USERNAME_PWD.getException();
        } else {
            if (user.getStatus() == STATUS_FORBIDDEN) {
                throw AdminErrors.USER_FORBIDDEN.getException();
            }
            Map<String, String> jwtData = new HashMap<>();
            jwtData.put("id", String.valueOf(user.getId()));
            jwtData.put("username", username);
            String jwt = ApiContext.createJwt(jwtData);
            return jwt;
        }
    }


    @Api(name = "admin.userinfo.get")
    @ApiDocMethod(description = "获取用户信息")
    AdminUserInfoVO getAdminUserInfo() {
        AdminUserInfo loginUser = WebContext.getInstance().getLoginUser();
        AdminUserInfoVO adminUserInfoVO = new AdminUserInfoVO();
        BeanUtils.copyProperties(loginUser, adminUserInfoVO);
        return adminUserInfoVO;
    }
}
