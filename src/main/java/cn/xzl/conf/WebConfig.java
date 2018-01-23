package cn.xzl.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * ...
 *
 * @author xzl
 * @create 2018-01-23 15:34
 **/
@Configuration
@EnableWebMvc
@ComponentScan//组件扫描
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatter(new MyFormatterRegistry.StringDateFormatter());

    }
}
