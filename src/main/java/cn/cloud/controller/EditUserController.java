package cn.cloud.controller;

import cn.cloud.domain.CloudUser;
import cn.cloud.service.EditUserService;
import cn.cloud.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("/edit")
public class EditUserController {
    @Autowired

    EditUserService service;
    @Autowired
    LoginService loginService;
    @RequestMapping("/user")
    public String editUser(@RequestParam(value = "user_name",required =true)String user_name,
                           @RequestParam(value = "user_id",required =true)String user_id,
                           @RequestParam(value = "user_stu_id",defaultValue = "error")Integer user_stu_id,
                           @RequestParam(value = "user_real_name",defaultValue = "error")String user_real_name,
                           @RequestParam(value = "user_dept",defaultValue = "error")String user_dept,
                           @RequestParam(value = "user_class",defaultValue = "error")String user_class, HttpSession session,Model model){
        boolean tof=loginService.findUser(user_name);
        boolean to=service.findUserAndId(user_name,user_id);
        if (user_name.isEmpty()){
            System.out.println("用户名不能为空");
            model.addAttribute("repeatMessage","用户名不能为空！");
            return "/user/test";
        }else if (tof!=true&&to!=true){
            //如果有对象返回说明数据库中该用户名已存在，需要向页面发送提示信息
            System.out.println("用户名已存在，勿重复！");
            model.addAttribute("repeatMessage","用户名已存在，勿重复！");
            return "/user/test";
        }else {
            System.out.println(user_class+user_dept+user_id);
            service.editUser(user_name,user_real_name,user_dept,user_id,user_class,user_stu_id);
            CloudUser user= loginService.queryByUserName(user_name);
            session.setAttribute("UserLogin",user);
            return "/user/test";
        }



    }

    @RequestMapping(value = "/findPass", produces = "application/json; charset=utf-8" )
    public Map<String,Object> findPass(String user_name,String user_pwd,HttpSession session){
        Map<String,Object> resultMap = new HashMap<String, Object>();
        boolean to=service.findPass(user_name,user_pwd);
        if(to==false){
            resultMap.put("result","notsuccess");
            return resultMap;
        }else{
            resultMap.put("result",session.getAttribute("user_name"));//session.getAttribute("user_name")
            return resultMap;
        }
    }


    @RequestMapping(value = "/reSetPwd")
    public String reSetPwd(String user_name, String user_pwd, Model model,HttpSession session){
        if (user_pwd.isEmpty()){

            model.addAttribute("reSetMessage","新密码不能为空！");
            return "/user/reWorld";
        }else {
            service.reSetPwd(user_name,user_pwd);
            session.removeAttribute("UserLogin");
            return "forward:/user/toLogin";
        }



    }
    @RequestMapping("/myspace")
    public String myspace(){
        return "user/myspace";
    }
    @RequestMapping("/reWorld")
    public String reWorld(){
        return "user/reWorld";
    }
    @RequestMapping("/test")
    public String test(){
        return "user/test";
    }

    @RequestMapping("/quit")
    public String quit(HttpSession session, HttpServletRequest request){

        CloudUser user= (CloudUser) session.getAttribute("UserLogin");
        ServletContext application=session.getServletContext();
        Map<String, String> loginMap =(Map<String, String>)application.getAttribute("loginMap");

            loginMap.remove(user.getUser_name());

        session.removeAttribute("UserLogin");
        return "redirect:/homepage";
    }
}