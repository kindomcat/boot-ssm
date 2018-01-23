package cn.xzl.filter;

import cn.xzl.domain.Module;
import cn.xzl.domain.User;
import cn.xzl.service.PermissionService;
import cn.xzl.util.ErrorCodeUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限过滤器
 * @author xzl
 * @create 2018-01-23 16:36
 **/
public class ShiroPermsFilter extends PermissionsAuthorizationFilter {
    @Autowired
    private PermissionService permissionService;
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        // 记录日志
        Subject subject = this.getSubject(request, response);
//        String url = this.getLoginUrl();
//
//        String[] perms = (String[])((String[])mappedValue);

        User user = (User) subject.getPrincipal();

        boolean isPermitted = false;

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String code = httpServletRequest.getParameter("cmd");

//        if (code != null && subject.isPermitted(code)) {

/*        Module module = (Module) user.getModules().get(code);

        module = module == null ? (Module) user.getMenus().get(code) : module;

        if (code != null && user != null) {
//        if (code != null && module != null) {
            // 说明有权限
            isPermitted = true;
            // allow to add log.
            if (module != null && module.getIsLog() == 1) {
                Map map = new HashMap();
                map.put("ip", IpUtils.getIp(httpServletRequest));
                map.put("userId", user.getUid());
                map.put("optCode", code);
                try {
                    permissionService.addUserLog(map);
                } catch (Exception e) {}
            }
        }*/
        return isPermitted;
    }

    /**
     * shiro认证perms资源失败后回调方法
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        PrintWriter printWriter = null;
        try {
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("application/json");
            printWriter = servletResponse.getWriter();
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String cmd = httpServletRequest.getParameter("cmd");

            if (cmd == null) {
                printWriter.write("{\"errorCode\": \"" + ErrorCodeUtils.get("NoCMD") + "\"}");
            } else if (cmd != null) {
                printWriter.write("{\"errorCode\": \"" + ErrorCodeUtils.get("NoPower") + "\"}");
            }
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
}
