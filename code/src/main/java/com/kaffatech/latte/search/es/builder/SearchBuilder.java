package com.kaffatech.latte.search.es.builder;

import com.kaffatech.latte.search.es.SearchRule;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;


/**
 * Created by lingzhen on 16/4/1.
 */
public class SearchBuilder {


    private SearchRequestBuilder delegate;

    private SearchRule rule;

    public SearchBuilder(SearchRequestBuilder delegate) {
        this(delegate, null);
    }

    public SearchBuilder(SearchRequestBuilder delegate, SearchRule rule) {
        this.delegate = delegate;
        this.rule = rule;
    }

    public SearchBuilder setIndices(String... indices) {
        String[] newIndices = indices;
        if (indices != null && rule != null) {
            for (int i = 0; i < indices.length; i++) {
                newIndices[i] = rule.getIndexName(indices[i]);
            }
        }
        delegate.setIndices(newIndices);
        return this;
    }

    public SearchBuilder setTypes(String... types) {
        String[] newTypes = types;
        if (types != null && rule != null) {
            for (int i = 0; i < types.length; i++) {
                newTypes[i] = rule.getTypeName(types[i]);
            }
        }
        delegate.setTypes(newTypes);
        return this;
    }

    public SearchBuilder setSearchType(SearchType searchType) {
        delegate.setSearchType(searchType);
        return this;
    }

    public ListenableActionFuture<SearchResponse> execute() {
        return delegate.execute();
    }
}
