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
	
	@Insert("insert into `work` (userId, workName, workDescribe, workType, uploadDate, workUrls, fileType, workLike, workCollection, comment)" +
			"values (#{userId}, #{workName}, #{workDescribe}, #{workType}, #{uploadDate}, #{workUrls}, #{fileType}, #{workLike}, #{workCollection}, #{comment})")
	void insert(Work work);
	
	@Select("select * from `work` inner join `user` on work.userId = user.id where fileType = #{fileType} order by workId desc")
	List<Work> selectWorkByFileType(@Param("fileType") String fileType);
	
	@Select("select * from `work` inner join `user` on work.userId = user.id where userId = #{userId} and fileType = #{fileType} order by workId desc")
	List<Work> selectByUserIdAndFileType(@Param("userId") Integer userId, @Param("fileType") String fileType);
	
	@Select("select * from `work` inner join `user` on work.userId = user.id where workId = #{workId}")
	Work selectByWorkId(@Param("workId") Integer workId);
	
	@Select("select * from `work` inner join `user` on work.userId = user.id where workName like concat('%', #{keyword}, '%') or workDescribe like concat('%', #{keyword}, '%') and fileType = #{fileType} order by workId desc")
	List<Work> selectByKeywordImage(@Param("keyword") String keyword, @Param("fileType") String fileType);
	
	@Select("select * from `work` inner join `user` on work.userId = user.id where workName like concat('%', #{keyword}, '%') or workDescribe like concat('%', #{keyword}, '%') and fileType = #{fileType} order by workId desc")
	List<Work> selectByKeywordVideo(@Param("keyword") String keyword, @Param("fileType") String fileType);
	
	@Update("update `work` set comment = #{commentCount} where workId = #{workId}")
	void updateCommentCount(@Param("workId") Integer workId, @Param("commentCount") Integer commentCount);
	
	@Update("update `work` set workLike = #{likeCount} where workId = #{workId}")
	void updateLikeCount(@Param("workId") Integer workId, @Param("likeCount") Integer likeCount);
	
	@Select("select * from `work` inner join `user` on work.userId = user.id where workName like concat('%', #{keyword}, '%') and workDescribe like concat('%', #{keyword}, '%') and fileType = #{fileType} order by id desc limit #{skipNum}, #{pageSize}")
	List<Work> selectWorkByKeywordPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize, @Param("keyword") String keyword, @Param("fileType") String fileType);
	
	@Select("select count(id) from `work` where workName like concat('%', #{keyword}, '%') and workDescribe like concat('%', #{keyword}, '%') and fileType =#{fileType} order by id desc")
	int selectCountPage(@Param("keyword") String keyword, @Param("fileType") String fileType);
}
