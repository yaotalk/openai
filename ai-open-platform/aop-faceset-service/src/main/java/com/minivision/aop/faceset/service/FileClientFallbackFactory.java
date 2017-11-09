package com.minivision.aop.faceset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileClientFallbackFactory implements FallbackFactory<FileClient> {
	
	@Autowired
	private FileClientHystrix fileClientFallback;

	@Override
	public FileClient create(Throwable cause) {
		log.error("file-service not available", cause);
		return fileClientFallback;
	}

}
