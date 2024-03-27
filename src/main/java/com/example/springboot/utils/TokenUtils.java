package com.example.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author : siwei.fan
 * @date : 2024/3/3 19:22
 * @modyified By :
 */

@Component // 将该类添加到 spring 容器中
public class TokenUtils {
	
	private static UserMapper staticUserMapper;
	
	@Resource // 获取 spring 容器中的对象
	UserMapper userMapper;
	
	@PostConstruct
	public void setUserService() {
		staticUserMapper = userMapper;
	}
	
	/*
	* 生成 token
	* @return token
	*/
	public static String createToken(String userId, String sign) {
		return JWT.create().withAudience(userId) // 将 userId 保存到 token 中，作为载荷
				.withExpiresAt(DateUtil.offsetHour(new Date(), 24)) // 2小时后 token 过期
				.sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
	}
	
	/*
	* 获取当前登录的用户信息
	* @retrun user 对象
	*/
	public static User getCurrentUser() {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String token = request.getHeader("token");
			if(StrUtil.isNotBlank(token)) {
				String userId = JWT.decode(token).getAudience().get(0);
				return staticUserMapper.selectById(Integer.valueOf(userId));
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
}