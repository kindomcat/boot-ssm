package cn.xzl.es;

import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

/**
 * 更新数据
 *
 * @author xzl
 * @create 2018-02-06 17:57
 **/
public class UpdateData {
    private TransportClient client;
    @Before
    public void test0() throws Exception{
        Settings.Builder setting = Settings.builder().put("cluster.name", "my-application");
        setting.put("client.transport.sniff", true);
        client = TransportClient.builder().settings(setting).build();
        client.addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));
    }
    String index ="szcentral";
    String type ="emp";

    /*
     *Author: xzl
     *@Description:局部更新数据
     *@Date:18:00 2018/2/6
     */
    @Test
    public void test1() throws Exception{
        XContentBuilder builder = XContentFactory.jsonBuilder().
                startObject().field("age", "45").endObject();
        UpdateResponse response = client.prepareUpdate(index, type, "4").setDoc(builder).get();
        System.out.println(response.getVersion());
    }
}
