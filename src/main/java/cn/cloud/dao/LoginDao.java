package cn.cloud.dao;

import cn.cloud.domain.CloudAdmin;
import cn.cloud.domain.CloudUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface LoginDao {
    @Select("select * from cloud_admin where admin_name = #{admin_name}")
    public CloudAdmin getCloudAdminInfoById(String id);
    @Select("select user_name from cloud_user where id=1")
    public String getUserName();
    @Select("select * from cloud_user where user_name =#{user_name} and user_pwd=#{user_pwd}")
    public CloudUser login(@Param("user_name") String user_name, @Param("user_pwd") String user_pwd);
    @Insert(" insert into cloud_user(user_name,user_pwd,user_stu_id,user_real_name,user_dept,user_class,user_status) values(#{user_name},#{user_pwd},#{user_stu_id},#{user_real_name},#{user_dept},#{user_class},#{user_status})")
    public void regist(CloudUser user);
    @Select("select * from cloud_admin where admin_name=#{admin_name} and admin_pwd=#{admin_pwd}")
    public CloudAdmin adminLogin(@Param("admin_name") String admin_name, @Param("admin_pwd") String admin_pwd);
    @Select("select * from cloud_user where user_name=#{user_name}")
    public CloudUser queryByUserName(String user_name);
    @Select("select * from cloud_user where user_name = #{user_name}")
    CloudUser findUser(String user_name);
}