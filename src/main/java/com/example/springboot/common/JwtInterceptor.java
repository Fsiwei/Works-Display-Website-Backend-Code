package com.example.springboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : siwei.fan
 * @date : 2024/3/3 16:18
 * @modyified By :
 */


public class JwtInterceptor implements HandlerInterceptor {
 
    @Resource
    private UserMapper userMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        
        String token = request.getHeader("token"); // 从前端传来的请求中获取首部中的 token
        if(StrUtil.isBlank(token)) {
            token = request.getParameter("token"); // 请求首部没有，就从 url 中的请求参数中获取
        }
        
        // 获取请求方法上的 AuthAccess 自定义注解，该注解是让鉴权放行特定请求的
        if(handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if(annotation != null) {
                return true; // 当判断有定义该注解，就不用执行后面鉴权的代码
            }
        }
        
        // 执行认证
        if(StrUtil.isBlank(token)) { // 上传文件大小超出限制后，会报这里的错
            throw new ServiceException("401", "请登录！");
        }
        
        // 获取 token 中的 userId
        String userId;
        try{
            userId = JWT.decode(token).getAudience().get(0); // JWT.decode(token) 解码 jwt token; getAudience().get(0) 获取 Audience 仓库中的 第一个
        } catch (JWTDecodeException j) {
            throw new ServiceException("401", "请登录!!");
        }
        
        // 根据 token 中的 userId 查询数据库
        User user = userMapper.selectById(Integer.valueOf(userId));
        if(user == null) {
            throw new ServiceException("401", "请登录!!!");
        }
        
        // 通过用户密码加密后生成验证器 jwtVerifier
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build(); // Algorithm.HMAC256(user.getPassword()) 将密码加密
        try {
            jwtVerifier.verify(token); // 通过验证器验证 token
        } catch (JWTVerificationException e) {
            throw new ServiceException("401", "请登录!!!!");
        }
        
        return true;
    }
    
}
