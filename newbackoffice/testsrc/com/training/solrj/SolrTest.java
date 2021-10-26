package com.training.solrj;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.Registry;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.io.IOException;

//@JuitTest
public class SolrTest {


    private HttpSolrClient solrClient;
    private String url = "http://localhost:8983/solr/";


    @Test
    public void query() throws IOException, SolrServerException {
        Registry.getApplicationContext();
        solrClient = new HttpSolrClient(url);
        SolrQuery query = new SolrQuery();
        //设置主查询条件
        query.setQuery("*:*");
        //过滤条件
        query.addFilterQuery("code_string:001");//区间
        query.setSort("id", SolrQuery.ORDER.asc);//排序
        query.setStart(0);//分页
        query.setRows(10);
        query.setFields("code","name","content");//显示的字段
        query.setHighlight(true);//高亮显示
        query.addHighlightField("name");//高亮显示的域
        query.setHighlightSimplePre("<em>");//高亮显示的前缀
        query.setHighlightSimplePost("</em>");//高亮显示的后缀
        QueryResponse response = solrClient.query(query);
        SolrDocumentList results = response.getResults();
        System.out.println(results.getNumFound());
        for(SolrDocument doc:results) {
            System.out.println(doc.get("id") + "," + doc.get("name") + "," + doc.get("age"));
        }
        solrClient.close();
    }
}
