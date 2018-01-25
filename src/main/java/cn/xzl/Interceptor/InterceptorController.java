package cn.xzl.Interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ....
 *
 * @author xzl
 * @create 2018-01-25 18:04
 **/
@Aspect
//声明这是一个组件
@Component
public class InterceptorController {

    @Pointcut("execution(* cn.xzl.controller..*Controller.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    //配置环绕通知，使用在方法上注册的切入点
    @Around("controllerMethodPointcut()") //指定拦截器规则；也可以直接
    public Map Interceptor(ProceedingJoinPoint pjp) {

        try {
            pjp.proceed();  //controller层调用方法，如果异常直接捕获。
        } catch (Throwable throwable) {
            System.out.println(throwable);
            System.out.println(123);
            Map map =new HashMap();
            map.put("result",throwable.getMessage());
            return map;
        }
        return null;
    }

}
