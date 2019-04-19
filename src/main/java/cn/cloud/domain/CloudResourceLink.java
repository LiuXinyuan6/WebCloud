package cn.cloud.domain;

import java.io.Serializable;
import java.util.Date;

public class CloudResourceLink implements Serializable {
    private Integer srcl_id;
    private String  srcl_name;
    private String  srcl_link;
    private String  srcl_pwd;
    private String  srcl_type;
    private Integer user_id;
    private Integer srcl_status;
    private Date    srcl_date;

    public CloudResourceLink() {
        super();
    }

    public CloudResourceLink(String srcl_name, String srcl_link, String srcl_pwd, String srcl_type, Integer user_id, Integer srcl_status, Date srcl_date) {
        this.srcl_name = srcl_name;
        this.srcl_link = srcl_link;
        this.srcl_pwd = srcl_pwd;
        this.srcl_type = srcl_type;
        this.user_id = user_id;
        this.srcl_status = srcl_status;
        this.srcl_date = srcl_date;
    }

    public CloudResourceLink(Integer srcl_id, String srcl_name, String srcl_link, String srcl_pwd, String srcl_type, Integer user_id, Integer srcl_status, Date srcl_date) {
        this.srcl_id = srcl_id;
        this.srcl_name = srcl_name;
        this.srcl_link = srcl_link;
        this.srcl_pwd = srcl_pwd;
        this.srcl_type = srcl_type;
        this.user_id = user_id;
        this.srcl_status = srcl_status;
        this.srcl_date = srcl_date;
    }

    public Integer getSrcl_id() {
        return srcl_id;
    }

    public void setSrcl_id(Integer srcl_id) {
        this.srcl_id = srcl_id;
    }

    public String getSrcl_name() {
        return srcl_name;
    }

    public void setSrcl_name(String srcl_name) {
        this.srcl_name = srcl_name;
    }

    public String getSrcl_link() {
        return srcl_link;
    }

    public void setSrcl_link(String srcl_link) {
        this.srcl_link = srcl_link;
    }

    public String getSrcl_pwd() {
        return srcl_pwd;
    }

    public void setSrcl_pwd(String srcl_pwd) {
        this.srcl_pwd = srcl_pwd;
    }

    public String getSrcl_type() {
        return srcl_type;
    }

    public void setSrcl_type(String srcl_type) {
        this.srcl_type = srcl_type;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getSrcl_status() {
        return srcl_status;
    }

    public void setSrcl_status(Integer srcl_status) {
        this.srcl_status = srcl_status;
    }

    public Date getSrcl_date() {
        return srcl_date;
    }

    public void setSrcl_date(Date srcl_date) {
        this.srcl_date = srcl_date;
    }
}
