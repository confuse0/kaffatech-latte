package com.kaffatech.latte.search.es.impl;

import com.kaffatech.latte.search.es.SearchClient;
import com.kaffatech.latte.search.es.SearchRule;
import com.kaffatech.latte.search.es.builder.SearchBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;


/**
 * Created by lingzhen on 16/3/31.
 */
public class SearchClientImpl implements SearchClient {

    /**
     *  代理对象
     */
    private Client delegate;

    /**
     * 名字
     */
    private String name;

    /**
     * ip
     */
    private String ip;

    /**
     * 分库分表规则
     */
    private SearchRule rule;


    public void init() {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", name).build();
        delegate =  new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(
                        ip, 9300));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public SearchRule getRule() {
        return rule;
    }

    public void setRule(SearchRule rule) {
        this.rule = rule;
    }



    /**
     * 预搜索
     *
     * @param indices
     * @return
     */
    @Override
    public SearchBuilder prepareSearch(String... indices) {
        String[] newIndices = indices;
        if (indices != null && rule != null) {
            for (int i = 0; i < indices.length; i++) {
                newIndices[i] = rule.getIndexName(indices[i]);
            }
        }
        return new SearchBuilder(delegate.prepareSearch(newIndices));
    }

}
