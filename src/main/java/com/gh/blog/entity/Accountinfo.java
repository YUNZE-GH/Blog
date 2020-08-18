package com.gh.blog.entity;


import java.io.Serializable;

public class Accountinfo implements Serializable {

    private long id;
    private String oid;
    private String username;
    private String phone;
    private String password;
    private String email;
    private long lockflag;
    private long failurenum;
    private String createtime;
    private String updatetime;
    private String finallytime;
    private long accountState;
    private String canceltime;


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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public long getLockflag() {
        return lockflag;
    }

    public void setLockflag(long lockflag) {
        this.lockflag = lockflag;
    }


    public long getFailurenum() {
        return failurenum;
    }

    public void setFailurenum(long failurenum) {
        this.failurenum = failurenum;
    }


    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }


    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }


    public String getFinallytime() {
        return finallytime;
    }

    public void setFinallytime(String finallytime) {
        this.finallytime = finallytime;
    }


    public long getAccountState() {
        return accountState;
    }

    public void setAccountState(long accountState) {
        this.accountState = accountState;
    }


    public String getCanceltime() {
        return canceltime;
    }

    public void setCanceltime(String canceltime) {
        this.canceltime = canceltime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"oid\":\"")
                .append(oid).append('\"');
        sb.append(",\"username\":\"")
                .append(username).append('\"');
        sb.append(",\"phone\":\"")
                .append(phone).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append(",\"email\":\"")
                .append(email).append('\"');
        sb.append(",\"lockflag\":")
                .append(lockflag);
        sb.append(",\"failurenum\":")
                .append(failurenum);
        sb.append(",\"createtime\":\"")
                .append(createtime).append('\"');
        sb.append(",\"updatetime\":\"")
                .append(updatetime).append('\"');
        sb.append(",\"finallytime\":\"")
                .append(finallytime).append('\"');
        sb.append(",\"accountState\":")
                .append(accountState);
        sb.append(",\"canceltime\":\"")
                .append(canceltime).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
