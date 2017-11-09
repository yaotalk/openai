package com.minivision.fastdfs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import fastdfs.client.FastdfsClient;
import fastdfs.client.FileId;

public class FdfsService {

	@Autowired
	private FastdfsClient fastdfsClient;
	
	@Autowired
	private FdfsProperties properties;

	public String uploadToFastDFS(String fileName, byte[] fileData) throws InterruptedException, ExecutionException {
		CompletableFuture<FileId> resultFuture = fastdfsClient.upload(fileName, fileData);
		FileId fileId = resultFuture.get();
		return fileId.toBase64String();
	}
	
	public byte[] downloadFromFastDFS(String fileIdBase64) throws InterruptedException, ExecutionException {
		FileId fileId = FileId.fromBase64String(fileIdBase64);
		return fastdfsClient.download(fileId).get();
	}
	
	public String getFileUrl(String fileIdBase64) {
		FileId fileId = FileId.fromBase64String(fileIdBase64);
		String group = fileId.group();
		String path = fileId.path();
		String urlPrefix = properties.getUrlPrefix().get(group);
		String fileUrl = urlPrefix + path;
		return fileUrl;
	}
	
}
