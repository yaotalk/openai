package com.minivision.aop.face.rest.param.faceset;

import com.minivision.aop.face.rest.param.RestParam;

public class SetCreateParam extends RestParam {

	private static final long serialVersionUID = -4555796023459761095L;

	private String displayName;
	private String outerId;
	private String owner;
	
	private int capacity = 1000;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

    public int getCapacity() {
      return capacity;
    }
  
    public void setCapacity(int capacity) {
      this.capacity = capacity;
    }
	
}
