package cn.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * 错误页面配置
 *     web-xml+Controller
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {


    @RequestMapping("/400")
    public String handle1(HttpServletRequest request) {
        return "error/400";
    }

    @RequestMapping("/404")
    public String handle2(HttpServletRequest request) {
        return "error/404";
    }

    @RequestMapping("/500")
    public String handle3(HttpServletRequest request) {
        return "error/500";
    }

}
