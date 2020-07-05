package com.it.community.dto;

import com.it.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
	private Long id;
	private Long gmtCreate;
	private Integer status;
	private User notifier;
	private String outerTitle;
	private String type;
}
