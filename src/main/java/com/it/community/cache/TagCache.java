package com.it.community.cache;

import com.it.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
	public static List<TagDTO> get(){
		ArrayList<TagDTO> tagDTOS = new ArrayList<>();
		TagDTO program = new TagDTO();
		program.setCategoryName("开发语言");
		program.setTags(Arrays.asList("js","php","css","html","java","node","python"));
		tagDTOS.add(program);
		TagDTO framework = new TagDTO();
		framework.setCategoryName("平台框架");
		framework.setTags(Arrays.asList("spring","spring mvc","mybatis"));
		tagDTOS.add(framework);
		TagDTO server = new TagDTO();
		server.setCategoryName("服务器");
		server.setTags(Arrays.asList("tomcat","apache","nginx","centos"));
		tagDTOS.add(server);
		TagDTO db = new TagDTO();
		db.setCategoryName("数据库");
		db.setTags(Arrays.asList("mysql","sqlserver","oracle","sqlite"));
		tagDTOS.add(db);
		TagDTO tools = new TagDTO();
		tools.setCategoryName("开发工具");
		tools.setTags(Arrays.asList("idea","eclipse","sql yog","vs code"));
		tagDTOS.add(tools);
		return tagDTOS;
	}
	public static String filterInvalid(String tags){
		String[] split = StringUtils.split(tags, ",");
		List<TagDTO> tagDTOS = get();
		List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
		String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
		return invalid;
	}
}
