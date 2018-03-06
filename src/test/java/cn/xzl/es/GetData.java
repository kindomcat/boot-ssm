package cn.xzl.es;

import org.apache.lucene.index.Term;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.List;

/**
 * 获得数据
 *
 * @author xzl
 * @create 2018-02-06 17:49
 **/
public class GetData {

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
     *@Description:根据id查数据
     *@Date:17:50 2018/2/6
     */
    @Test
    public void test1(){
        GetResponse response = client.prepareGet(index, type, "1").get();
        System.out.println(response.getSource());  //获得 map
    }

    /*
     *Author: xzl
     *@Description:求数据行总数
     *@Date:18:11 2018/2/6
     */
    @Test
    public void test2(){
        //es 2.x
        long count = client.prepareCount(index).setTypes(type).get().getCount();
        System.out.println(count);
    }

    /*
     *QUERY_AND_FETCH：只追求查询性能的时候可以选择
     * QUERY_THEN_FETCH：默认
     * DFS_QUERY_AND_FETCH：只需要排名准确即可
     * DFS_QUERY_THEN_FETCH：对效率要求不是非常高，对查询准确度要求非常高，建议使用这一种
     * Author: xzl
     *@Description:查询
     *@Date:18:36 2018/2/6
     */
    @Test
    public void test3(){
        SearchResponse response = client.prepareSearch(index)     //指定索引库
                .setTypes(type)          //指定类型
                //.setQuery(QueryBuilders.matchQuery("name", "tom"))   //指定查询条件
                //.setSearchType(SearchType.DFS_QUERY_AND_FETCH)             //指定查询方式
                .get();
        SearchHits hits = response.getHits();
        long total = hits.getTotalHits();
        System.out.println("总数： "+total);

        //获取满足查询条件的详细类容
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getSource());
        }
    }
    
    /*
     *Author: xzl
     *@Description:sarch查询详情
     *@Date:9:25 2018/2/7
     */
    @Test
    public void test4(){
        SearchResponse response = client.prepareSearch(index)     //指定索引库
                .setTypes(type)          //指定类型
                //.setQuery(QueryBuilders.matchQuery("name", "tom"))   //指定查询条件
               // .setQuery(QueryBuilders.matchAllQuery())               //查询所有
               // .setQuery(QueryBuilders.multiMatchQuery("tom","name","title"))   //相当于  where name=tom or title=tom
              // .setQuery(QueryBuilders.queryStringQuery("name:to*"))     // ?匹配一个   *匹配多个

                .setQuery(QueryBuilders.boolQuery()//匹配多个条件  must 相当于and ,should相当于or
                        .should(QueryBuilders.matchQuery("name","jack2"))
                        .should(QueryBuilders.matchQuery("score","87")))
                .setExplain(true)          //查询数据的匹配度返回数据
                .get();
        SearchHits hits = response.getHits();
        System.out.println("总数为：" +hits.getTotalHits());

        SearchHit[] searchHits = hits.getHits();
        System.out.println(searchHits.length);
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getSource());
        }
    }

    /*
     *Author: xzl
     *@Description:
     *@Date:13:51 2018/2/7
     */
    @Test
    public void test5(){
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                //.setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name","19").boost(8.0f))) //设置权重
                //.setQuery(QueryBuilders.termQuery("name","tom zhang"))   //精确查询，主要针对 人名、地名
                //注意：需要精确查询的字段一般不需要分词，如果分词了，还想要精确查询，使用queryStringQuery
                //.setQuery(QueryBuilders.queryStringQuery("name:\"tom\""))
                //and 或者or 表示 和 或者的关系
                //.setQuery(QueryBuilders.matchQuery("name","tom cat").operator(MatchQueryBuilder.Operator.AND))
                .setFrom(0).setSize(2)   //分页
                //.addSort("age", SortOrder.DESC)  排序
                .get();

        SearchHits hits = response.getHits();
        System.out.println("总数为：" +hits.getTotalHits());

        SearchHit[] searchHits = hits.getHits();
        System.out.println(searchHits.length);
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getSource());
        }
    }

    /*
     *lt小于  lte小于等于  gt大于  gte大于等于
     * Author: xzl
     *@Description:过滤   默认是闭区间
     *@Date:15:53 2018/2/7
     */
    @Test
    public void test6(){
        client.prepareSearch(index)
                .setTypes(type)
                //.setPostFilter(QueryBuilders.rangeQuery("age").from(18).to(19).includeLower(true).includeUpper(true)) //这个可以缓存
                .setPostFilter(QueryBuilders.rangeQuery("age").gt(17).lt(19))
                .get();
    }

    /*
     *Author: xzl
     *@Description:分组求count
     *@Date:16:00 2018/2/7
     */
    @Test
    public void test7(){
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("term_age").field("score").size(0))   //设置为0会返回所有的。
                .get();
        //获取分组信息
        Terms terms = response.getAggregations().get("term_age");
        List<Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKey()+"---"+bucket.getDocCount());
        }

    }

    /*
     *分组求sum
     * Author: xzl
     *@Description:
     *@Date:16:19 2018/2/7
     */
    @Test
    public void test8(){
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("term_name").field("name").size(0)   //设置为0会返回所有的。
                .subAggregation(AggregationBuilders.sum("sum_score").field("score")))
                .get();
        //获取分组信息
        Terms terms = response.getAggregations().get("term_name");
        List<Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            Sum sum =bucket.getAggregations().get("sum_score");
            System.out.println(bucket.getKey()+"---"+sum.getValue());
        }
    }

    /*
     *多索引多类型查询
     * Author: xzl
     *@Description:
     *@Date:16:29 2018/2/7
     */
    @Test
    public void test9(){
        client.prepareSearch("szcentral*")   //支持通配符
                .setTypes("type1","type2")    //不支持通配符
                .get();
    }











}
