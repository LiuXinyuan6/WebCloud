package cn.cloud.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * 文件上传下载实体类
 */
public class CloudResourceFile implements Serializable {
    private Integer srcf_id; //文件ID
    private String srcf_name; //文件名称
    private String srcf_type;//文件类型
    private String srcf_src; //文件存储路径
    private Integer srcf_size; //文件大小
    private Date srcf_date; //文件上传日期
    private String srcf_status; //文件状态
    private String srcf_describe; //文件描述
    private CloudUser clouduser;//用户信息

    @Override
    public String toString() {
        return "CloudResourceFile{" +
                "srcf_id=" + srcf_id +
                ", srcf_name='" + srcf_name + '\'' +
                ", srcf_type='" + srcf_type + '\'' +
                ", srcf_src='" + srcf_src + '\'' +
                ", srcf_size=" + srcf_size +
                ", srcf_date=" + srcf_date +
                ", srcf_status='" + srcf_status + '\'' +
                ", srcf_describe='" + srcf_describe + '\'' +
                ", clouduser=" + clouduser +
                '}';
    }

    public Integer getSrcf_id() {
        return srcf_id;
    }

    public void setSrcf_id(Integer srcf_id) {
        this.srcf_id = srcf_id;
    }

    public String getSrcf_name() {
        return srcf_name;
    }

    public void setSrcf_name(String srcf_name) {
        this.srcf_name = srcf_name;
    }

    public String getSrcf_type() {
        return srcf_type;
    }

    public void setSrcf_type(String srcf_type) {
        this.srcf_type = srcf_type;
    }

    public String getSrcf_src() {
        return srcf_src;
    }

    public void setSrcf_src(String srcf_src) {
        this.srcf_src = srcf_src;
    }

    public Integer getSrcf_size() {
        return srcf_size;
    }

    public void setSrcf_size(Integer srcf_size) {
        this.srcf_size = srcf_size;
    }

    public Date getSrcf_date() {
        return srcf_date;
    }

    public void setSrcf_date(Date srcf_date) {
        this.srcf_date = srcf_date;
    }

    public String getSrcf_status() {
        return srcf_status;
    }

    public void setSrcf_status(String srcf_status) {
        this.srcf_status = srcf_status;
    }

    public String getSrcf_describe() {
        return srcf_describe;
    }

    public void setSrcf_describe(String srcf_describe) {
        this.srcf_describe = srcf_describe;
    }

    public CloudUser getClouduser() {
        return clouduser;
    }

    public void setClouduser(CloudUser clouduser) {
        this.clouduser = clouduser;
    }
}
