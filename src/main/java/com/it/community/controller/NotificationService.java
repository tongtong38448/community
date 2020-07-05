package com.it.community.controller;

import com.it.community.dto.NotificationDTO;
import com.it.community.dto.PageInfo;
import com.it.community.mapper.NotificationMapper;
import com.it.community.mapper.UserMapper;
import com.it.community.model.Notification;
import com.it.community.model.NotificationExample;
import com.it.community.model.User;
import com.it.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private UserMapper userMapper;
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
		List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
		if(notifications.size()==0){
			return pageInfo;
		}else {
			Set<Long> disUserIds = notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
			ArrayList<Long> userIds = new ArrayList<>(disUserIds);
			UserExample userExample = new UserExample();
			userExample.createCriteria().andIdIn(userIds);
			List<User> users = userMapper.selectByExample(userExample);
			Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));


		}
		List<NotificationDTO> notificationDTOS = new ArrayList<>();
		pageInfo.setData(notificationDTOS);
		return null;
	}
}
