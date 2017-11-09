package com.minivision.aop.faceset.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileClientHystrix implements FileClient {

	@Override
	public Map<String, Object> uploadFile(String fileNum, MultipartFile file) {
		Map<String, Object> result = new HashMap<>();
		result.put("errorCode", 2);
		result.put("errorMessage", "失败");
		return result;
	}

}
