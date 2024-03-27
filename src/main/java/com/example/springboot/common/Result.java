package com.example.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : siwei.fan
 * @date : 2024/2/25 13:44
 * @modyified By :
 */

@Data // 自动创建 getter setter
@AllArgsConstructor // 全属性的构造函数
@NoArgsConstructor // 无参数的构造函数
@Builder // 建造者模式，创建对象
public class Result {
	
	public static final String CODE_SUCCESS = "200";
	public static final String CODE_AUTH_ERROR = "401";
	public static final String CODE_SYS_ERROR = "500";
	
	/**
	 * code: 请求的返回编码 200 401 404 500 编码表示这次请求是成功还是失败，可以看出失败的类型是什么
	 * msg: 表示对 code 的详细描述
	 * data: 请求成功则返回请求的数据
	 */
	private String code;
	private String msg;
	private Object data;
	
	public static Result success() {
		return Result.builder().code(CODE_SUCCESS).msg("请求成功").build();
		// return new Result(CODE_SUCCESS, "请求成功", null);
	}
	public static Result success(Object data) {
		return new Result(CODE_SUCCESS, "请求成功", data);
	}
	public static Result error(String msg) {
		return new Result(CODE_SYS_ERROR, msg, null);
	}
	public static Result error(String code, String msg) {
		return new Result(code, msg, null);
	}
	public static Result error() {
		return new Result(CODE_SYS_ERROR, "系统错误!", null);
	}
	
}
