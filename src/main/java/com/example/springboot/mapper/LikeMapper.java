package com.example.springboot.mapper;

import com.example.springboot.entity.Like;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/4/3 22:23
 * @modyified By :
 */

@Mapper
public interface LikeMapper {
	
	// @Insert("insert into `like` (userId, username, avatar, authorId, workId, workName, likeDate, isLike)" +
	// 		"values (#{userId}, #{username}, #{avatar}, #{authorId}, #{workId}, #{workName}, #{likeDate}, 1)" +
	// 		"on duplicate key update isLike = 1")
	// void insert(Like like);
	
	@Insert("insert into `like` (userId, authorId, workId, likeDate, isLike) " +
			"values (#{userId}, #{authorId}, #{workId}, #{likeDate}, 1)")
	void insert(Like like);
	
	@Select("select COUNT(*) from `like` inner join `user` on like.userId = user.id inner join `works` on like.workId = works.workId where userId = #{userId} and workId = #{workId}")
	Integer countLikeByUserIdAndWorkId(@Param("workId") Integer workId, @Param("userId") Integer userId);
	
	@Select("select count(*) as likeCount, workId from `like` inner join `user` on like.userId = user.id inner join `works` on like.workId = works.workId where workId = #{workId} and isLike = 1 group by workId")
	Integer getLikeCount(@Param("workId") Integer workId);
	
	@Select("select *, count(*) from `like` inner join `user` on like.userId = user.id inner join `works` on like.workId = works.workId where workId = #{workId} order by likeId desc")
	List<Like> selectLikeByWorkId(@Param("workId") Integer workId);
	
	@Select("select * from `like` inner join `user` on like.userId = user.id inner join `works` on like.workId = works.workId where authorId = #{authorId} order by likeId desc")
	List<Like> selectLikeByAuthorId(@Param("authorId") Integer authorId);
	
	@Select("select * from `like` inner join `user` on like.userId = user.id inner join `works` on like.workId = works.workId where userId = #{userId} and isLike = 1 order by likeId desc")
	List<Like> selectLikeByUserId(@Param("userId") Integer userId);
	
	@Update("update `like` set isLike = #{isLike} where userId = #{userId} and workId = #{workId}")
	void updateLikeByUserIdAndWorkId(@Param("workId") Integer workId, @Param("userId") Integer userId, @Param("isLike") Integer isLike);
	
	@Select("select isLike from `like` inner join `user` on like.userId = user.id inner join `works` on like.workId = works.workId where userId = #{userId} and workId = #{workId}")
	Boolean selectLikeByUserIdAndWorkId(@Param("workId") Integer workId, @Param("userId") Integer userId);
}
