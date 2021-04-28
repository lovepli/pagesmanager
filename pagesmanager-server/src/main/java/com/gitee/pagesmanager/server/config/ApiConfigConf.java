package com.gitee.pagesmanager.server.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiInvoker;
import com.gitee.easyopen.ApiParam;
import com.gitee.easyopen.bean.ApiDefinition;
import com.gitee.easyopen.interceptor.ApiInterceptor;
import com.gitee.pagesmanager.server.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tanghc
 */
@Configuration
public class ApiConfigConf {

    @Bean
    public ApiConfig apiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setJsonResultSerializer(obj -> JSON.toJSONString(obj,
                SerializerFeature.WriteDateUseDateFormat
                , SerializerFeature.WriteMapNullValue
                , SerializerFeature.WriteNullListAsEmpty
                , SerializerFeature.WriteNullStringAsEmpty
        ));
        apiConfig.setInvoker(new ApiInvoker() {
            @Override
            protected ApiDefinition getApiDefinition(ApiParam param) {
                ApiDefinition apiDefinition = super.getApiDefinition(param);
                if (param.fatchName().startsWith("nologin.")) {
                    apiDefinition.setIgnoreJWT(true);
                }
                return apiDefinition;
            }
        });
        // 登录拦截器
        apiConfig.setInterceptors(new ApiInterceptor[]{new LoginInterceptor()});
        return apiConfig;
    }
}
