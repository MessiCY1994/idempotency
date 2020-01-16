package com.messiyang.idempotency.interceptor;


import com.messiyang.idempotency.annotation.AccessLimit;
import com.messiyang.idempotency.common.Constant;
import com.messiyang.idempotency.common.ResponseCode;
import com.messiyang.idempotency.exception.ServiceException;
import com.messiyang.idempotency.service.TokenService;
import com.messiyang.idempotency.util.IpUtil;
import com.messiyang.idempotency.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 接口防刷限流拦截器
 */
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        AccessLimit annotation = method.getAnnotation(AccessLimit.class);
        if (annotation != null) {
            check(annotation, request);
        }

        return true;
    }


    private void check(AccessLimit annotation, HttpServletRequest request) {
        int maxCount = annotation.maxCount();
        int seconds = annotation.seconds();

        StringBuilder sb = new StringBuilder();
        sb.append(Constant.Redis.ACCESS_LIMIT_PREFIX).append(IpUtil.getIpAddress(request)).append(request.getRequestURI());
        String key = sb.toString();
        tokenService.checkAccessLimit(key,maxCount,seconds);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
