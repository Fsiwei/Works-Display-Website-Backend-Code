package com.example.springboot.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author : siwei.fan
 * @date : 2024/4/3 22:01
 * @modyified By :
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RestController
public class Comment {
	private Integer commentId;
	private Integer userId;
	private String username;
	private String avatar;
	private Integer authorId;
	private Integer workId;
	private String workName;
	private String content;
	private Date commentDate;
}
