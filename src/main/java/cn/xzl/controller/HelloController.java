package cn.xzl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    public Map get(){
        Map map1 =new HashMap();
        map1.put("123","234");
        return map1;
    }
}
