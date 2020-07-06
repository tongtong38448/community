package com.it.community.service;

import com.it.community.dto.NotificationDTO;
import com.it.community.dto.PageInfo;
import com.it.community.enums.NotificationStatusEnum;
import com.it.community.enums.NotificationTypeEnum;
import com.it.community.excepion.CustomizeErrorCode;
import com.it.community.excepion.CustomizeException;
import com.it.community.mapper.NotificationMapper;
import com.it.community.model.Notification;
import com.it.community.model.NotificationExample;
import com.it.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
	@Autowired
	private NotificationMapper notificationMapper;

	public PageInfo list(Long userId, Integer page, Integer size) {
		PageInfo<NotificationDTO> pageInfo = new PageInfo<>();

		Integer totalPage;//总页数

		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria().andReceiverEqualTo(userId);
		Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
		if (totalCount % size == 0) {
			totalPage = totalCount / size;
		} else {
			totalPage = totalCount / size + 1;
		}
		if (page < 1) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		pageInfo.setPageInfo(totalPage, page);

		Integer offset = size * (page - 1);
		NotificationExample example = new NotificationExample();
		example.createCriteria().andReceiverEqualTo(userId);
		example.setOrderByClause("gmt_create desc");
		List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
		if (notifications.size() == 0) {
			return pageInfo;
		}
		List<NotificationDTO> notificationDTOS = new ArrayList<>();
		for (Notification notification : notifications) {
			NotificationDTO notificationDTO = new NotificationDTO();
			BeanUtils.copyProperties(notification, notificationDTO);
			notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
			notificationDTOS.add(notificationDTO);
		}
		pageInfo.setData(notificationDTOS);
		return pageInfo;
	}

	public Long unreadCount(Long userId) {
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus()
		);
		return notificationMapper.countByExample(notificationExample);
	}

	public NotificationDTO read(Long id, User user) {
		Notification notification = notificationMapper.selectByPrimaryKey(id);
		if(notification.getReceiver() != user.getId()){
			throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
		}
		if(notification.getReceiver() == null){
			throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
		}
		notification.setStatus(NotificationStatusEnum.READ.getStatus());
		notificationMapper.updateByPrimaryKey(notification);
		NotificationDTO notificationDTO = new NotificationDTO();
		BeanUtils.copyProperties(notification, notificationDTO);
		notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
		return notificationDTO;
	}
}
