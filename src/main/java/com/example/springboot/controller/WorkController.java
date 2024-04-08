package com.example.springboot.controller;

import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Work;
import com.example.springboot.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : siwei.fan
 * @date : 2024/3/16 15:49
 * @modyified By :
 */

@RestController
@RequestMapping("/api/work")
public class WorkController {
	
	@Autowired
	WorkService workService;
	
	/**
	 * 新增作品信息
	 */
	@PostMapping("/addWork")
	public Result addWork(@RequestBody Map<String, Object> requestData) {
		Integer userId = (Integer) requestData.get("userId");
		String username = (String) requestData.get("username");
		String avatar = (String) requestData.get("avatar");
		String workName = (String) requestData.get("workName");
		String workDescribe = (String) requestData.get("workDescribe");
		String workType = (String) requestData.get("workType");
		String uploadDateString = (String) requestData.get("uploadDate");
		String workUrls = (String) requestData.get("workUrls");
		String fileType = (String) requestData.get("fileType");
		
		// 将时间字符串转换为 Date 类型的对象
		Date uploadDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			uploadDate = dateFormat.parse(uploadDateString);
			// 创建 Work 对象并设置属性
			Work work = Work.builder()
					.userId(userId)
					.username(username)
					.avatar(avatar)
					.workName(workName)
					.workDescribe(workDescribe)
					.workType(workType)
					.uploadDate(uploadDate)
					.workUrls(workUrls)
					.fileType(fileType)
					// 其他属性...
					.build();
			workService.insertWork(work);
		} catch (ParseException e) {
			// 处理解析异常
			e.printStackTrace();
			return Result.error("系统错误!!");
		}
		catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("插入数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
		return Result.success(true);
	}
	
	/**
	 * 获取全部作品信息
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getWorkByFileType")
	public Result selectWorkByFileType(@RequestParam String fileType) {
		try {
			List<Work> workList = workService.selectWorkByFileType(fileType);
			return Result.success(workList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作品数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 通过用户ID获取作品
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getWorkByUserIdAndFileType")
	public Result selectByUserId(@RequestParam String id, @RequestParam String fileType) {
		Integer userId = Integer.valueOf(id);
		try {
			List<Work> workList = workService.selectByUserIdAndFileType(userId, fileType);
			return Result.success(workList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作品数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 通过关键词获取image作品
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getWorkByKeywordImage")
	public Result selectByKeywordImage(@RequestParam String keyword, @RequestParam String fileType) {
		System.out.println("keyword:" + keyword);
		System.out.println("fileType:" + fileType);
		try {
			List<Work> workList = workService.selectByKeywordImage(keyword, fileType);
			System.out.println(workList);
			return Result.success(workList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作品数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 通过关键词获取video作品
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getWorkByKeywordVideo")
	public Result selectByKeywordVideo(@RequestParam String keyword, @RequestParam String fileType) {
		System.out.println("keyword:" + keyword);
		System.out.println("fileType:" + fileType);
		try {
			List<Work> workList = workService.selectByKeywordVideo(keyword, fileType);
			System.out.println(workList);
			return Result.success(workList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作品数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 通过作品ID获取作品信息
	 */
	@AuthAccess
	@GetMapping("/getWorkByWorkId")
	public Result getWorkByWorkId(@RequestParam String id) {
		Integer workId = Integer.valueOf(id);
		try {
			Work work = workService.selectByWorkId(workId);
			return Result.success(work);
		} catch (Exception e) {
			if(e instanceof DuplicateKeyException) {
				return Result.error("获取作品信息失败");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 多条件分页模糊查询
	 * 通过关键词模糊查询作品信息
	 */
	@AuthAccess
	@GetMapping("/selectWorkByKeywordPage")
	public Result selectWorkByKeywordPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, String keyword, String fileType) {
		try {
			Page<Work> page = workService.selectWorkByKeywordPage(pageNum, pageSize, keyword, fileType);
			return Result.success(page);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取用户数据错误");
			} else {
				return Result.error(e.getMessage());
				// return Result.error("系统错误");
			}
		}
	}
	
}
