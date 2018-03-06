package cn.xzl.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xzl
 * @create 2018-02-06 16:41
 **/
public class ESClient {

    private static TransportClient client;

    public synchronized static TransportClient getClient() {
        if (client != null) {
            return client;
        }
        try {
            client = init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    public static TransportClient init() throws Exception{
        Settings.Builder setting = Settings.builder().put("cluster.name", "my-application");
        setting.put("client.transport.sniff", true);
        TransportClient client = TransportClient.builder().settings(setting).build();
        client.addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"),9300));
        return client;
    }


}
