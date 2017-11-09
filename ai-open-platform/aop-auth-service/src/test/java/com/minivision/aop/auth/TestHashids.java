package com.minivision.aop.auth;

import org.hashids.Hashids;

public class TestHashids {

  public static void main(String[] args) {
    Hashids hashids = new Hashids("com.minivision.aop.auth", 4);
    for (int i = 0; i < 10; i++) {
      System.out.println(hashids.encode(i + 1, System.currentTimeMillis()));
    }
  }

}
