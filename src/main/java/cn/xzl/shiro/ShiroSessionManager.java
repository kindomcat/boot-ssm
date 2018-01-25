package cn.xzl.shiro;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author xzl
 * @create 2018-01-23 18:01
 **/
public class ShiroSessionManager extends DefaultWebSessionManager {

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 其实这里还可以使用如下参数：cookie中的session名称：如：JSESSIONID=xxx,路径中的 ;JESSIONID=xxx，但建议还是使用 __sid参数。
        javax.servlet.http.Cookie[] cookies = ((HttpServletRequest)request).getCookies();
        String sid = request.getParameter("token");
//        if(StrUtils.isEmpty(sid)){
        if(cookies !=null){
            for(javax.servlet.http.Cookie c : cookies){
//                    if(c.getName().equals(domain)){
                System.out.println(c.getName());
                sid = c.getValue();
                System.out.println("manager: " + sid);
//                    }
            }
        }
//        }else{
//            try {
//                DESUtils des = new DESUtils(ConstantDate.DESPassword.SSO_TOKEN);
//                sid =des.encrypt(sid);
//            } catch (Exception e) {
//                e.printStackTrace();
//                sid="";
//            }
//        }
//        if (StringUtils.isNotBlank(sid)) {
//            //设置session来源于URL
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sid);
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
//            return sid;
//        }else{
        return sid;
//            return super.getSessionId(request, response);
//        }
    }

}
