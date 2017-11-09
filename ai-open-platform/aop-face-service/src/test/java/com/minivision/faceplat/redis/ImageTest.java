package com.minivision.faceplat.redis;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

import org.apache.commons.io.FileUtils;

public class ImageTest {
  
  public static void main(String[] args) throws IOException {
    byte[] bs = FileUtils.readFileToByteArray(new File("E://9000001.jpg"));
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bs);
    BufferedImage image = ImageIO.read(inputStream);
    System.out.println(image);
    
    ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
    File file1 = new File("E://9000001_2.jpg");
    writer.setOutput(ImageIO.createImageOutputStream(file1));
    writer.write(image);
  }

}
