package com.example.springboot.controller;

import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Comment;
import com.example.springboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : siwei.fan
 * @date : 2024/4/3 22:05
 * @modyified By :
 */

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	/**
	 * 新增评论信息
	 */
	@PostMapping("/releaseComment")
	public Result releaseComment(@RequestBody Map<String, Object> requestData) {
		Integer userId = (Integer) requestData.get("userId");
		String username = (String) requestData.get("username");
		String avatar = (String) requestData.get("avatar");
		Integer authorId = (Integer) requestData.get("authorId");
		Integer workId = (Integer) requestData.get("workId");
		String workName = (String) requestData.get("workName");
		String content = (String) requestData.get("content");
		String commentDateString = (String) requestData.get("commentDate");
		
		// 将时间字符串转换为 Date 类型的对象
		Date commentDate = null;
		// System.out.println("comment:" + comment);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			commentDate = dateFormat.parse(commentDateString);
			// 创建 Comment 对象并设置属性
			Comment comment = Comment.builder()
					.userId(userId)
					.username(username)
					.avatar(avatar)
					.authorId(authorId)
					.workId(workId)
					.workName(workName)
					.content(content)
					.commentDate(commentDate)
					.build();
			commentService.insertComment(comment);
		} catch (ParseException e) {
			// 处理解析异常
			e.printStackTrace();
			return Result.error("系统错误!!");
		}
		catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("插入评论错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
		return Result.success(true);
	}
	
	/**
	 * 通过作品ID获取所有评论
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getCommentByWorkId")
	public Result getCommentByWorkId(@RequestParam String id) {
		Integer workId = Integer.valueOf(id);
		try {
			List<Comment> commentList = commentService.getCommentByWorkId(workId);
			Map<String, Object> commentData = new HashMap<>();
			commentData.put("commentList", commentList);
			commentData.put("count", commentList.size());
			return Result.success(commentData);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作品所有评论数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 通过作者ID获取作品评论，作为消息发送过来
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getCommentByAuthorId")
	public Result getCommentByAuthorId(@RequestParam String id) {
		Integer authorId = Integer.valueOf(id);
		try {
			List<Comment> commentList = commentService.getCommentByAuthorId(authorId);
			return Result.success(commentList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作者作品评论数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 通过用户ID获取对作品评论，作为评论发送过来
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getCommentByUserId")
	public Result getCommentByUserId(@RequestParam String id) {
		Integer userId = Integer.valueOf(id);
		try {
			List<Comment> commentList = commentService.getCommentByUserId(userId);
			return Result.success(commentList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取用户评论数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
}
