package com.example.springboot.controller;

import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Message;
import com.example.springboot.entity.Violation;
import com.example.springboot.entity.Work;
import com.example.springboot.service.MessageService;
import com.example.springboot.service.ViolationService;
import com.example.springboot.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	ViolationService violationService;
	
	/**
	 * 新增作品信息
	 */
	@PostMapping("/addWork")
	public Result addWork(@RequestBody Map<String, Object> requestData) {
		Integer userId = (Integer) requestData.get("userId");
		String workName = (String) requestData.get("workName");
		String workDescribe = (String) requestData.get("workDescribe");
		String workType = (String) requestData.get("workType");
		String uploadDateString = (String) requestData.get("uploadDate");
		String workUrls = (String) requestData.get("workUrls");
		String fileType = (String) requestData.get("fileType");
		String status = (String) requestData.get("status");
		
		// 将时间字符串转换为 Date 类型的对象
		Date uploadDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			uploadDate = dateFormat.parse(uploadDateString);
			// 创建 Work 对象并设置属性
			Work work = Work.builder()
					.userId(userId)
					.workName(workName)
					.workDescribe(workDescribe)
					.workType(workType)
					.uploadDate(uploadDate)
					.workUrls(workUrls)
					.fileType(fileType)
					.status(status)
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
	public Result selectByUserId(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer userId, @RequestParam String fileType) {
		try {
			Page<Work> workList = workService.selectByUserIdAndFileType(pageNum, pageSize, userId, fileType);
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
	 * 通过关键词和文件类型获取作品
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getWorkByKeywordAndFileType")
	public Result selectByKeywordAndFileType(@RequestParam String keyword, @RequestParam String fileType) {
		try {
			List<Work> workList = workService.selectByKeywordAndFileType(keyword, fileType);
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
	public Result getWorkByWorkId(@RequestParam Integer workId) {
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
	public Result selectWorkByKeywordPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String keyword, @RequestParam String fileType) {
		System.out.println("fileType" +fileType);
		try {
			Page<Work> page = workService.selectWorkByKeywordPage(pageNum, pageSize, keyword, fileType);
			return Result.success(page);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作品数据错误");
			} else {
				return Result.error(e.getMessage());
				// return Result.error("系统错误");
			}
		}
	}
	
	
	@AuthAccess
	@GetMapping("/selectAllWorkPage")
	public Result selectAllWorkPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
		try {
			Page<Work> page = workService.selectAllWorkPage(pageNum, pageSize);
			return Result.success(page);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作品数据错误");
			} else {
				return Result.error(e.getMessage());
				// return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 管理员审核后更改作品的状态
	 * param: workId
	 * param: status
	 */
	@PostMapping("/submitReviewWork")
	public Result updateWorkStatus(@RequestBody Map<String, Object> requestData) {
		Integer workId = (Integer) requestData.get("workId");
		String status = (String) requestData.get("status");
		Integer userId = (Integer) requestData.get("userId");
		String workName = (String) requestData.get("workName");
		String content = (String) requestData.get("content");
		String reviewDateString = (String) requestData.get("reviewDate");
		Date reviewDate = null;
		Date violationDate = null;
		System.out.println("数据" + workId + status + userId + workName + content + reviewDateString);
		try {
			workService.updateWorkStatus(workId, status);
			// 添加审核记录，发送给用户的审核消息
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			reviewDate = dateFormat.parse(reviewDateString);
			Message message = Message.builder()
					.userId(userId)
					.content(content)
					.reviewDate(reviewDate)
					.build();
			messageService.insertMessage(message, workId, status);
			// 判断审核后作品的状态是否是驳回，是则添加该用户的违规记录
			if (Objects.equals(status, "驳回")) {
				String description = "具体违规内容：" + content + "。";
				violationDate = dateFormat.parse(reviewDateString);
				Violation violation = Violation.builder()
						.userId(userId)
						.workName(workName)
						.description(description)
						.violationDate(violationDate)
						.build();
				violationService.insertViolation(violation);
			}
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("更改作品状态失败");
			} else {
				return Result.error("系统错误");
			}
		}
		return Result.success();
	}
	
	@GetMapping("/getWorksMostLike")
	public Result getWorksMostLike() {
		try {
			Work work = workService.selectWorksMostLike();
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
	
}
