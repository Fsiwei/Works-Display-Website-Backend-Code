package com.example.springboot.mapper;

import com.example.springboot.entity.Work;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/3/16 15:56
 * @modyified By :
 */

@Mapper
public interface WorkMapper {
	
	@Insert("insert into `works` (userId, workName, workDescribe, workType, uploadDate, workUrls, fileType, status, workLike, workCollection, comment)" +
			"values (#{userId}, #{workName}, #{workDescribe}, #{workType}, #{uploadDate}, #{workUrls}, #{fileType}, #{status}, #{workLike}, #{workCollection}, #{comment})")
	void insert(Work work);
	
	@Select("select * from `works` inner join `user` on works.userId = user.id where fileType = #{fileType} and status = '已发布' order by workId desc")
	List<Work> selectWorkByFileType(@Param("fileType") String fileType);
	
	@Select("select * from `works` inner join `user` on works.userId = user.id where userId = #{userId} and fileType = #{fileType} order by workId desc limit #{skipNum}, #{pageSize}")
	List<Work> selectByUserIdAndFileType(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize, @Param("userId") Integer userId, @Param("fileType") String fileType);
	
	@Select("select count(workId) from `works` where userId = #{userId} and fileType =#{fileType} order by workId desc")
	int selectCountPageByUserIdAndFileType(@Param("userId") Integer userId, @Param("fileType") String fileType);
	
	@Select("select * from `works` inner join `user` on works.userId = user.id where workId = #{workId}")
	Work selectByWorkId(@Param("workId") Integer workId);
	
	@Select("select * from `works` inner join `user` on works.userId = user.id where workName like concat('%', #{keyword}, '%') or workDescribe like concat('%', #{keyword}, '%') and fileType = #{fileType} and status = '已发布' order by workId desc")
	List<Work> selectByKeywordAndFileType(@Param("keyword") String keyword, @Param("fileType") String fileType);
	
	@Update("update `works` set comment = #{commentCount} where workId = #{workId}")
	void updateCommentCount(@Param("workId") Integer workId, @Param("commentCount") Integer commentCount);
	
	@Update("update `works` set workLike = #{likeCount} where workId = #{workId}")
	void updateLikeCount(@Param("workId") Integer workId, @Param("likeCount") Integer likeCount);
	
	// @Select("select * from `works` inner join `user` on works.userId = user.id where workName like concat('%', #{keyword}, '%') and workDescribe like concat('%', #{keyword}, '%') and fileType = #{fileType} and status = '已发布' order by workId desc limit #{skipNum}, #{pageSize}")
	// List<Work> selectWorkByKeywordPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize, @Param("keyword") String keyword, @Param("fileType") String fileType);
	
	@Select("select * from `works` inner join `user` on works.userId = user.id " +
			"where (workName like CONCAT('%', #{keyword}, '%') or workDescribe like CONCAT('%', #{keyword}, '%')) " +
			"and fileType = #{fileType} and status = '已发布' " +
			"order by workId desc limit #{skipNum}, #{pageSize}")
	List<Work> selectWorkByKeywordPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize, @Param("keyword") String keyword, @Param("fileType") String fileType);
	
	// @Select("select count(workId) from `works` where workName like concat('%', #{keyword}, '%') and workDescribe like concat('%', #{keyword}, '%') and fileType =#{fileType} and status = '已发布' order by workId desc")
	@Select("select count(workId) from `works`" +
			"where (workName like CONCAT('%', #{keyword}, '%') or workDescribe like CONCAT('%', #{keyword}, '%')) " +
			"and fileType = #{fileType} and status = '已发布' " +
			"order by workId desc")
	int selectCountPage(@Param("keyword") String keyword, @Param("fileType") String fileType);
	
	@Select("select * from `works` inner join `user` on works.userId = user.id order by workId desc limit #{skipNum}, #{pageSize}")
	List<Work> selectAllWorkPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize);
	
	@Select("select count(workId) from `works` order by workId desc")
	int selectAllCountPage();
	
	@Update("update `works` set status = #{status} where workId = #{workId}")
	void updateWorkStatus(@Param("workId") Integer workId, @Param("status")  String status);
	
	@Select("select * from `works` where fileType = 'image' order by workLike desc limit 1")
	Work selectWorksMostLike();
}
