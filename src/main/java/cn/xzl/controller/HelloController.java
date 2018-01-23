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
    public String get(Map map){
        System.out.println(map);
        return "123";
    }
}
