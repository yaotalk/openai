package com.minivision.aop.auth.rest;

import java.util.List;

//import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minivision.ai.enumeration.Status;
import com.minivision.ai.rest.result.RestResult;
import com.minivision.aop.auth.domain.Developer;
import com.minivision.aop.auth.rest.param.CreateDeveloperParam;
import com.minivision.aop.auth.rest.param.LoginParam;
import com.minivision.aop.auth.rest.param.ResetPwdParam;
import com.minivision.aop.auth.rest.param.UpdateDeveloperParam;
import com.minivision.aop.auth.rest.result.LoginResult;
import com.minivision.aop.auth.service.DeveloperService;
import com.minivision.aop.auth.util.CaptchaUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "api/v1", method = RequestMethod.POST)
@Api(tags = "DeveloperApi", value = "AI Open Platform Developer Apis")
@Slf4j
@RefreshScope
public class DeveloperApi {

  @Autowired
  private DeveloperService developerService;

//  @Autowired
//  private JavaMailSender mailSender;

  @Value("${resetpwd.code.timeout}")
  private long timeout;

  @RequestMapping(value = "createDeveloper")
  @ApiOperation(value = "开发者注册", notes = "开发者注册账号")
  public RestResult<String> createDeveloper(@Valid @ModelAttribute CreateDeveloperParam param, BindingResult errResult) {
    RestResult<String> response = new RestResult<>();

    if (errResult.hasErrors()) {
      List<ObjectError> errorList = errResult.getAllErrors();
      for(ObjectError error : errorList){
        log.error(error.getDefaultMessage());
      }
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
      return response;
    }

    try {
      Developer exists = developerService.findByUsername(param.getUsername());
      if (exists != null) {
        response.setErrorCode(Status.FAIL.getCode());
        response.setErrorMessage("用户" + param.getUsername() + "已存在，请使用其他用户名！");
        return response;
      }

      Developer developer = new Developer();
      BeanUtils.copyProperties(param, developer);
      developerService.create(developer);
    } catch (Throwable e) {
      log.error("开发者注册失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("开发者注册失败");
      return response;
    }

    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    return response;
  }

  @RequestMapping(value = "/auth/updateDeveloper")
  @ApiOperation(value = "开发者修改个人资料", notes = "开发者修改个人资料")
  public RestResult<String> updateDeveloper(@Valid @ModelAttribute UpdateDeveloperParam param, BindingResult errResult) {
    RestResult<String> response = new RestResult<>();

    if (errResult.hasErrors()) {
      List<ObjectError> errorList = errResult.getAllErrors();
      for(ObjectError error : errorList){
        log.error(error.getDefaultMessage());
      }
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
      return response;
    }

    try {
      Developer developer = new Developer();
      BeanUtils.copyProperties(param, developer);
      if (developerService.login(developer).getDeveloper() == null) {
        log.error(param.getUsername() + "：密码错误");
        response.setErrorCode(Status.FAIL.getCode());
        response.setErrorMessage("密码错误不能修改");
        return response;
      }
      //修改密码
      if (StringUtils.isNotBlank(param.getNewpassword())) {
        developer.setPassword(param.getNewpassword());
      }
      developerService.update(developer);
    } catch (Throwable e) {
      log.error("开发者修改个人资料失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("开发者修改个人资料失败");
      return response;
    }

    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    return response;
  }

  @RequestMapping(value = "login")
  @ApiOperation(value = "开发者登录", notes = "开发者登录")
  public RestResult<Developer> login(@Valid @ModelAttribute LoginParam param, BindingResult errResult) {
    RestResult<Developer> response = new RestResult<>();
    LoginResult loginResult = null;

    if (errResult.hasErrors()) {
      List<ObjectError> errorList = errResult.getAllErrors();
      for(ObjectError error : errorList){
        log.error(error.getDefaultMessage());
      }
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
      return response;
    }

    try {
      Developer developer = new Developer();
      BeanUtils.copyProperties(param, developer);
      loginResult = developerService.login(developer);
      if (loginResult.getDeveloper() == null) {
        log.error(param.getUsername() + "：用户名或密码错误");
        response.setErrorCode(Status.FAIL.getCode());
        response.setErrorMessage(loginResult.getMessage());
        return response;
      }
    } catch (Throwable e) {
      log.error("开发者登录失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("开发者登录失败");
      return response;
    }

    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(loginResult.getMessage());
    response.setData(loginResult.getDeveloper());
    return response;
  }

  //忘记密码
//  @RequestMapping(value = "forgetPwd")
//  @ApiOperation(value = "忘记密码", notes = "忘记密码")
//  @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String", required = true)
//  public RestResult<String> forgetPwd(@RequestParam("username") String username) {
//    RestResult<String> response = new RestResult<>();
//
//    if (StringUtils.isBlank(username)) {
//      response.setErrorCode(Status.FAIL.getCode());
//      response.setErrorMessage("用户名不能为空");
//      return response;
//    }
//
//    MimeMessage message = mailSender.createMimeMessage();
//    MimeMessageHelper helper = new MimeMessageHelper(message);
//    try {
//      Developer exists = developerService.findByUsername(username);
//      if (exists == null) {
//        response.setErrorCode(Status.FAIL.getCode());
//        response.setErrorMessage("用户" + username + "不存在");
//        return response;
//      }
//
//      helper.setFrom("minivision_ai@163.com", "小视科技人工智能研发部");
//      helper.setSubject("重置密码验证码");
//      helper.setTo(exists.getEmail());
//      helper.setCc("minivision_ai@163.com");
//      helper.setText("您的重置密码验证码为" + (CaptchaUtil.encode(exists.getId(), System.currentTimeMillis())) + "，请在重置密码页面输入！");
//      mailSender.send(message);
//    } catch (Throwable e) {
//      log.error("忘记密码发送验证码邮件失败", e);
//      response.setErrorCode(Status.FAIL.getCode());
//      response.setErrorMessage("忘记密码发送验证码邮件失败");
//      return response;
//    }
//
//    response.setErrorCode(Status.SUCCESS.getCode());
//    response.setErrorMessage(Status.SUCCESS.getDescription());
//    return response;
//  }

  //重置密码
  @RequestMapping(value = "resetPwd")
  @ApiOperation(value = "重置密码", notes = "重置密码")
  public RestResult<String> resetPwd(@Valid @ModelAttribute ResetPwdParam param, BindingResult errResult) {
    RestResult<String> response = new RestResult<>();

    if (errResult.hasErrors()) {
      List<ObjectError> errorList = errResult.getAllErrors();
      for(ObjectError error : errorList){
        log.error(error.getDefaultMessage());
      }
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage(Status.FAIL.getDescription() + "：" + errorList.get(0).getDefaultMessage());
      return response;
    }

    long[] args = CaptchaUtil.decode(param.getCode());
    if (args == null || args.length == 0 || args.length == 1) {
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("验证码错误");
      return response;
    }

    try {
      Developer exists = developerService.findByUsername(param.getUsername());
      if (exists == null) {
        response.setErrorCode(Status.FAIL.getCode());
        response.setErrorMessage("用户" + param.getUsername() + "不存在");
        return response;
      }

      if (exists.getId() != args[0]) {
        response.setErrorCode(Status.FAIL.getCode());
        response.setErrorMessage("验证码错误");
        return response;
      }

      if (((System.currentTimeMillis() - args[1]) / 60000) >= timeout) {
        response.setErrorCode(Status.FAIL.getCode());
        response.setErrorMessage("验证码过期");
        return response;
      }

      Developer developer = new Developer();
      developer.setUsername(param.getUsername());
      developer.setPassword(param.getNewpassword());
      developerService.update(developer);
    } catch (Throwable e) {
      log.error("重置密码失败", e);
      response.setErrorCode(Status.FAIL.getCode());
      response.setErrorMessage("重置密码失败");
      return response;
    }

    response.setErrorCode(Status.SUCCESS.getCode());
    response.setErrorMessage(Status.SUCCESS.getDescription());
    return response;
  }

}
