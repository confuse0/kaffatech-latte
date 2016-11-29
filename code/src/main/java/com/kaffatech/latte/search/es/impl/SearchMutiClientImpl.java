package com.kaffatech.latte.search.es.impl;

import com.kaffatech.latte.search.es.SearchClient;
import com.kaffatech.latte.search.es.SearchRule;
import com.kaffatech.latte.search.es.builder.SearchBuilder;

import java.util.Map;

/**
 * Created by lingzhen on 16/3/31.
 */
public class SearchMutiClientImpl implements SearchClient {

    /**
     * 多个Client
     */
    private Map<String, SearchClient> clientMap;

    /**
     * 分库分表规则
     */
    private SearchRule rule;

    public Map<String, SearchClient> getClientMap() {
        return clientMap;
    }

    public void setClientMap(Map<String, SearchClient> clientMap) {
        this.clientMap = clientMap;
    }

    public SearchRule getRule() {
        return rule;
    }

    public void setRule(SearchRule rule) {
        this.rule = rule;
    }

    private SearchClient getClient() {
        String clientName = (rule == null) ? "" :  rule.getClientName();
        return clientMap.get(clientName);
    }

    /**
     * 预搜索
     *
     * @param indices
     * @return
     */
    @Override
    public SearchBuilder prepareSearch(String... indices) {
        return getClient().prepareSearch(indices);
    }

}
