package cn.xzl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 测试类
 *
 * @author xzl
 * @create 2018-01-19 17:39
 **/
@RestController
public class HelloController {

    @RequestMapping("/test")
    public Map get(Map map){
        System.out.println(1/0);
        return null;
    }
}
