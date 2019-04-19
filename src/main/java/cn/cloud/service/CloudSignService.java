package cn.cloud.service;

import cn.cloud.domain.CloudSign;

import java.util.List;
import java.util.Map;

/**
 * 签到模块 servic层
 */
public interface CloudSignService {
    public List<CloudSign> findMonthSign(String user_id);//根据用户user_id 遍历用户签到情况

    public void  addSign(CloudSign cloudSign);//向数据库添加签到信息

    public  CloudSign judgeSign(String user_id, String sign_status);//判断该用户今天是否签到

    public List<Map<String,Object>> exportSign(String firstTime, String lastTime);
    public List<Map<String,Object>> showMonthChart( String firstTime, String lastTime);
}
