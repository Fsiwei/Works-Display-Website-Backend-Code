package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : siwei.fan
 * @date : 2024/2/25 13:44
 * @modyified By :
 */

// @CrossOrigin
@RestController
@RequestMapping("/api/user")
public class WebController {
	
	@Resource
	UserService userService;
	
	@AuthAccess
	@GetMapping("/") // 检查接口是否正常运行
	public Result hello(){
		return Result.success("success");
	}
	
	@AuthAccess
	@PostMapping("/login")
	public Result login(@RequestBody User user){
		if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
			return Result.error("数据输入不合法");
		}
		user = userService.login(user);
		return Result.success(user);
	}
	
	@AuthAccess
	@PostMapping("/register")
	public Result register(@RequestBody User user){
		if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
			return Result.error("数据输入不合法");
		}
		user = userService.register(user);
		return Result.success(user);
	}
	
}
