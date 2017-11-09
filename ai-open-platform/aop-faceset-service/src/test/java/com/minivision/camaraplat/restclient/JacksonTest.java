package com.minivision.camaraplat.restclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minivision.camaraplat.restclient.JacksonTest.User.Name;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JacksonTest {

  @Test
  public void testJsonView() throws JsonProcessingException{
    ObjectMapper oMapper = new ObjectMapper();
    oMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
    String res = oMapper.writerWithView(null).writeValueAsString(new User(new Name("name","last"), "pwd"));
    System.out.println(res);
  }
  
  
  public static class User{
    public User(Name name, String pwd) {
      this.name = name;
      this.pwd = pwd;
    }
    public static class Name{
      private String first;
      private String last;
      
      public Name(String first, String last) {
        this.first = first;
        this.last = last;
      }
      public String getFirst() {
        return first;
      }
      public void setFirst(String first) {
        this.first = first;
      }
      public String getLast() {
        return last;
      }
      public void setLast(String last) {
        this.last = last;
      }
    }
    @JsonView(UserView.class)
    private Name name;
    private String pwd;
    public Name getName() {
      return name;
    }
    public void setName(Name name) {
      this.name = name;
    }
    public String getPwd() {
      return pwd;
    }
    public void setPwd(String pwd) {
      this.pwd = pwd;
    }
  }
  
  public interface UserView{};
}
