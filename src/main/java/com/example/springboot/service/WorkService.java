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
	
	public void insertWork(Work work) {
		workMapper.insert(work);
	}
	
	public List<Work> selectWorkByFileType(String fileType) {
		return workMapper.selectWorkByFileType(fileType);
	}
	
	public List<Work> selectByUserIdAndFileType(Integer userId, String fileType) {
		return workMapper.selectByUserIdAndFileType(userId, fileType);
	}
	
	public Work selectByWorkId(Integer workId) {
		return workMapper.selectByWorkId(workId);
	}
	
	public List<Work> selectByKeywordImage(String keyword, String fileType) {
		return workMapper.selectByKeywordImage(keyword, fileType);
	}
	
	public List<Work> selectByKeywordVideo(String keyword, String fileType) {
		return workMapper.selectByKeywordVideo(keyword, fileType);
	}
	
	public Page<Work> selectWorkByKeywordPage(Integer pageNum, Integer pageSize, String keyword, String fileType) {
		Integer skipNum = (pageNum - 1) * pageSize;
		Page<Work> page = new Page<>();
		List<Work> workList = workMapper.selectWorkByKeywordPage(skipNum, pageSize, keyword, fileType);
		int total = workMapper.selectCountPage(keyword, fileType);
		page.setList(workList);
		page.setTotal(total);
		return page;
	}
}
