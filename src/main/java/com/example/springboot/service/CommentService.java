package com.example.springboot.service;

import com.example.springboot.entity.Comment;
import com.example.springboot.mapper.CommentMapper;
import com.example.springboot.mapper.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/4/3 22:06
 * @modyified By :
 */

@RestController
@Service
public class CommentService {
	
	@Autowired // 注入 Mapper
	CommentMapper commentMapper;
	
	@Autowired // 注入 Mapper
	WorkMapper workMapper;
	
	//将用户ID、用户名、头像、作者ID、作品ID、作品名称、评论内容、评论时间写入数据库
	public void insertComment(Comment comment) {
		commentMapper.insert(comment);
		// 插入评论后更新对应作品的评论数
		Integer workId = comment.getWorkId();
		Integer commentCount = commentMapper.getCommentCount(workId);
		System.out.println("commentCount:" + commentCount);
		workMapper.updateCommentCount(workId, commentCount);
	}
	
	// 通过作品ID获取所有评论
	public List<Comment> getCommentByWorkId(Integer workId) {
		return commentMapper.selectCommentByWorkId(workId);
	}
	
	// 通过作者ID获取所有评论
	public List<Comment> getCommentByAuthorId(Integer authorId) {
		return commentMapper.selectCommentByAuthorId(authorId);
	}
	
	// 通过用户ID获取对作品的所有评论
	public List<Comment> getCommentByUserId(Integer userId) {
		return commentMapper.selectCommentByUserId(userId);
	}
}
