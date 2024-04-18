package com.example.springboot.service;

import com.example.springboot.common.Page;
import com.example.springboot.entity.Work;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.mapper.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/3/16 15:56
 * @modyified By :
 */

@RestController
@Service
public class WorkService {
	
	@Autowired // 注入 Mapper
	WorkMapper workMapper;
	
	/*
	 * 添加作品
	 * param: work
	 */
	public void insertWork(Work work) {
		workMapper.insert(work);
	}
	
	/*
	 * 根据文件类型获取数据
	 * param: fileType
	 */
	public List<Work> selectWorkByFileType(String fileType) {
		return workMapper.selectWorkByFileType(fileType);
	}
	
	/*
	 * 根据用户ID和文件类型获取数据
	 * param: userId
	 * param: fileType
	 */
	public List<Work> selectByUserIdAndFileType(Integer userId, String fileType) {
		return workMapper.selectByUserIdAndFileType(userId, fileType);
	}
	
	/*
	 * 根据作品ID获取数据
	 * param: workId
	 */
	public Work selectByWorkId(Integer workId) {
		return workMapper.selectByWorkId(workId);
	}
	
	/*
	 * 根据关键词和文件类型获取数据
	 * param: keyword
	 * param: fileType
	 */
	public List<Work> selectByKeywordAndFileType(String keyword, String fileType) {
		return workMapper.selectByKeywordAndFileType(keyword, fileType);
	}
	
	/*
	 * 根据关键词和文件类型分页查询
	 * param: keyword
	 * param: fileType
	 * param: pageNum
	 * param: pageSize
	 */
	public Page<Work> selectWorkByKeywordPage(Integer pageNum, Integer pageSize, String keyword, String fileType) {
		Integer skipNum = (pageNum - 1) * pageSize;
		Page<Work> page = new Page<>();
		List<Work> workList = workMapper.selectWorkByKeywordPage(skipNum, pageSize, keyword, fileType);
		int total = workMapper.selectCountPage(keyword, fileType);
		page.setList(workList);
		page.setTotal(total);
		return page;
	}
	
	/*
	 * 分页查询所有作品数据
	 * param: keyword
	 * param: fileType
	 * param: pageNum
	 * param: pageSize
	 */
	public Page<Work> selectAllWorkPage(Integer pageNum, Integer pageSize) {
		Integer skipNum = (pageNum - 1) * pageSize;
		Page<Work> page = new Page<>();
		List<Work> workList = workMapper.selectAllWorkPage(skipNum, pageSize);
		int total = workMapper.selectAllCountPage();
		page.setList(workList);
		page.setTotal(total);
		return page;
	}

	
	public void updateWorkStatus(Integer workId, String status) {
		workMapper.updateWorkStatus(workId, status);
	}
}
