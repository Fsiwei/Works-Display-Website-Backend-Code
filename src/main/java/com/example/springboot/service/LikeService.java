package com.example.springboot.service;

import com.example.springboot.entity.Like;
import com.example.springboot.mapper.LikeMapper;
import com.example.springboot.mapper.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/4/3 22:22
 * @modyified By :
 */

@RestController
@Service
public class LikeService {
	
	@Autowired // 注入 Mapper
	LikeMapper likeMapper;
	
	@Autowired // 注入 Mapper
	WorkMapper workMapper;
	
	//将用户ID、用户名、头像、作者ID、作品ID、作品名称、点赞时间写入数据库
	public void insertLike(Like like) {
		likeMapper.insert(like);
		// 插入点赞后更新对应作品的点赞数
		Integer workId = like.getWorkId();
		Integer likeCount = likeMapper.getLikeCount(workId);
		System.out.println("点赞likeCount:" + likeCount);
		workMapper.updateLikeCount(workId, likeCount);
	}
	
	// 通过作品ID获取所有点赞
	public Integer countLikeByUserIdAndWorkId(Integer workId, Integer userId) {
		return likeMapper.countLikeByUserIdAndWorkId(workId, userId);
	}
	
	// 通过作品ID获取所有点赞
	public List<Like> getLikeByWorkId(Integer workId) {
		return likeMapper.selectLikeByWorkId(workId);
	}
	
	// 通过作者ID获取所有点赞
	public List<Like> getLikeByAuthorId(Integer authorId) {
		return likeMapper.selectLikeByAuthorId(authorId);
	}
	
	// 通过用户ID获取对作品的所有点赞
	public List<Like> getLikeByUserId(Integer userId) {
		return likeMapper.selectLikeByUserId(userId);
	}
	
	public void updateLikeByUserIdAndWorkId(Integer workId, Integer userId, Integer isLike) {
		likeMapper.updateLikeByUserIdAndWorkId(workId, userId, isLike);
		Integer likeCount = likeMapper.getLikeCount(workId);
		System.out.println("取消后likeCount:" + likeCount);
		workMapper.updateLikeCount(workId, likeCount);
	}
	
	public Boolean getLikeByUserIdAndWorkId(Integer workId, Integer userId) {
		// boolean flag = likeMapper.selectLikeByUserIdAndWorkId(userId, workId);
		// if (flag) {
		// 	return true;
		// } else {
		// 	return false;
		// }
		return likeMapper.selectLikeByUserIdAndWorkId(workId, userId);
	}
}
