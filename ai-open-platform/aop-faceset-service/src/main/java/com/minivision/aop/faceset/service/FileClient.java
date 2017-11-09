package com.minivision.aop.faceset.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.minivision.aop.faceset.config.FeignMultipartSupportConfig;

//@FeignClient(value = "file-service", fallbackFactory = FileClientFallbackFactory.class, configuration = FeignMultipartSupportConfig.class)
public interface FileClient {
	
	@RequestMapping(value = "/file-api/v1/uploadFile", method = RequestMethod.POST)
    Map<String, Object> uploadFile(@RequestParam("fileNum") String fileNum, @RequestPart("file") MultipartFile file);
	
}
