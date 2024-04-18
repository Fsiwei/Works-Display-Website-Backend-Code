package com.example.springboot.mapper;

import com.example.springboot.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/4/17 0:32
 * @modyified By :
 */

@Mapper
public interface MessageMapper {
	@Insert("insert into `message` (userId, content, date) values (#{userId}, #{content}, #{reviewDate})")
	void insert(Message message);
	
	@Select("select * from `message` inner join `user` on message.userId = user.id where userId = #{authorId} order by commentId desc")
	List<Message> selectMessageByAuthorId(Integer authorId);
}
