package cn.cloud.domain;

import java.util.Date;

public class CloudSign {
    private String user_id;
    private Date sign_date;
    private String sign_status;

    public CloudSign(String user_id, Date sign_date, String sign_status) {
        this.user_id = user_id;
        this.sign_date = sign_date;
        this.sign_status = sign_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getSign_date() {
        return sign_date;
    }

    public void setSign_date(Date sign_date) {
        this.sign_date = sign_date;
    }

    public String getSign_status() {
        return sign_status;
    }

    public void setSign_status(String sign_status) {
        this.sign_status = sign_status;
    }


    public CloudSign() {
        super();
    }

    @Override
    public String toString() {
        return "CloudSign{" +
                "user_id='" + user_id + '\'' +
                ", sign_date=" + sign_date +
                ", sign_status='" + sign_status + '\'' +
                '}';
    }
}
