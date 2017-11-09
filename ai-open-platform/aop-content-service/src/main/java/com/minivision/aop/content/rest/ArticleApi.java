package com.minivision.aop.content.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minivision.ai.enumeration.Status;
import com.minivision.ai.rest.result.RestResult;
import com.minivision.aop.content.domain.Article;
import com.minivision.aop.content.rest.param.ArticleListParam;
import com.minivision.aop.content.rest.param.CreateArticleParam;
import com.minivision.aop.content.rest.param.UpdateArticleParam;
import com.minivision.aop.content.rest.result.ArticleListResult;
import com.minivision.aop.content.service.ArticleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "api/v1", method = RequestMethod.POST)
@Api(tags = "ArticleApi", value = "AI Open Platform Article Apis")
@Slf4j
public class ArticleApi {

  @Autowired
  private ArticleService articleService;
  
  @RequestMapping(value = "createArticle")
  @ApiOperation(value = "创建内容", notes = "创建内容")
  public RestResult<Article> createArticle(@Valid @ModelAttribute CreateArticleParam param, BindingResult errResult) {
    RestResult<Article> response = new RestResult<>();

    if (errResult.hasErrors()) {
      List<ObjectError> errorList = errResult.getAllErrors();
      for(ObjectError error : errorList){
        log.error(error.getDefaultMessage());
      }
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
      return response;
    }
    
    Article created = null;
    try {
      Article article = new Article();
      BeanUtils.copyProperties(param, article);
      created = articleService.createArticle(article);
    } catch (Throwable e) {
      log.error("创建内容失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("创建内容失败");
      return response;
    }
    
    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    response.setData(created);
    return response;
  }
  
  @RequestMapping(value = "updateArticle")
  @ApiOperation(value = "修改内容", notes = "修改内容")
  public RestResult<Article> updateArticle(@Valid @ModelAttribute UpdateArticleParam param, BindingResult errResult) {
    RestResult<Article> response = new RestResult<>();

    if (errResult.hasErrors()) {
      List<ObjectError> errorList = errResult.getAllErrors();
      for(ObjectError error : errorList){
        log.error(error.getDefaultMessage());
      }
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
      return response;
    }
    
    Article updated = null;
    try {
      Article article = new Article();
      BeanUtils.copyProperties(param, article);
      updated = articleService.updateArticle(article);
    } catch (Throwable e) {
      log.error("修改内容失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("修改内容失败");
      return response;
    }
    
    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    response.setData(updated);
    return response;
  }
  
  @RequestMapping(value = "deleteArticle")
  @ApiOperation(value = "删除内容", notes = "删除内容")
  @ApiImplicitParams(value = {
      @ApiImplicitParam(name = "id", value = "应用主键ID", paramType = "query", dataType = "long", required = true)
  })
  public RestResult<String> deleteArticle(@RequestParam("id") long id) {
    RestResult<String> response = new RestResult<>();
    
    if (id <= 0) {
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("应用主键ID无效");
      return response;
    }

    try {
      articleService.deleteArticle(id);
    } catch (Throwable e) {
      log.error("删除内容失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("删除内容失败");
      return response;
    }
    
    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    return response;
  }
  
  @RequestMapping(value = "getArticle")
  @ApiOperation(value = "获取详细内容", notes = "获取详细内容")
  @ApiImplicitParams(value = {
      @ApiImplicitParam(name = "id", value = "应用主键ID", paramType = "query", dataType = "long", required = true)
  })
  public RestResult<Article> getArticle(@RequestParam("id") long id) {
    RestResult<Article> response = new RestResult<>();
    
    if (id <= 0) {
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("应用主键ID无效");
      return response;
    }

    Article detail = null;
    try {
      detail = articleService.findArticle(id);
    } catch (Throwable e) {
      log.error("获取详细内容失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("获取详细内容失败");
      return response;
    }
    
    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    response.setData(detail);
    return response;
  }
  
  @RequestMapping(value = "articleList")
  @ApiOperation(value = "获取内容列表", notes = "获取内容列表")
  public RestResult<ArticleListResult> articleList(@Valid @ModelAttribute ArticleListParam param, BindingResult errResult) {
    RestResult<ArticleListResult> response = new RestResult<>();
    
    if (errResult.hasErrors()) {
      List<ObjectError> errorList = errResult.getAllErrors();
      for(ObjectError error : errorList){
        log.error(error.getDefaultMessage());
      }
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
      return response;
    }

    ArticleListResult result = new ArticleListResult();
    try {
      Page<Article> page = articleService.findArticles(param);
      if (page != null && page.hasContent()) {
        result.setPages(page.getTotalPages());
        result.setTotal(page.getTotalElements());
        result.setArticleList(page.getContent());
      }
    } catch (Throwable e) {
      log.error("获取内容列表失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("获取内容列表失败");
      return response;
    }
    
    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    response.setData(result);
    return response;
  }
  
}
