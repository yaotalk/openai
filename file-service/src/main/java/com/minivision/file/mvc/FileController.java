package com.minivision.file.mvc;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.minivision.file.config.FileConfig;

/**
 * 短链接请求地址还原并重定向
 * @author hughzhao
 * @2017年7月6日
 */
@Controller
public class FileController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileConfig fileConfig;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Gson gson;

	@GetMapping("/file/{path}")
	public String getFile(@PathVariable String path) {
		String shortUrl = fileConfig.getShortenServiceRoot() + path;
		LOGGER.info("短链接：" + shortUrl);
		String expandResult = restTemplate.getForObject(fileConfig.getExpandServiceUrl() + "?s_url=" + shortUrl, String.class);
		Map<String, String> expandJson = gson.fromJson(expandResult, Map.class);
		String fileUrl = expandJson.get("url");
		LOGGER.info("文件地址：" + fileUrl);
		return "redirect:" + fileUrl;
	}
	
}
