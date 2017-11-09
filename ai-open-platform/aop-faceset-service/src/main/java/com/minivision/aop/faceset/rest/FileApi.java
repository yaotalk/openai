package com.minivision.aop.faceset.rest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.minivision.aop.faceset.service.FileClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class FileApi {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileApi.class);

	@Autowired
	private FileClient fileClient;
	
	@PostMapping("/file")
	@ApiOperation(hidden = false, value = "uploadFile")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "file", paramType = "form", dataType = "file", required = true)})
	public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
		String fileNum = UUID.randomUUID().toString();
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		return fileClient.uploadFile(fileNum, file);
	}
	
	@GetMapping("/timeout1")
	@ApiOperation(hidden = true, value = "timeout")
	@HystrixCommand(fallbackMethod = "timeout1Fallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
    })
    public String timeout1() throws InterruptedException {
		LOG.info("Entering timeout1()...");
		TimeUnit.MILLISECONDS.sleep(98);
		return "success";
	}
	
	@GetMapping("/timeout2")
    @ApiOperation(hidden = true, value = "timeout")
    @HystrixCommand(fallbackMethod = "timeout2Fallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
    })
    public String timeout2(@RequestParam String name) throws InterruptedException {
        LOG.info("Entering timeout2()...");
        TimeUnit.MILLISECONDS.sleep(102);
        return "success";
    }
	
	public String timeout1Fallback() {
        return "timeout";
    }
	
	public String timeout2Fallback(String name, Throwable e) {
	    LOG.info("request params:name->" + name);
	    LOG.error("timeout exception", e);
		return "timeout";
	}
	
}
