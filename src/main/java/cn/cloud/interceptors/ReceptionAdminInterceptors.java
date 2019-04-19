package cn.cloud.interceptors;

import cn.cloud.domain.CloudAdmin;
import cn.cloud.domain.CloudUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 后台过滤器 防止用户登录或登录后就可以进入后台
 * 必须要登录管理员账号才可以进入后台
 */
public class ReceptionAdminInterceptors implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("Admin"+httpServletRequest.getRequestURI()+"**************************************");
        HttpSession session = httpServletRequest.getSession();

        Object loginCode = session.getAttribute("loginAdmin");
        if (loginCode!=null){
            if (loginCode instanceof CloudAdmin){
                return true;
            }else if (loginCode instanceof CloudUser){
                httpServletResponse.sendRedirect("/login/admin/toLogin");
                return false;
            }
        }else {
            httpServletResponse.sendRedirect("/login/admin/toLogin");
            return false;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
