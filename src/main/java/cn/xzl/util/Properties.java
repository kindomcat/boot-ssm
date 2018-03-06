package cn.xzl.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 硬编码字符串读取
 *
 * @author xzl
 * @create 2018-01-27 16:38
 **/
@Component
public class Properties {

    @Value("es_name")
    private String  es_name;
    @Value("nodes")
    private String nodes;

    public String getEs_name() {
        return es_name;
    }

    public void setEs_name(String es_name) {
        this.es_name = es_name;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }
}
