package com.kaffatech.latte.search.es;

import com.kaffatech.latte.search.es.builder.SearchBuilder;

/**
 * Created by lingzhen on 16/4/5.
 */
public interface SearchClient {

    SearchBuilder prepareSearch(String... indices);
}
