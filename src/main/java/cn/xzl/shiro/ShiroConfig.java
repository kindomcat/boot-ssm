package cn.xzl.shiro;

import cn.xzl.filter.ShiroLoginFilter;
import cn.xzl.filter.ShiroPermsFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
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

    // 配置会话管理
    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new ShiroSessionManager();
        //默认为true
        sessionManager.setSessionIdCookieEnabled(true);
//        SimpleCookie simpleCookie = new SimpleCookie();
//        simpleCookie.setName("JSESSIONID");
////        simpleCookie.setDomain("192.168.5.49");
//        sessionManager.setSessionIdCookie(simpleCookie);
        return sessionManager;
    }

    // 配置自定义的权限登录器
    @Bean(name = "authRealm")
    public AuthRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    // 配置自定义的密码比较器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    //配置shiro自己管理bean的生命周期
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    //开启shiro的注解模式
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

}
