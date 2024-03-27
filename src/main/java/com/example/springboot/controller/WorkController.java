package com.example.springboot.controller;

import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Work;
import com.example.springboot.service.UserService;
import com.example.springboot.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
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
		
		// 将时间字符串转换为 Date 类型的对象
		Date uploadDate = null;
		// System.out.println("work:" + work);
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
		return Result.success();
	}
	
	/**
	 * 获取全部作品信息
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/selectAllWork")
	public Result selectAllWork() {
		try {
			List<Work> workList = workService.selectAllWork();
			return Result.success(workList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取所有用户数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
	
	/**
	 * 通过用户ID获取image作品
	 */
	@AuthAccess // 这个注释只在测试接口时使用
	@GetMapping("/getWorkByUseridAndFiletype")
	public Result selectByUsername(@RequestParam String id, @RequestParam String fileType) {
		Integer userId = Integer.valueOf(id);
		try {
			List<Work> workList = workService.selectByUseridAndFiletype(userId, fileType);
			return Result.success(workList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作品数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误11");
			}
		}
	}
}
