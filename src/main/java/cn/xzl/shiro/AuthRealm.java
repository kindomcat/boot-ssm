package cn.xzl.shiro;

import cn.xzl.domain.User;
import cn.xzl.service.Userservice;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xzl
 * @create 2018-01-23 16:04
 **/
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    Userservice userservice;

    //认证登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) authenticationToken; // 获取用户输入的token
        String username = utoken.getUsername();
        User user = userservice.findAccountByName(username);
        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }

    //授权  将权限放入shiro中
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
/*        User user = (User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();// 获取session中的用户
        List<String> permissions = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Module> modules = role.getModules();
            for (Module module : modules) {
                permissions.add(module.getCode());
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions); // 将权限放入shiro中.
        return info;*/
        return null;
    }
}
