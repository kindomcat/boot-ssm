package cn.xzl.filter;


import cn.xzl.domain.User;
import cn.xzl.util.ErrorCodeUtils;
import org.apache.shiro.web.servlet.AdviceFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录过滤器    AdviceFilter是一个AOP过滤器
 * @author xzl
 * @create 2018-01-23 16:24
 **/
public class ShiroLoginFilter extends AdviceFilter {

    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     *
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        User User = (User) httpServletRequest.getSession().getAttribute("user");
        //设置会话维持时间 单位秒
        httpServletRequest.getSession().setMaxInactiveInterval(36000);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
//        System.out.println("code_sid12:" + httpServletRequest.getSession().getId());
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        PrintWriter printWriter = null;
        if (null == User) {
            try {
                printWriter = response.getWriter();
                printWriter.write("{\"errorCode\": \"" + ErrorCodeUtils.get("NoLogin") + "\"}");
                printWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (printWriter != null) {
                    printWriter.close();
                }
            }
            return false;
        }
        return true;
    }

}
