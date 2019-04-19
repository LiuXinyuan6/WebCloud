package cn.cloud.dao;

import cn.cloud.domain.CloudUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EditUserDao {
    @Update("update cloud_user set user_name=#{user_name},user_real_name=#{user_real_name},user_dept=#{user_dept},user_class=#{user_class},user_stu_id=#{user_stu_id} where user_id=#{user_id}")
    public void editUser(@Param("user_name") String user_name,
                         @Param("user_real_name") String user_real_name,
                         @Param("user_dept") String user_dept,
                         @Param("user_id") String user_id,
                         @Param("user_class") String user_class,
                         @Param("user_stu_id") Integer user_stu_id);
    @Select("select * from cloud_user where user_name = #{user_name} and user_pwd = #{user_pwd} ")
    CloudUser findPass(@Param("user_name") String user_name, @Param("user_pwd") String user_pwd);
    @Select("select user_id from cloud_user where user_name=#{user_name} ")
    Integer findUserAndId(@Param("user_name") String user_name);
    @Update("update cloud_user set user_pwd=#{user_pwd} where user_name=#{user_name}")
    public void reSetPwd(@Param("user_name") String user_name, @Param("user_pwd") String user_pwd);
}
