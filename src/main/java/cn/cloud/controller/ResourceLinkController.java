package cn.cloud.controller;

import cn.cloud.domain.CloudAdmin;
import cn.cloud.domain.CloudResourceLink;
import cn.cloud.domain.CloudUser;
import cn.cloud.service.CloudResourceLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 资源连接模块
 */
@Controller
@RequestMapping(value = "/rsl")
public class ResourceLinkController {

    @Autowired
    CloudResourceLinkService cloudResourceLinkService;

    @InitBinder//日期需要进行格式修改
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     *上传资源链接
     * 失败返回原地址从新输入
     * 成功则跳转到显示页面
     */
    @RequestMapping("/addLink")
    public ModelAndView addLink(String srcl_name, String srcl_link, String srcl_pwd, String srcl_type, Integer user_id, Integer srcl_status, Date srcl_date, HttpSession session,ModelAndView view){
        System.out.println(user_id);
        Date date = new Date();
        try{
            if (cloudResourceLinkService.searchLinkByLink(srcl_link)!=null){                        //查重
                view.addObject("errorLink", "不要重复上传");
                view.setViewName("resourcesLink/add_link");
            }else{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            CloudResourceLink cloudResourceLink=new CloudResourceLink(srcl_name,srcl_link,srcl_pwd,srcl_type,user_id,0,sdf.parse(df.format(date)));
            cloudResourceLinkService.addLink(cloudResourceLink);                                   //添加资源链接
            view.setViewName("redirect:/rsl/searchAll");
            }
        }catch (Exception e){
                throw new RuntimeException(e);

        }
        return view;
    }
    /**
     * 查询及显示链接资源
     */
    @RequestMapping("/searchAll")
    public String searchAll(Model model,HttpSession session){

        CloudUser user= (CloudUser) session.getAttribute("UserLogin");
        Integer user_id=user.getUser_id();
        List<CloudResourceLink> list=cloudResourceLinkService.show_per_link(user_id);               //根据用户ID查询出对应的所有上传链接
        model.addAttribute("Link",list);
        List<CloudResourceLink> cloudResourceLinks = cloudResourceLinkService.searchLinkAll();      //查询出所有链接
        model.addAttribute("Links",cloudResourceLinks);
        return "resourcesLink/show_link";
    }
    /**
     * 用户删除资源链接
     *
     */
    @RequestMapping("/deleteLink")
    public ModelAndView deleteLink(String srcl_id,ModelAndView view){
        cloudResourceLinkService.deleteLink(srcl_id);                                               //根据ID删除链接
        view.setViewName("redirect:/rsl/searchAll");
        return  view;

    }
    /**
     * 管理员管理资源
     *
     */@RequestMapping("admin/manage")
    public String manageLink(Model model){
        List<CloudResourceLink> list=  cloudResourceLinkService.searchLink();                       //查询未通过审核的资源
        model.addAttribute("list1",list);
        List<CloudResourceLink> MResourceLinks=  cloudResourceLinkService.searchLinkAll();          //显示通过审核的资源链接
        model.addAttribute("list2",MResourceLinks);
        return "admin/admin_managelink";

    }

    /**
     * 管理员审核资源
     *
     */
    @RequestMapping("admin/update")
    public String updateLink(Integer srcl_id){
        cloudResourceLinkService.updateLink(srcl_id);                                               //将链接状态改为1
        return "redirect:/rsl/admin/manage";
    }



    /**
     * 管理员删除资源链接
     *
     */
    @RequestMapping("admin/delete")
    public ModelAndView delete(String srcl_id,ModelAndView view){

        cloudResourceLinkService.deleteLink(srcl_id);                                               //根据ID删除资源链接
        view.setViewName("redirect:/rsl/admin/manage");
        return view;
    }
}
