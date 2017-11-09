package com.minivision.aop.faceset.util;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {
  
  public static String getDatePath(){
    return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }

  public static List<String> getDiskRoot(){
    File[] roots = File.listRoots();
    return Arrays.stream(roots).filter(f -> f.canRead()).map(f -> f.getPath()).collect(Collectors.toList());
  }
  
  public static List<String> listDir(String parent){
    File dir = new File(parent);
    if(dir.exists() && dir.isDirectory()){
      File[] files = dir.listFiles(f -> f.isDirectory()&&!f.isHidden());
      if(files != null){
        return Arrays.stream(files).map(f -> f.getPath()).collect(Collectors.toList());
      }
    }
    return null;
  }
}
