package cn.xzl.shiro;

import cn.xzl.filter.ShiroLoginFilter;
import cn.xzl.filter.ShiroPermsFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 *
 * @author xzl
 * @create 2018-01-23 16:03
 **/
public class ShiroConfig {

    // 配置核心安全事务管理器
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm, @Qualifier("sessionManager") SessionManager sessionManager) {

        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //设置认证领域
        manager.setRealm(authRealm);
//        manager.setSessionManager(sessionManager);
//        manager.setCacheManager();
        return manager;
    }

    //配置拦截权限
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);

        // filtersMap
        Map<String, Filter> filterMap = new LinkedHashMap<>();

//        filterMap.put("cors", new CorsFilter());
        filterMap.put("authc", new ShiroLoginFilter());
        filterMap.put("perms", new ShiroPermsFilter());

        bean.setFilters(filterMap);

        // 配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/static/**", "anon"); // 表示可以匿名访问
        filterChainDefinitionMap.put("/loginUser", "anon");
        filterChainDefinitionMap.put("/getVerCode", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/*", "authc,perms");// 表示需要认证才可以访问
        filterChainDefinitionMap.put("/**", "authc,perms");// 表示需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "authc,perms");
//        filterChainDefinitionMap.put("/*", "perms");// 表示需要认证才可以访问
//        filterChainDefinitionMap.put("/**", "perms");// 表示需要认证才可以访问
//        filterChainDefinitionMap.put("/*.*", "perms");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }
}
