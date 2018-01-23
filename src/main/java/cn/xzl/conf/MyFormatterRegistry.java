package cn.xzl.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

/**
 * ...
 *
 * @author xzl
 * @create 2018-01-23 15:32
 **/
public class MyFormatterRegistry {
    @Autowired
    private FormatterRegistry formatterRegistry;

    @PostConstruct
    public void init() {
        formatterRegistry.addFormatter(new StringDateFormatter());
    }


    public static class StringDateFormatter implements Formatter<List> {
        //解析接口，根据Locale信息解析字符串到T类型的对象；

        @Override
        public List parse(String text, Locale locale) throws ParseException {
            System.out.println(123);
            return null;
        }

        //格式化显示接口，将T类型的对象根据Locale信息以某种格式进行打印显示（即返回字符串形式）；
        @Override
        public String print(List object, Locale locale) {
            return "我是格式化的日期";
        }
    }
}
