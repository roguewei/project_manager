package com.winston.interceptor;

import com.alibaba.fastjson.JSON;
import com.winston.jwt.TokenService;
import com.winston.properties.SecurityProperties;
import com.winston.redis.RedisService;
import com.winston.redis.key.PermissionKey;
import com.winston.result.CodeMsg;
import com.winston.result.Result;
import com.winston.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @ClassName Logininterceptor
 * @Author: Winston
 * @Description: 拦截未登录的请求
 * @Date:Create：in 2019/10/9 16:13
 * @Version：
 */
@Component
public class Logininterceptor implements HandlerInterceptor {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean authenticationInfo = doGetAuthenticationInfo(request, response, handler);

//        boolean authorizationInfo = doGetAuthorizationInfo(request, response);

        return authenticationInfo;
    }

    private boolean doGetAuthenticationInfo(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        // 获取请求头带回来的token
        String token = request.getHeader(securityProperties.getJwt().getHeader());
        if(StringUtils.isBlank(token)){
//            response.sendRedirect(request.getContextPath()+"/login");
            outPut(response, CodeMsg.LOGIN_TIME_PASS);
        }
        // 校验token
        if(!tokenService.authToken(request)){
//            response.sendRedirect(request.getContextPath()+"/login");
            outPut(response, CodeMsg.LOGIN_TIME_PASS);
            return false;
        }else{
            return true;
        }
    }

    private boolean doGetAuthorizationInfo(HttpServletRequest request, HttpServletResponse response){
        String requestUrl = HttpUtil.getRequestUrl(request);

        String redisPer = redisService.get(PermissionKey.PERMISSION_USER_KEY, request.getSession().getId(), String.class);
        if(redisPer.contains(requestUrl)){
            return true;
        }else{
            outPut(response, CodeMsg.HAS_NOT_PERMISSION);
            return false;
        }
    }

    private void outPut(ServletResponse response, CodeMsg codeMsg) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println(JSON.toJSONString(Result.error(codeMsg)));
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
