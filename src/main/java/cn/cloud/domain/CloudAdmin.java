package cn.cloud.domain;

import java.io.Serializable;

public class CloudAdmin implements Serializable {
    private String admin_name;
    private Integer admin_id;
    private String admin_pwd;
    private String admin_second_pwd;

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_pwd() {
        return admin_pwd;
    }

    public void setAdmin_pwd(String admin_pwd) {
        this.admin_pwd = admin_pwd;
    }

    public String getAdmin_second_pwd() {
        return admin_second_pwd;
    }

    public void setAdmin_second_pwd(String admin_second_pwd) {
        this.admin_second_pwd = admin_second_pwd;
    }

    @Override
    public String toString() {
        return "CloudAdmin{" +
                "admin_name='" + admin_name + '\'' +
                ", admin_id=" + admin_id +
                ", admin_pwd='" + admin_pwd + '\'' +
                ", admin_second_pwd='" + admin_second_pwd + '\'' +
                '}';
    }
}
