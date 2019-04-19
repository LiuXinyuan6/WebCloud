package cn.cloud.interceptors;

import cn.cloud.domain.CloudAdmin;
import cn.cloud.domain.CloudUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 前台过滤器
 *  访问某些页面需要先登录才可以进行访问
 */
public class ReceptionUserInterceptors implements HandlerInterceptor {
    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();

         Object loginCode = session.getAttribute("UserLogin");
        if (loginCode!=null){
            if (loginCode instanceof CloudAdmin){//判断对象是否是管理员对象 是可以访问全部的用户界面
                return true;
            }else if (loginCode instanceof CloudUser){//判断对象是否是用户对象 是可以访问用户界面
                return true;
            }else {
                httpServletResponse.sendRedirect("/login/user/toLogin");
                return false;
            }
        }else {
            httpServletResponse.sendRedirect("/login/user/toLogin");
            return false;
        }
    }
    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
