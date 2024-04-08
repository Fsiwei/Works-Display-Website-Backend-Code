package com.example.springboot.exception;

import com.example.springboot.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : siwei.fan
 * @date : 2024/3/1 23:42
 * @modyified By :
 */

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(ServiceException.class)
	@ResponseBody // 将 Result 对象转换成 json
	public Result serviceException(ServiceException e) {
		return Result.error(e.getCode(), e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result globalException(Exception e) {
		e.printStackTrace();
		return Result.error("500", "系统错误，全局");
	}
}
