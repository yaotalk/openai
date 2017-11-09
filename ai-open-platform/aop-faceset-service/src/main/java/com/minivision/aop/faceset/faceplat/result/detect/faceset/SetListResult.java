package com.minivision.aop.faceset.faceplat.result.detect.faceset;

import java.util.List;

public class SetListResult {

  private List<SetDetailResult> faceSets;
  
  private long total;

  public List<SetDetailResult> getFaceSets() {
    return faceSets;
  }

  public void setFaceSets(List<SetDetailResult> faceSets) {
    this.faceSets = faceSets;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }
  
  
}
