package com.minivision.aop.face.rest.result.detect;

public class FaceAttribute {
  private int age;
  private double ageConfidence;
  private int gender;
  private double genderConfidence;
  
  public int getAge() {
    return age;
  }
  public void setAge(int age) {
    this.age = age;
  }
  public int getGender() {
    return gender;
  }
  public void setGender(int gender) {
    this.gender = gender;
  }
  public double getAgeConfidence() {
    return ageConfidence;
  }
  public void setAgeConfidence(double ageConfidence) {
    this.ageConfidence = ageConfidence;
  }
  public double getGenderConfidence() {
    return genderConfidence;
  }
  public void setGenderConfidence(double genderConfidence) {
    this.genderConfidence = genderConfidence;
  }
  
}
