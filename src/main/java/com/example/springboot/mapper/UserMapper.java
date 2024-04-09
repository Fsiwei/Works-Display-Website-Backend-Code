package com.example.springboot.mapper;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/2/25 13:49
 * @modyified By :
 */

@Mapper
public interface UserMapper {
	
	// 将数据插入数据库
	@Insert("insert into `user` (username, password, name, phone, email, address, avatar)" +
			"values (#{username}, #{password}, #{name}, #{phone}, #{email}, #{address}, #{avatar})")
	void insert(User user);
	
	// 修改数据库数据
	@Update("update `user` set username = #{username}, gender = #{gender}, name = #{name}, phone = #{phone}, " +
			"email = #{email}, address = #{address}, avatar = #{avatar} where id = #{id}")
	void update(User user);
	
	@Select("select * from `user` where id = #{userId} order by id desc")
	User selectByUserId(@Param("userId") Integer userId);
	
	@Update("update `user` set isActive = #{isActive} where id = #{userId}")
	void updateUserIsActive(@Param("userId") Integer userId, @Param("isActive") Integer isActive);
	
	@Select("select * from `user` order by id asc limit #{skipNum}, #{pageSize}")
	List<User> selectAllUserPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize);
	
	@Select("select count(id) from `user` order by id desc")
	int selectCountPage();
	
	@Delete("delete from `user` where id = #{id}")
	void delete(Integer id);
	
	@Select("select * from `user` order by id desc")
	List<User> selectAllUser();
	
	@Select("select * from `user` where id = #{userId} order by id desc")
	User selectById(int userId);
	
	@Select("select * from `user` where username = #{username} order by id desc")
	User selectByUsername(String username);
	
	@Select("select * from `user` where username = #{username} and name = #{name} order by id desc")
	List<User> selectByUsernameAndName(@Param("username") String username, @Param("name") String name);
	
	@Select("select * from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc")
	// List<User> selectByUsernameAndNameLike(@Param("username") String username, @Param("name") String name);
	List<User> selectByUsernameAndNameLike(@Param("username") String username, @Param("name") String name);
	
	// @Select("select * from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc limit #{skipNum}, #{pageSize}")
	// List<User> selectByUsernameAndNamePage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize, @Param("username") String username, @Param("name") String name);
	//
	// @Select("select count(id) from `user` where username like concat('%', #{username}, '%') and name like concat('%', #{name}, '%') order by id desc")
	// Integer selectCountPage(@Param("username") String username, @Param("name") String name);
	
}
