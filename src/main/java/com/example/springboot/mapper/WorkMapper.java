package com.example.springboot.mapper;

import com.example.springboot.entity.Work;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
	
	@Select("select * from `work` order by workId desc")
	List<Work> selectAllWork();
	
	@Select("select * from `work` where userId = #{userId} and fileType = #{fileType} order by workId desc")
	List<Work> selectByUseridAndFiletype(@Param("userId") Integer userId, @Param("fileType") String fileType);
}
