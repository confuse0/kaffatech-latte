package com.kaffatech.latte.search.es;

/**
 * Created by lingzhen on 16/3/31.
 */
public interface SearchRule {

    String getClientName();

    String getIndexName(String indexName);

    String getTypeName(String typeName);
}
