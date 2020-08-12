package com.gh.blog.entity;


import java.io.Serializable;

public class Demo implements Serializable {

  private long id;
  private String name;
  private String sex;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("\"id\":")
            .append(id);
    sb.append(",\"name\":\"")
            .append(name).append('\"');
    sb.append(",\"sex\":\"")
            .append(sex).append('\"');
    sb.append('}');
    return sb.toString();
  }
}
