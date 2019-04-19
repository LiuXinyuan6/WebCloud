package cn.cloud.controller;


import cn.cloud.domain.CloudResourceFile;
import cn.cloud.domain.CloudUser;

import cn.cloud.domain.Msg;
import cn.cloud.service.FileResourcesService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


/**
 * 文件资源模块
 */
@Controller
@RequestMapping(value = "/rsf")
public class FileResourcesController {
@Autowired
    FileResourcesService fileResourcesService;
    @RequestMapping(value = "/download/{id}",method = RequestMethod.GET)
    public void downLoad(HttpServletRequest request, HttpServletResponse response, @PathVariable("id")Integer id){
        CloudUser user = (CloudUser)request.getSession(false).getAttribute("UserLogin");
        FileInputStream bis=null;
        BufferedOutputStream outputStream=null;
        try {
            CloudResourceFile cloudResourceFile  = fileResourcesService.findFileById(id);
            String path = request.getSession().getServletContext().getRealPath(cloudResourceFile.getSrcf_src());// 转换为utf-8格式 file路径才可以找到);
            bis = new FileInputStream(new File(path));

            response.setHeader("Content-Disposition", "attachment;filename=" + new String(FilenameUtils.getName(cloudResourceFile .getSrcf_src()).getBytes(),"ISO8859-1"));
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType(request.getSession().getServletContext().getMimeType(path));
            //设置转码格式为utf-8
            response.setCharacterEncoding("UTF-8");
            //获得文件输出流
            outputStream = new BufferedOutputStream(response.getOutputStream());

            byte[] bytes= new byte[4096];
            int len = 0;
            while((len = bis.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.close();
            bis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (bis!=null){
                    bis.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * 处理文件上传
     *
     * @param request
     * @param files   将上传的文件封装至MultipartFile数组中
     * @return
     */
    @RequestMapping("/upload/file")
    public String uploadPic(HttpServletRequest request, MultipartFile[] files,String describe) {
        //设置路径
        if (files.length==0){
            return "redirect:/rsf/upload";
        }
        CloudUser user = (CloudUser) request.getSession(false).getAttribute("UserLogin");
        if (user == null) {
            return "redirect:/login/user/toLogin";
        }

        for (MultipartFile file : files) {
            Boolean judge = fileResourcesService.encapsulationDataOfFileMSG(request, file, user);
            if (judge) {
                continue;
            } else {
                return "redirect:/homepage";
            }
        }

        return "redirect:/homepage";//没有页面
    }




    //后台管理层


    /**
     * 封装分页数据(以json格式)
     * @param pn
     * @return
     */
    @RequestMapping("/admin/FileManagePaging")
    @ResponseBody
    public Msg getInformationWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn){

        //在查询前只需要调用，传入页码以及每页显示多少条数据
        PageHelper.startPage(pn,5);
        //startPage后面跟的查询就是一个分页查询
        List<CloudResourceFile> informationList = fileResourcesService.getAllOfInformation();
        System.out.println(informationList);
        //使用pageInfo包装查询后的结果,将pageInfo交个页面即可
        PageInfo page = new PageInfo(informationList,5);//包含了详细的分页信息，包括查询出来的数据(information),连续显示的页数(5)
        return Msg.success().add("pageInfo",page);
    }

    /**
     *根据数据id查询相应的数据返回并在模态框(点击审核弹出)中显示
     * @param id
     * @return
     */
    @RequestMapping(value = "/admin/getInformationById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getInformationById(@PathVariable("id")Integer id){
        CloudResourceFile cloudResourceFile = fileResourcesService.getOneInformation(id);

        return Msg.success().add("information",cloudResourceFile);
    }

    /**
     * 更新通过审核的资源文件的审核状态
     * @param id
     * @return
     */
    @RequestMapping(value = "/admin/passcheck/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg passCheck(@PathVariable("id") Integer id){
        fileResourcesService.updateStatus(id);
        Msg msg = Msg.success();
        //System.out.println(msg);
        return msg;
    }

    /**
     * 删除资源文件
     * 1.单条删除没有通过审核的资源文件(1,2)
     * 2.批量删除已经失效或者不在共享的资源文件(1-2-3)
     * 注意：批量删除待优化
     * @param ids
     * @return
     */
    @RequestMapping(value = "/admin/deleteoneresource/{ids}",method = RequestMethod.GET)
    @ResponseBody
    public Msg deleteOneResource(@PathVariable("ids")String ids){

        //判断是批量删除还是单个删除 如果ids中含有"-"是批量删除
        if(ids.contains("-")){
            //如果是批量删除就先把传过来的ids字符串分割成单个的String类型的id放到数组中
            String[] str_ids = ids.split("-");
            for (String id : str_ids){
                Integer deId = Integer.parseInt(id);
                fileResourcesService.deleteOneResource(deId);
            }

        }else {
            //如果不是批量删除就直接把ids转换成Integer类型
            Integer id = Integer.parseInt(ids);
            fileResourcesService.deleteOneResource(id);
        }


        Msg msg = Msg.success();
        //System.out.println(msg);
        return msg;
    }

}
