package com.minivision.aop.face.rest.param.faceset;

import javax.validation.constraints.Max;

import com.minivision.aop.face.rest.param.RestParam;

public class SetListParam extends RestParam {
  
  private int offset;
  @Max(100)
  private int limit;
  public int getOffset() {
    return offset;
  }
  public void setOffset(int offset) {
    this.offset = offset;
  }
  public int getLimit() {
    return limit;
  }
  public void setLimit(int limit) {
    this.limit = limit;
  }
  
}
