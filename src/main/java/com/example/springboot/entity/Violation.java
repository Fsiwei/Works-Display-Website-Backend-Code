package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author : siwei.fan
 * @date : 2024/4/17 0:26
 * @modyified By :
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RestController
public class Violation {
	private Integer userId;
	private String workName;
	private String description;
	private Date violationDate;
}