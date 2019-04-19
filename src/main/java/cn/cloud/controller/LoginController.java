package cn.cloud.controller;

import cn.cloud.domain.CloudAdmin;
import cn.cloud.domain.CloudUser;
import cn.cloud.service.LoginService;
import cn.cloud.util.SecurityCode;
import cn.cloud.util.SecurityImage;
import cn.cloud.util.TimerTaskTest;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * 登录 注册 模块
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;



    /**
     * 用户登录
     * @param user_name 用户名 必须
     * @param user_pwd  用户密码 必须
     * @param code      验证码   必须
     * @param session   处理验证码 以及判断用户登录状态
     * @param model     将数据返回到页面
     * @return
     */
    @Logger
    @RequestMapping(value = "/user",produces = "application/json; charset=utf-8")
    public String user(String user_name, String user_pwd, String code, HttpSession session, Model model){
        CloudUser user=loginService.login(user_name,user_pwd);
        String severCode= (String) session.getAttribute("serverCode");
        if (StringUtils.isEmpty(severCode)){
            return "user/user_login";
        }
        if(severCode.equalsIgnoreCase(code)) {
            if (user != null) {
                //session.getServletContext()得到时application对象
                ServletContext application=session.getServletContext();
                //得到存储session信息的map集合
                Map<String, String> loginMap =(Map<String, String>)application.getAttribute("loginMap");
                //为用户的session在线时间申请一个时间的对象
                Timer timer=new Timer();
                if(loginMap==null){//如果map集合为空则申请一个新的空间
                    loginMap = new HashMap<String,String>();
                }

                for(String key:loginMap.keySet()) {//遍历map的key值
                    if (user.getUser_name().equals(key)) {//两个if嵌套判断用户名和session id与session里的是否相同
                        timer.schedule(new TimerTaskTest(user.getUser_name(),loginMap),1800000);//在这里设置用户可在线的时间长度
                        if(session.getId().equals(loginMap.get(key))) {//如果登录的session的id和map集合里的一致则判断同一地点登陆
                            System.out.println(user_name+"在同一地点多次登录！");
                            session.setAttribute("UserLogin",user);
                            session.setAttribute("user_name",user.getUser_name());
                            return "redirect:/homepage";
                        }else{//否则为异地登录
                            System.out.println(user_name+"异地登录被拒绝！");
                            model.addAttribute("errorMessage", "该用户已经在异地登录！");
                            return "user/user_login";
                        }
                    }
                }
                loginMap.put(user.getUser_name(),session.getId());//把当前session信息存入map集合
                application.setAttribute("loginMap", loginMap);
                session.setAttribute("UserLogin",user);
                session.setAttribute("user_name",user.getUser_name());
                return "redirect:/homepage2";


//                return "index";
            } else {
                model.addAttribute("errorMessage", "用户名密码错误！");
                return "user/user_login";
            }
        }else {
            model.addAttribute("codeMessage","验证码错误！");
            return "user/user_login";
        }
    }
    @RequestMapping("/toindex")
    public String toindex(HttpSession session, Model model){
        CloudUser user= (CloudUser) session.getAttribute("UserLogin");
        if (user==null){
            return "redirect:/homepage";
        }
        model.addAttribute("user",user);
        return "redirect:/homepage2";

    }

    /**
     * 创建验证码
     *    依赖于util下
     *              SecurityCode和SecurityImage两个类
     * @param session   将验证码放入到session
     * @param response  将图片发送至网页
     * @throws Exception
     */
    @RequestMapping("/createCode")
    public void createCode(HttpSession session, HttpServletResponse response)throws Exception{
        String securityCode=SecurityCode.getSecurityCode();
        session.setAttribute("serverCode",securityCode);
        BufferedImage image=SecurityImage.createImage(securityCode);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"png",out);

    }

    /**
     * 用户注册页面(非管理员）（管理员只能由root级别的管理员在后台添加)
     *      用户分两种
     *          1.普通用户
     *              即不是本社成员注册(可能不是本校)，此情况没有学院或者学号
     *          2.社团成员
     *              由管理员添加(有待改进)
     * @param user_name  昵称 登录凭证 前台要矫正数据格式以及查重
     * @param user_pwd     密码  16位 限制密码的格式 前台限制
     * @param user_stu_id   学号  非必须
     * @param user_real_name 真实姓名 非必须
     * @param user_dept       学院    非必须
     * @param user_class      班级  非必须
     * @param user_status     用户级别
     *                              0 普通用户
     *                              1  社团成员
     * @return
     */
    @RequestMapping("/register")
    public String regist(@RequestParam(value = "user_name",required =true)String user_name,
                         @RequestParam(value = "user_pwd",required =true)String user_pwd,
                         @RequestParam(value = "user_stu_id",defaultValue = "error")Integer user_stu_id,
                         @RequestParam(value = "user_real_name",defaultValue = "error")String user_real_name,
                         @RequestParam(value = "user_dept",defaultValue = "error")String user_dept,
                         @RequestParam(value = "user_class",defaultValue = "error")String user_class,
                         @RequestParam(value = "user_status",defaultValue = "0")Integer user_status,Model model){
        CloudUser user=new CloudUser(user_name,user_pwd,user_stu_id,user_real_name,user_dept,user_class,user_status);
        if (user_name.isEmpty()){
            model.addAttribute("repeatMessage","用户名不能为空！");
            return "/user/regist";
        }else
        if (loginService.queryByUserName(user_name)!=null){
            //如果有对象返回说明数据库中该用户名已存在，需要向页面发送提示信息
            model.addAttribute("repeatMessage","用户名已存在，勿重复！");
            return "/user/regist";
        }else if (user_pwd.isEmpty()){

            model.addAttribute("repeatMessage","密码不能为空！");
            return "/user/regist";

        }else {
            loginService.regist(user);
            return "redirect:/login/user/toLogin";
        }
    }




    /**
     * 管理员登录
     * @param admin_name  管理员账号
     * @param admin_pwd   管理员密码
     * @param model
     * @return
     */
    @Logger
    @RequestMapping("/admin")
    public String adminLogin(String admin_name,String admin_pwd,String code, Model model, HttpSession session) {
        CloudAdmin admin = loginService.adminLogin(admin_name, admin_pwd);
        String severCode = (String) session.getAttribute("serverCode");
        if (StringUtils.isEmpty(severCode)) {
            return "/admin/admin_login";
        }
        if (severCode.equalsIgnoreCase(code)) {
            if (admin != null) {
                session.setAttribute("loginAdmin", admin);
                return "redirect:/admin/index";
            } else {
                model.addAttribute("errorMessage", "用户名或密码错误！");
            }

        }else{
            model.addAttribute("codeMessage", "验证码错误！");

        }
        return "/admin/admin_login";
    }
    /**
     * 用于管理员注销功能
     * @param request 用于获取session，移除登录状态
     * @return  返回到管理员登录界面
     */
    @RequestMapping("adminLogOut")
    public String AdminLogOut(HttpServletRequest request){
        request.getSession().removeAttribute("login");
        return "redirect:/login/admin/toLogin";
    }

    /**
     * 用于清除用户存储session的map集合
     * @param word
     * @param session
     */
    @RequestMapping(value = "/clear",produces = "application/json; charset=utf-8")
    public void clear(String word,HttpSession session){//clear方法接收ajax传递的参数,改参数是判断浏览器窗口是否关闭
        CloudUser user= (CloudUser) session.getAttribute("UserLogin");
        ServletContext application=session.getServletContext();
        Map<String, String> loginMap =(Map<String, String>)application.getAttribute("loginMap");
        if ("close".equals(word)){//如果关闭,则清空当前map集合里相对的session信息
            loginMap.remove(user.getUser_name());

        }


    }
}
