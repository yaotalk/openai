package com.minivision.urlshorten;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

public class UrlShortenService {

	@Autowired
	private UrlShortenProperties properties;
	
	@Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private Gson gson;

	public String toShort(String group, String url) {
	  String shortUrlResult = restTemplate.getForObject(properties.getShortenServiceUrl() + "?url=" + url, String.class);
      Map<String, String> resultJson = gson.fromJson(shortUrlResult, Map.class);
	  String shortUrl = resultJson.get("s_url");
	  String path = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
	  return properties.getShortUrlPrefix() + (StringUtils.isEmpty(group) ? "" : (group + "/")) + path;
	}
	
	public String toExpand(String path) {
	  String shortUrl = properties.getShortenServiceRoot() + path;
      String expandResult = restTemplate.getForObject(properties.getExpandServiceUrl() + "?s_url=" + shortUrl, String.class);
      Map<String, String> expandJson = gson.fromJson(expandResult, Map.class);
      return expandJson.get("url");
	}
	
}
