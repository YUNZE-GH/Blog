package com.gh.blog.entity;


import java.io.Serializable;

public class VerificationCode implements Serializable {

  private long id;
  private String oid;
  private String phone;
  private String email;
  private String verification;
  private String createtime;
  private String bizid;
  private String code;
  private String message;
  private String requestid;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getVerification() {
    return verification;
  }

  public void setVerification(String verification) {
    this.verification = verification;
  }


  public String getCreatetime() {
    return createtime;
  }

  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }


  public String getBizid() {
    return bizid;
  }

  public void setBizid(String bizid) {
    this.bizid = bizid;
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  public String getRequestid() {
    return requestid;
  }

  public void setRequestid(String requestid) {
    this.requestid = requestid;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("\"id\":")
            .append(id);
    sb.append(",\"oid\":\"")
            .append(oid).append('\"');
    sb.append(",\"phone\":\"")
            .append(phone).append('\"');
    sb.append(",\"email\":\"")
            .append(email).append('\"');
    sb.append(",\"verification\":\"")
            .append(verification).append('\"');
    sb.append(",\"createtime\":\"")
            .append(createtime).append('\"');
    sb.append(",\"bizid\":\"")
            .append(bizid).append('\"');
    sb.append(",\"code\":\"")
            .append(code).append('\"');
    sb.append(",\"message\":\"")
            .append(message).append('\"');
    sb.append(",\"requestid\":\"")
            .append(requestid).append('\"');
    sb.append('}');
    return sb.toString();
  }
}
