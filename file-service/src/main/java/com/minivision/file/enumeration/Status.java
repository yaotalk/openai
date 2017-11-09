package com.minivision.file.enumeration;

import java.util.Arrays;

import lombok.Getter;

/**
 * 接口调用状态
 * @author hughzhao
 * @2017年5月22日
 */
@Getter
public enum Status {

  SUCCESS(1, "成功"),
  FAIL(2, "失败");

  private int code;
  private String description;

  private Status(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public static String getNameByCode(int code) {
    return Arrays.asList(Status.values())
        .stream().filter(status -> status.getCode() == code)
        .map(status -> status.name()).findAny().orElse("UNKNOWN");
  }

  @Override
  public String toString() {
    return this.code + "_" + this.description;
  }

}
