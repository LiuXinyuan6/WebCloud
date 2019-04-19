package cn.cloud.dao;

import cn.cloud.domain.CloudSign;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CloudSignDao {
    public List<CloudSign> findMonthSign(String user_id);//根据用户user_id 遍历用户签到情况

    public void addSign(CloudSign cloudSign);//向数据库添加签到信息

    public CloudSign judgeSign(@Param("user_id") String user_id, @Param("sign_status") String sign_status);//判断该用户今天是否签到

    public List<Map<String,Object>> exportSign(@Param("firstTime") String firstTime, @Param("lastTime") String lastTime);
    public List<Map<String,Object>> showMonthChart(@Param("firstTime") String firstTime,@Param("lastTime") String lastTime);
}
