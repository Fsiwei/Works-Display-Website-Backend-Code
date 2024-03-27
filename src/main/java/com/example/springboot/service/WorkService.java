package com.example.springboot.service;

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
	
	public List<Work> selectAllWork() {
		return workMapper.selectAllWork();
	}
	
	public List<Work> selectByUseridAndFiletype(Integer userId, String fileType) {
		return workMapper.selectByUseridAndFiletype(userId, fileType);
	}
}
