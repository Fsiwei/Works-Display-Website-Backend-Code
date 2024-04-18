package com.example.springboot.controller;

import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Comment;
import com.example.springboot.entity.Message;
import com.example.springboot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : siwei.fan
 * @date : 2024/4/17 22:25
 * @modyified By :
 */

@RestController
@RequestMapping("/api/message")
public class MessageController {
	
	@Autowired
	MessageService messageService;
	
	@AuthAccess
	@GetMapping("/getMessageByAuthorId")
	public Result getMessageByAuthorId(@RequestParam String id) {
		Integer authorId = Integer.valueOf(id);
		try {
			List<Message> messageList = messageService.getMessageByAuthorId(authorId);
			return Result.success(messageList);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return Result.error("获取作者消息数据错误");
			} else {
				System.out.println(e.getMessage());
				return Result.error("系统错误");
			}
		}
	}
}
