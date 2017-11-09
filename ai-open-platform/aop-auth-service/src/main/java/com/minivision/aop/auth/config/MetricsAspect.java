package com.minivision.aop.auth.config;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.minivision.ai.enumeration.Status;
import com.minivision.ai.rest.result.RestResult;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class MetricsAspect {

  @Autowired
  private CounterService counterService;

  @Autowired
  private GaugeService gaugeService;
  
  @Autowired
  private InfluxDB influxDB;

  @Around("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(mapping) && @annotation(apiOperation)")
  public Object recordMetrics(ProceedingJoinPoint proceedingJoinPoint, RequestMapping mapping, ApiOperation apiOperation) {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String url = request.getRequestURL().toString();
    
    log.info("API请求：" + apiOperation.value());

    long start = System.currentTimeMillis();
    Object result = null;
    try {
      result = proceedingJoinPoint.proceed();
    } catch (Throwable e) {
      log.error("Rest接口调用异常：" + url, e);
      result = new RestResult<>(Status.FAIL.getCode(), Status.FAIL.getDescription());
    }

    if(result instanceof RestResult<?>){
      RestResult<?> res = (RestResult<?>) result;
      int consume = (int) (System.currentTimeMillis() - start);
      res.setTimeUsed(consume);
      
      influxDB.setDatabase("api_metrics");
      influxDB.disableBatch();
      String username = request.getParameter("username");
      String appId = request.getParameter("appId");
      appId = StringUtils.hasText(appId) ? appId : "default";
      String api = mapping.value()[0];
      int status = 0;
      
      gaugeService.submit("response.time." + url, consume);
      if (res.getErrorCode() == Status.SUCCESS.getCode()) {
        counterService.increment("counter.success." + url);
        status = Status.SUCCESS.getCode();
      } else {
        counterService.increment("counter.failure." + url);
        status = Status.FAIL.getCode();
      }
      
      Point point = Point.measurement("metrics")
          .tag("username", username)
          .tag("appId", appId)
          .tag("api", api)
          .addField("status", status)
          .addField("consume", consume)
          .addField("count", 1)
          .build();
      influxDB.write(point);
    }

    return result;
  }

}
