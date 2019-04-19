package cn.cloud.service;

public interface EditUserService {
    public void editUser(String user_name,
                         String user_real_name,
                         String user_dept,
                         String user_id,
                         String user_class,
                         Integer user_stu_id);
    Boolean findPass(String user_name, String user_pwd);
    Boolean findUserAndId(String user_name, String user_id);
    public void reSetPwd(String user_name, String user_pwd);
}
