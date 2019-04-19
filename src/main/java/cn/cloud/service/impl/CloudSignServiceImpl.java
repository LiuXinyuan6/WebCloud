package cn.cloud.service.impl;

import cn.cloud.dao.CloudSignDao;
import cn.cloud.domain.CloudSign;
import cn.cloud.service.CloudSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CloudSignServiceImpl implements CloudSignService {
    @Autowired
    private CloudSignDao cloudSignDao;
    @Override
    public List<Map<String,Object>> showMonthChart(String firstTime,String lastTime) {
        return cloudSignDao.showMonthChart(firstTime,lastTime);
    }


    public void addSign(CloudSign cloudSign) {//用户签到
        //判断用户今天是否签到
        System.out.println(cloudSign);
        cloudSignDao.addSign(cloudSign);//接收Controller的数据进行存储


    }

    @Override
    public CloudSign judgeSign(String user_id, String sign_status) {

        CloudSign cloudSign = cloudSignDao.judgeSign(user_id, sign_status);
        return cloudSign;
    }


    public List<CloudSign> findMonthSign(String user_id) {//获取数据库的签到数据----》用FullCalendar插件存储
        List list1 = new ArrayList();//定义List集合，用于后面存一个map集合
        List<CloudSign> list = cloudSignDao.findMonthSign(user_id);//获取数据库数据,查询use_id=171的成员签到情况
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        for (CloudSign s : list
                ) {

            Map<String, Object> map = new HashMap<String, Object>();//定义map集合
            map.put("title", df2.format(s.getSign_date()));
            map.put("start", df1.format(s.getSign_date()));

            list1.add(map);

        }
        return list1;
    }
    /**
     * 按照月份进行查询
     * @param firstTime 按照月份进行导出,月初
     * @param  lastTime 按照月份进行导出，月末，即下个月的月初
     * @return  返回为map键值对类型数据，方便json
     */
    @Override
    public List<Map<String,Object>> exportSign(String firstTime,String lastTime) {
        return cloudSignDao.exportSign(firstTime,lastTime);
    }
}
