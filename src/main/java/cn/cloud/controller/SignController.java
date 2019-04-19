package cn.cloud.controller;

import cn.cloud.domain.CloudSign;
import cn.cloud.service.CloudSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 签到模块
 */
@Controller
@RequestMapping("sign")
public class SignController {
    @Autowired
    CloudSignService cloudSignService;

    /**
     * addSign签到方法
     * 接收前端界面传过来的user_id,如何通过user_id和当前是几号进行判断用户是否已经签到
     */
    @RequestMapping("/addSign")
    public ModelAndView addSign(String user_id, ModelAndView view,HttpSession session) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd ");//用于切割时间，获取几天是几号
        String[] date1 = sdf1.format(date).split("-");//分割日期获取今天是几号
        session.setAttribute("user_id",user_id);
        try {
            System.out.println(user_id + " " + date1[2]);//date1[2]，其值就是今天是几号
            CloudSign cloudSign = new CloudSign(user_id, sdf.parse(df.format(date)), date1[2]);
            //先判断该用户是否已经签到过 ,通过user_id，和sign_status进行判断，每天签到一次
            CloudSign sign = cloudSignService.judgeSign(user_id, date1[2]);//根据返回的CloudSign 对象判断用户是否已经完成签到
            if (sign == null) {
                cloudSignService.addSign(cloudSign);
                view.setViewName("/sign/showSign");
            } else {
                System.out.println("不要重复签到");
                view.addObject("error", "今天已经签到过");
                view.setViewName("/sign/sign");
            }

        } catch (Exception e) {
            new RuntimeException(e);
        }
        return view;
    }




}
