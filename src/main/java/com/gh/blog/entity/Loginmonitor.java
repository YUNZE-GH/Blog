package com.gh.blog.entity;


import java.io.Serializable;

public class Loginmonitor implements Serializable {

  private long id;
  private String ip;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("\"id\":")
            .append(id);
    sb.append(",\"ip\":\"")
            .append(ip).append('\"');
    sb.append('}');
    return sb.toString();
  }
}
