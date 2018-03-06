package cn.xzl.es;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

/**
 * 删除数据
 *
 * @author xzl
 * @create 2018-02-06 18:05
 **/
public class DeleteData {
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
     *@Description:根据ID删除数据
     *@Date:18:07 2018/2/6
     */
    @Test
    public void test1() throws Exception{
        DeleteResponse response = client.prepareDelete(index, type, "4").get(); //必须有get方法
        System.out.println(response.isFound());
    }


}
