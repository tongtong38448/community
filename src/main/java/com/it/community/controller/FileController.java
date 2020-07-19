package com.it.community.controller;

import com.it.community.dto.FileDTO;
import com.it.community.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class FileController {
	@Autowired
	private UCloudProvider uCloudProvider;

	@RequestMapping("/file/upload")
	public FileDTO upload(HttpServletRequest request){
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
		try {
			String fileName = uCloudProvider.upload(file.getInputStream(),file.getContentType(),file.getOriginalFilename());
			FileDTO fileDTO = new FileDTO();
			fileDTO.setSuccess(1);
			fileDTO.setUrl(fileName);
			return fileDTO;
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileDTO fileDTO = new FileDTO();
		fileDTO.setSuccess(1);
		fileDTO.setUrl("/images/wechat.png");
		return fileDTO;
	}
}
