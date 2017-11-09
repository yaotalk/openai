package com.minivision.aop.auth.util;

import org.hashids.Hashids;

public final class CaptchaUtil {

  private static final Hashids hashids = new Hashids("com.minivision.aop.auth", 4);

  public static String encode(long... args) {
    return hashids.encode(args);
  }

  public static long[] decode(String hashid) {
    return hashids.decode(hashid);
  }

}
