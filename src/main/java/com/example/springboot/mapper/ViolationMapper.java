package com.example.springboot.mapper;

import com.example.springboot.entity.Violation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/4/17 21:47
 * @modyified By :
 */

@Mapper
public interface ViolationMapper {
	
	@Insert("insert into `violationrecord` (userId, description, violationDate, workName) values (#{userId}, #{description}, #{violationDate}, #{workName})")
	void insert(Violation violation);
	
	@Select("select * from `violationRecord` where userId = #{userId} order by id")
	List<Violation> selectUserViolationRecord(@Param("userId") Integer userId);
}
