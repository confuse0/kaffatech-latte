package com.kaffatech.latte.search.es.impl;

import com.kaffatech.latte.search.es.SearchRule;

/**
 * Created by lingzhen on 16/4/5.
 */
public abstract class BaseSearchRule implements SearchRule {

    @Override
    public String getClientName() {
        return "";
    }

    @Override
    public String getIndexName(String indexName) {
        return indexName;
    }

    @Override
    public String getTypeName(String typeName) {
        return typeName;
    }
}
