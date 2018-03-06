package cn.xzl.es;

import cn.xzl.domain.Emp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.RE;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 向ES添加数据
 *
 * @author xzl
 * @create 2018-02-06 16:54
 **/
public class AddData {

    private TransportClient client;
    @Before
    public void test0() throws Exception{
        Settings.Builder setting = Settings.builder().put("cluster.name", "my-application");
        setting.put("client.transport.sniff", true);
        client = TransportClient.builder().settings(setting).build();
        client.addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));
    }

    /*
     *Author: xzl
     *@Description:添加json字符串数据
     *@Date:17:26 2018/2/6
     */
    String index ="szcentral";
    String type ="emp";
    @Test
    public void test1(){
        String jsonStr ="{\"name\":\"tom zhang\",\"age\":19}";
        IndexResponse response = client.prepareIndex(index,type,"1").setSource(jsonStr).get();
        System.out.println(response.getVersion());
    }

    /*
     *Author: xzl
     *@Description:添加map数据
     *@Date:17:26 2018/2/6
     */
    @Test
    public void test2(){
        Map map =new HashMap();
        map.put("name","jack7");
        map.put("score",87);
        IndexResponse response = client.prepareIndex(index, type, "10").setSource(map).get();
        System.out.println(response.getVersion());
    }

    /*
     *Author: xzl
     *@Description:添加bean类型的数据
     *@Date:17:32 2018/2/6
     */
    @Test
    public void test3() throws Exception{
        Emp emp =new Emp("xiao",15);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(emp);
        IndexResponse response = client.prepareIndex(index, type, "3").setSource(s).get();
        System.out.println(response.getVersion());
    }

    /*
     *Author: xzl
     *@Description: 测试helper类型的数据  ES官方提供的
     *@Date:17:42 2018/2/6
     */
    @Test
    public void test4() throws Exception{
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject().
                field("name", "huang").field("age", "12").endObject();
        IndexResponse response = client.prepareIndex(index, type, "4").setSource(builder).get();
        System.out.println(response.getVersion());
    }


}
