package com.example.springboot.mapper;

import com.example.springboot.entity.Violation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : siwei.fan
 * @date : 2024/4/17 21:47
 * @modyified By :
 */

@Mapper
public interface ViolationMapper {
	
	@Insert("insert into `violationrecord` (userId, description, violation_date) values (#{userId}, #{description}, #{violationDate})")
	void insert(Violation violation);
}
