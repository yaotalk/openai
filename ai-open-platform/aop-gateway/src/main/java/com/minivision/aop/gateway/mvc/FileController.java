package com.minivision.aop.gateway.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.minivision.urlshorten.UrlShortenService;

@Controller
public class FileController {
  
  @Autowired
  private UrlShortenService shortService;
  
  @GetMapping("/file/{faceset}/{path}")
  public String redirect(@PathVariable String faceset, @PathVariable String path) {
      return "redirect:" + shortService.toExpand(path);
  }

}
