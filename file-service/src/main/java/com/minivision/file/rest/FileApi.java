package com.minivision.file.rest;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.minivision.file.config.FileConfig;
import com.minivision.file.enumeration.Status;
import com.minivision.file.rest.param.FileParam;
import com.minivision.file.rest.result.RestResult;

import fastdfs.client.FastdfsClient;
import fastdfs.client.FileId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 文件服务Rest API
 * @author hughzhao
 * @2017年7月6日
 */
@RestController
@RequestMapping(value = "file-api/v1", method = RequestMethod.POST)
@Api(tags = "FileApi", value = "Distributed File Storage Apis")
public class FileApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileApi.class);
	
	@Autowired
	private FastdfsClient fastdfsClient;
	
	@Autowired
	private FileConfig fileConfig;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Gson gson;
	
	@RequestMapping(value = "uploadFile")
	@ApiOperation(value = "上传文件", notes = "上传文件到FastDFS服务端")
	@ApiImplicitParams({
	      @ApiImplicitParam(value = "需要上传的文件", name = "file", paramType = "form", dataType = "file", required = true)})
	public RestResult<String> uploadFile(@Valid @ModelAttribute FileParam param, BindingResult errResult, HttpServletRequest request) {
		RestResult<String> response = new RestResult<>();
		
		if (errResult.hasErrors()) {
			List<ObjectError> errorList = errResult.getAllErrors();
            for(ObjectError error : errorList){
            	LOGGER.error(error.getDefaultMessage());
            }
			response.setErrorCode(Status.FAIL.getCode());
			response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
			return response;
		}
		
		LOGGER.info("请求参数：" + param);
		try {
			CompletableFuture<FileId> resultFuture = fastdfsClient.upload(param.getFile().getOriginalFilename(), param.getFile().getBytes());
			FileId fileId = resultFuture.get();
			String group = fileId.group();
			String path = fileId.path();
			
			LOGGER.info("文件ID：" + fileId);
			
			String urlPrefix = fileConfig.getUrlPrefix().get(group);
			String fileUrl = urlPrefix + path;
			LOGGER.info("文件地址：" + fileUrl);
			String shortUrlResult = restTemplate.getForObject(fileConfig.getShortenServiceUrl() + "?url=" + fileUrl, String.class);
			Map<String, String> resultJson = gson.fromJson(shortUrlResult, Map.class);
			String shortUrl = resultJson.get("s_url");
			LOGGER.info("短链接：" + shortUrl);
			
			response.setData(InetAddress.getLocalHost().getHostAddress() + ":" + request.getServerPort() + "/file" + shortUrl.substring(shortUrl.lastIndexOf("/")));
			response.setErrorCode(Status.SUCCESS.getCode());
			response.setErrorMessage(Status.SUCCESS.getDescription());
		} catch (IOException | InterruptedException | ExecutionException e) {
			LOGGER.error("上传失败", e);
			
			response.setErrorCode(Status.FAIL.getCode());
			response.setErrorMessage("上传失败");
			return response;
		} 
		
		return response;
	}
	
}
