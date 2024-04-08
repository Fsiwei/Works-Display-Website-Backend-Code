package com.example.springboot.controller;

import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Like;
import com.example.springboot.service.LikeService;
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
 * @date : 2024/4/3 22:20
 * @modyified By :
 */

@RestController
@RequestMapping("/api/like")
public class LikeController {
	
	@Autowired
	LikeService likeService;
	
	/**
	 * 新增点赞信息
	 */
	@PostMapping("/releaseLike")
	public Result releaseLike(@RequestBody Map<String, Object> requestData) throws java.text.ParseException {
		Integer userId = (Integer) requestData.get("userId");
		String username = (String) requestData.get("username");
		String avatar = (String) requestData.get("avatar");
		Integer authorId = (Integer) requestData.get("authorId");
		Integer workId = (Integer) requestData.get("workId");
		String workName = (String) requestData.get("workName");
		String likeDateString = (String) requestData.get("likeDate");
		Boolean flag = true;
		// 将时间字符串转换为 Date 类型的对象
		Date likeDate = null;
		// System.out.println("like:" + like);
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// likeDate = dateFormat.parse(likeDateString);
		// // 创建 Like 对象并设置属性
		// Like like = Like.builder()
		// 		.userId(userId)
		// 		.userId(userId)
		// 		.username(username)
		// 		.avatar(avatar)
		// 		.authorId(authorId)
		// 		.workId(workId)
		// 		.workName(workName)
		// 		.likeDate(likeDate)
		// 		.build();
		Integer isLike = 1;
		Integer count = 0;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			likeDate = dateFormat.parse(likeDateString);
			// 创建 Like 对象并设置属性
			Like like = Like.builder()
					.userId(userId)
					.userId(userId)
					.username(username)
					.avatar(avatar)
					.authorId(authorId)
					.workId(workId)
					.workName(workName)
					.likeDate(likeDate)
					.build();
			// flag = hasLikeByUserIdAndWorkId(workId, userId);
			// if (!flag) {
			// 	// 将 isLike 从 false 改为 true
			// 	likeService.updateLikeByUserIdAndWorkId(userId, workId, isLike);
			// }
			count = likeService.countLikeByUserIdAndWorkId(like.getWorkId(), like.getUserId());
			if (count > 0) {
				// 更新
				likeService.updateLikeByUserIdAndWorkId(workId, userId, isLike);
			} else {
				// 插入
				likeService.insertLike(like);
			}
		} catch (ParseException e) {
			// 处理解析异常
			e.printStackTrace();
			return Result.error("系统错误!!");
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("插入点赞错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误，内里" + e.getMessage());
				// 此处没有相应查询记录时会返回空指针异常，其实没有该用户对该作品的点赞记录而已，没想到要如何处理，就先这样处理了
				// likeService.insertLike(like);
				// return Result.success(true);
			}
		}
		return Result.success(true);
	}
	
	/**
	 * 通过作品ID获取所有点赞
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getLiketByWorkId")
	public Result getLikeByWorkId(@RequestParam String id) {
		Integer workId = Integer.valueOf(id);
		try {
			List<Like> likeList = likeService.getLikeByWorkId(workId);
			Map<String, Object> likeData = new HashMap<>();
			likeData.put("likeList", likeList);
			likeData.put("count", likeList.size());
			return Result.success(likeData);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作品所有点赞数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
	
	
	/**
	 * 通过用户ID和作品ID，判断该用户是否点赞
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/isLikeByUserIdAndWorkId")
	public Result isLikeByUserIdAndWorkId(@RequestParam String wid, @RequestParam String uid) {
		Integer workId = Integer.valueOf(wid);
		Integer userId = Integer.valueOf(uid);
		boolean flag = false;
		Integer count = 0;
		try {
			count = likeService.countLikeByUserIdAndWorkId(workId, userId);
			System.out.println(count);
			if (count > 0) {
				flag = likeService.getLikeByUserIdAndWorkId(workId, userId);
			}
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				// 如果是主键重复异常，表示数据库中已经存在相同记录，但不影响查询结果为空的情况
				System.out.println(e.getMessage());
				return Result.error("获取是否点赞失败");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误，内里11" + e.getMessage());
				// 此处没有相应查询记录时会返回空指针异常，其实没有该用户对该作品的点赞记录而已，没想到要如何处理，就先这样处理了
				// return Result.success(flag);
			}
		}
		return Result.success(flag);
	}
	
	/**
	 * 通过用户ID和作品ID处理取消点赞
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/cancelLikeByUserIdAndWorkId")
	public Result cancelLikeByUserIdAndWorkId(@RequestParam String wid, @RequestParam String uid) {
		Integer workId = Integer.valueOf(wid);
		Integer userId = Integer.valueOf(uid);
		Integer isLike = 0;
		try {
			likeService.updateLikeByUserIdAndWorkId(workId, userId, isLike);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("取消点赞失败");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
		return Result.success(false);
	}
	
	/**
	 * 通过作者ID获取其作品点赞，作为消息发送过来
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getLikeByAuthorId")
	public Result getLikeByAuthorId(@RequestParam String id) {
		Integer authorId = Integer.valueOf(id);
		try {
			List<Like> likeList = likeService.getLikeByAuthorId(authorId);
			return Result.success(likeList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作者作品点赞数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 通过用户ID获取对作品点赞，作为点赞发送过来
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getLikeByUserId")
	public Result getLikeByUserId(@RequestParam String id) {
		Integer userId = Integer.valueOf(id);
		try {
			List<Like> likeList = likeService.getLikeByUserId(userId);
			return Result.success(likeList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取用户点赞数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
}
