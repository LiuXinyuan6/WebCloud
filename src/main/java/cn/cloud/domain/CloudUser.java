package cn.cloud.domain;

import java.io.Serializable;

public class CloudUser implements Serializable {
    private Integer user_id;
    private String user_name;
    private String user_pwd;
    private Integer user_stu_id;
    private String user_real_name;
    private String user_dept;
    private String user_class;
    private Integer user_status;

    public CloudUser(String user_name, String user_pwd, Integer user_stu_id, String user_real_name, String user_dept, String user_class, Integer user_status) {
        this.user_name = user_name;
        this.user_pwd = user_pwd;
        this.user_stu_id = user_stu_id;
        this.user_real_name = user_real_name;
        this.user_dept = user_dept;
        this.user_class = user_class;
        this.user_status = user_status;
    }

    @Override
    public String toString() {
        return "CloudUser{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_pwd='" + user_pwd + '\'' +
                ", user_stu_id=" + user_stu_id +
                ", user_real_name='" + user_real_name + '\'' +
                ", user_dept='" + user_dept + '\'' +
                ", user_class='" + user_class + '\'' +
                ", user_status=" + user_status +
                '}';
    }

    public CloudUser() {
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public Integer getUser_stu_id() {
        return user_stu_id;
    }

    public void setUser_stu_id(Integer user_stu_id) {
        this.user_stu_id = user_stu_id;
    }

    public String getUser_real_name() {
        return user_real_name;
    }

    public void setUser_real_name(String user_real_name) {
        this.user_real_name = user_real_name;
    }

    public String getUser_dept() {
        return user_dept;
    }

    public void setUser_dept(String user_dept) {
        this.user_dept = user_dept;
    }

    public String getUser_class() {
        return user_class;
    }

    public void setUser_class(String user_class) {
        this.user_class = user_class;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }
}
