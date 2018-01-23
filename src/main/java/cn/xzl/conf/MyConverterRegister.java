package cn.xzl.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * 路径解析器
 *
 * @author xzl
 * @create 2018-01-19 17:46
 **/
@Configurable
public class MyConverterRegister {

    @Autowired
    private ConverterRegistry converterRegistry;

    @PostConstruct
    public void init() {
        converterRegistry.addConverter(new StringToListConvert());
    }

    //拦截请求
    public static  class StringToListConvert implements Converter<String, List<String>> {
        @Override
        public List<String> convert(String source) {
            if (source == null) {
                return Arrays.asList();
            } else {
                String[] split = source.split(",");
                System.out.println(Arrays.asList(split));
                return Arrays.asList(split);
            }
        }
    }

}
