package com.example.springboot.mapper;

import com.example.springboot.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/4/3 22:07
 * @modyified By :
 */

@Mapper
public interface CommentMapper {
	
	@Insert("insert into `comment` (userId,  authorId, workId, content, commentDate)" +
			"values (#{userId}, #{authorId}, #{workId}, #{content}, #{commentDate})")
	void insert(Comment comment);
	
	@Select("select count(*) as commentCount, comment.workId from `comment` inner join `user` on comment.userId = user.id inner join `works` on comment.workId = works.workId where comment.workId = #{workId} group by comment.workId")
	Integer getCommentCount(@Param("workId") Integer workId);
	
	@Select("select comment.*, user.username, user.avatar from `comment` inner join `user` on comment.userId = user.id inner join `works` on comment.workId = works.workId where comment.workId = #{workId} order by commentId desc")
	List<Comment> selectCommentByWorkId(@Param("workId") Integer workId);
	
	@Select("select * from `comment` inner join `user` on comment.userId = user.id inner join `works` on comment.workId = works.workId where comment.authorId = #{authorId} order by commentId desc")
	List<Comment> selectCommentByAuthorId(@Param("authorId") Integer authorId);
	
	@Select("select * from `comment` inner join `user` on comment.userId = user.id inner join `works` on comment.workId = works.workId where comment.userId = #{userId} order by commentId desc")
	List<Comment> selectCommentByUserId(@Param("userId") Integer userId);
}

