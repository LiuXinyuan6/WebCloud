package cn.cloud.service;


import cn.cloud.domain.CloudAdmin;
import cn.cloud.domain.CloudUser;

public interface LoginService  {
    public CloudAdmin adminLogin(String admin_name, String admin_pwd);
    public CloudUser login(String user_name, String user_pwd);
    public CloudUser queryByUserName(String user_name);
    void regist(CloudUser user);
    Boolean findUser(String user_name);
}

