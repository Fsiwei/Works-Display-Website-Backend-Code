package com.example.springboot.service;

import com.example.springboot.entity.Message;
import com.example.springboot.entity.Work;
import com.example.springboot.mapper.MessageMapper;
import com.example.springboot.mapper.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author : siwei.fan
 * @date : 2024/4/17 0:29
 * @modyified By :
 */

@RestController
@Service
public class MessageService {
	
	@Autowired
	MessageMapper messageMapper;
	
	@Autowired // 注入 Mapper
	WorkMapper workMapper;
	
	public void insertMessage(Message message, Integer workId, String status){
		Work work = workMapper.selectByWorkId(workId);
		String content = "您上传的作品《" + work.getWorkName() + "》";
		if (Objects.equals(status, "已发布")) {
			content = content + "审核通过了";
		} else {
			content = content + "被驳回，原因是" + message.getContent();
		}
		message.setContent(content);
		messageMapper.insert(message);
	}
	
	public List<Message> getMessageByAuthorId(Integer authorId) {
		return messageMapper.selectMessageByAuthorId(authorId);
	}
}
