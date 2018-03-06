package cn.xzl.es;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

/**
 * 批量处理数据
 *
 * @author xzl
 * @create 2018-02-06 18:17
 **/
public class BulkData {
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
     *@Description:批量处理数据,适合初始化操作，提高效率
     *@Date:18:18 2018/2/6
     */
    @Test
    public void test1() throws Exception{
        BulkRequestBuilder bulk = client.prepareBulk();
        //index 请求
        IndexRequest indexRequest = new IndexRequest(index, type, "10");
        indexRequest.source("{\"name\":\"zhangsan\",\"age\":17}");
        //delete 请求
        DeleteRequest deleteRequest = new DeleteRequest(index, type, "1");
        bulk.add(indexRequest);
        bulk.add(deleteRequest);
        //执行批量操作
        BulkResponse responses = bulk.get();
        if (responses.hasFailures()){
            BulkItemResponse[] items = responses.getItems();
            for (BulkItemResponse item : items) {
                System.out.println(item.getFailureMessage());
            }
        }else {
            System.out.println("全部成功");
        }

    }
}
