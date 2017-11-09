package com.minivision.file.rest.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileParam extends RestParam {

	private static final long serialVersionUID = -1908457230797994211L;

	@NotNull(message = "文件不能为空")
	@ApiModelProperty(value = "需要上传的文件", required = true)
	private MultipartFile file;
	
	@NotBlank(message = "文件编码不能为空")
	@ApiModelProperty(value = "文件唯一编码", required = true)
	private String fileNum;

	@Override
	public String toString() {
		return "FileParam [file=" + (file == null ? "null" : file.getOriginalFilename()) + ", fileNum=" + fileNum + "]";
	} 
	
}
