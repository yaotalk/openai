package com.minivision.aop.face.rest.param;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.annotations.ApiModel;

@ApiModel
public abstract class RestParam implements Serializable {

	private static final long serialVersionUID = 1922721475810919555L;

/*	@NotBlank(message = "apiKey must not be empty")
	protected String apiKey;

	@NotBlank(message = "apiSecret must not be empty")
	protected String apiSecret;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	@Override
	public String toString() {
		return "RestRequest [apiKey=" + apiKey + ", apiSecret=" + apiSecret + "]";
	}*/
	
	@Override
	public String toString() {
	  return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
