package com.example.springboot.service;

import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.common.Page;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : siwei.fan
 * @date : 2024/2/25 13:41
 * @modyified By :
 */

@RestController
@Service // 将 UserService 注入 spring 容器，让容器来管理
public class UserService {
	@Autowired // 注入 Mapper
	UserMapper userMapper;
	
	public void insertUser(User user) {
		userMapper.insert(user);
	}
	
	public void updateUser(User user) {
		userMapper.update(user);
	}
	
	public void updateUserIsActive(Integer userId, Boolean isActive) {
		userMapper.updateUserIsActive(userId, isActive);
	}
	
	public User getUserInfo(Integer userId) {
		return userMapper.selectUserInfo(userId);
	}
	
	public void deleteUser(Integer id) {
		userMapper.delete(id);
	}
	
	public void batchDeleteUser(List<Integer> ids) {
		for (Integer id : ids) {
			userMapper.delete(id);
		}
	}
	
	public List<User> selectAllUser() {
		return userMapper.selectAllUser();
	}
	
	// public List<User> selectByUsername(String username) {
	// 	return userMapper.selectByUsername(username);
	// }
	
	public List<User> selectByUsernameAndName(String username, String name) {
		return userMapper.selectByUsernameAndName(username, name);
	}
	
	public List<User> selectByUsernameAndNameLike(String username, String name) {
		return userMapper.selectByUsernameAndNameLike(username, name);
	}
	
	public Page<User> selectAllUserPage(Integer pageNum, Integer pageSize) {
		Integer skipNum = (pageNum - 1) * pageSize;
		Page<User> page = new Page<>();
		List<User> userList = userMapper.selectAllUserPage(skipNum, pageSize);
		Integer total = userMapper.selectCountPage();
		page.setList(userList);
		page.setTotal(total);
		return page;
	}
	
	// public Page<User> selectByUsernameAndNamePage(Integer pageNum, Integer pageSize, String username, String name) {
	// 	Integer skipNum = (pageNum - 1) * pageSize;
	// 	Page<User> page = new Page<>();
	// 	List<User> userList = userMapper.selectByUsernameAndNamePage(skipNum, pageSize, username, name);
	// 	int total = userMapper.selectCountPage(username, name);
	// 	page.setList(userList);
	// 	page.setTotal(total);
	// 	return page;
	// }
	
	// 验证数据是否合法
	public User login(User user) {
		// 根据用户名查询数据库的用户信息
		User dbUser = userMapper.selectByUsername(user.getUsername());
		// List<User> dbuser = userMapper.selectByUsername(user.getUsername());
		if (dbUser == null) {
			// 抛出自定义异常
			throw new ServiceException("用户账号不可用");
		}
		if (!user.getPassword().equals(dbUser.getPassword())) {
			throw new ServiceException("用户名或密码错误");
		}
		// 生成 token
		String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());// userId 是要存到 token中，password 是用来验证 token
		dbUser.setToken(token);
		return dbUser;
	}
	
	public User register(User user) {
		// 注册前前判断该用户名是否已经存在
		User dbUser = userMapper.selectByUsername(user.getUsername());
		if (dbUser != null) {
			// 抛出自定义异常
			throw new ServiceException("用户名已存在");
		}
		userMapper.insert(user);
		return user;
	}
}