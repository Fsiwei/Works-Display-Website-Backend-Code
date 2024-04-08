package com.example.springboot.controller;

import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : siwei.fan
 * @date : 2024/2/25 13:43
 * @modyified By :
 */

// @CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/**
	 * 新增用户信息
	 */
	@PostMapping("/addUser")
	public Result addUser(@RequestBody User user) {
		try {
			userService.insertUser(user);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("插入数据错误");
			} else {
				return Result.error("系统错误");
			}
		}
		return Result.success();
	}
	
	/**
	 * 修改用户信息
	 */
	@PutMapping("/updateUser")
	public Result updateUser(@RequestBody User user) {
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("修改数据错误");
			} else {
				return Result.error("系统错误");
			}
		}
		return Result.success();
	}
	
	/**
	 * 删除单个用户
	 */
	@DeleteMapping("/deleteUser/{id}")
	public Result deleteUser(@PathVariable Integer id) {
		try {
			userService.deleteUser(id);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("删除数据错误");
			} else {
				return Result.error("系统错误");
			}
		}
		return Result.success();
	}
	
	/**
	 * 批量删除用户
	 */
	@DeleteMapping("/deleteUser/batch")
	public Result batchDeleteUser(@RequestBody List<Integer> ids) {
		try {
			userService.batchDeleteUser(ids);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("批量删除数据错误");
			} else {
				return Result.error("系统错误");
			}
		}
		return Result.success();
	}
	
	/**
	 * 获取全部用户信息
	 */
	@GetMapping("/selectAllUser")
	public Result selectAllUser() {
		try {
			List<User> userList = userService.selectAllUser();
			return Result.success(userList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取所有用户数据错误");
			} else {
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 通过用户ID查询用户信息
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/selectByUserId")
	public Result selectByUserId(@RequestParam String uid) {
		Integer userId = Integer.valueOf(uid);
		try {
			User user = userService.selectByUserId(userId);
			return Result.success(user);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取用户数据错误");
			} else {
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 修改用户信息
	 */
	@PutMapping("/updateUserInfo")
	public Result updateUserInfo(@RequestBody Map<String, Object> requestData) {
		Integer userId = (Integer) requestData.get("userId");
		String username = (String) requestData.get("username");
		String name = (String) requestData.get("name");
		String phone = (String) requestData.get("phone");
		String email = (String) requestData.get("email");
		String address = (String) requestData.get("address");
		String avatar = (String) requestData.get("avatar");
		
		try {
			// 创建 Work 对象并设置属性
			User user = User.builder()
					.id(userId)
					.username(username)
					.name(name)
					.phone(phone)
					.email(email)
					.address(address)
					.avatar(avatar)
					.build();
			userService.updateUser(user);
		} catch (ParseException e) {
			// 处理解析异常
			e.printStackTrace();
			return Result.error("系统错误!!");
		}
		catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("用户数据更新失败");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
		return Result.success();
	}
	
	/**
	 * 根据用户ID修改用户是否可用
	 */
	@PutMapping("/updateUserIsActive")
	public Result updateUserIsActive(@RequestBody Map<String, Object> requestData) {
		Integer userId = (Integer) requestData.get("userId");
		Integer isActive = (Integer) requestData.get("isActive");
		try {
			userService.updateUserIsActive(userId, isActive);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("修改用户是否可用失败");
			} else {
				return Result.error("系统错误");
			}
		}
		return Result.success();
	}
	
	/**
	 * 多条件分页模糊查询
	 */
	@GetMapping("/selectAllUserPage")
	public Result selectAllUserPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
		try {
			Page<User> page = userService.selectAllUserPage(pageNum, pageSize);
			return Result.success(page);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取用户数据错误");
			} else {
				return Result.error(e.getMessage());
				// return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 多条件查询
	 * 通过用户名和姓名查询用户信息
	 */
	@GetMapping("/selectByUsernameAndName")
	public Result selectByUsernameAndName(@RequestParam String username, @RequestParam String name) {
		try {
			List<User> userList = userService.selectByUsernameAndName(username, name);
			return Result.success(userList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取用户数据错误");
			} else {
				return Result.error(e.getMessage());
				// return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 多条件模糊查询
	 * 通过用户名和姓名模糊查询用户信息
	 */
	@GetMapping("/selectByUsernameAndNameLike")
	public Result selectByUsernameAndNameLike(@RequestParam String username, @RequestParam String name) {
		try {
			List<User> userList = userService.selectByUsernameAndNameLike(username, name);
			return Result.success(userList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取用户数据错误");
			} else {
				return Result.error(e.getMessage());
				// return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 多条件分页模糊查询
	 * 通过用户名和姓名分页模糊查询用户信息
	 */
	// @GetMapping("/selectByUsernameAndNamePage")
	// public Result selectByUsernameAndNamePage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username, @RequestParam String name) {
	// 	try {
	// 		Page<User> page = userService.selectByUsernameAndNamePage(pageNum, pageSize, username, name);
	// 		return Result.success(page);
	// 	} catch (Exception e) {
	// 		if (e instanceof DuplicateKeyException) {
	// 			return Result.error("获取用户数据错误");
	// 		} else {
	// 			return Result.error(e.getMessage());
	// 			// return Result.error("系统错误");
	// 		}
	// 	}
	// }
}
