package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
 * @author : siwei.fan
 * @date : 2024/3/16 15:51
 * @modyified By :
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RestController
public class Work {
	private Integer workId;
	private Integer userId;
	private String username;
	private String avatar;
	private String workName;
	private String workDescribe;
	private String workType;
	private Date uploadDate;
	private String workUrls;
	private String fileType;
	private String status;
	private Integer workLike;
	private Integer workCollection;
	private Integer comment;
}
