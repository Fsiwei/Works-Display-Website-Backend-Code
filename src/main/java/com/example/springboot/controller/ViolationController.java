package com.example.springboot.controller;

import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.entity.Violation;
import com.example.springboot.service.UserService;
import com.example.springboot.service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/4/24 20:45
 * @modyified By :
 */

@RestController
@RequestMapping("/api/violation")
public class ViolationController {
	
	@Autowired
	ViolationService violationService;
	
	/**
	 * 通过用户ID查询用户违规记录
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getUserViolationRecord")
	public Result getUserViolationRecord(@RequestParam Integer userId) {
		try {
			List<Violation> recordList = violationService.getUserViolationRecord(userId);
			return Result.success(recordList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取用户违规数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
}
