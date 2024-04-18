package com.example.springboot.service;

import com.example.springboot.entity.Violation;
import com.example.springboot.mapper.ViolationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : siwei.fan
 * @date : 2024/4/17 21:58
 * @modyified By :
 */

@RestController
@Service
public class ViolationService {
	
	@Autowired
	ViolationMapper violationMapper;
	
	public void insertViolation(Violation violation){
		violationMapper.insert(violation);
	}
}
