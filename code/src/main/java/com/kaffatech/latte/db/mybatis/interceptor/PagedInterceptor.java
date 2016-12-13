package com.kaffatech.latte.db.mybatis.interceptor;

import com.kaffatech.latte.commons.bean.model.paging.PagedParameter;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.ctx.base.SystemProperties;
import com.kaffatech.latte.db.dialect.Dialect;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @author lingzhen on 16/12/8.
 */
@Intercepts({@Signature(type=Executor.class,method="query",args={ MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })})
public class PagedInterceptor implements Interceptor {

    /**
     * 数据库方言
     */
    private Dialect dialect;

    public Object intercept(Invocation invocation) throws Throwable {
        //当前环境 MappedStatement，BoundSql，及sql取得
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String origSql = boundSql.getSql().trim();
        Object parameterObject = boundSql.getParameterObject();

        if (parameterObject instanceof PagedParameter) {
            // 如果是分页查询生成分页SQL
            PagedParameter pagedParameter = getPagedParameter((PagedParameter) parameterObject);
            String pagedSql = dialect.getPagedSql(origSql, pagedParameter.getPage(), pagedParameter.getRows());

            // 替换分页参数
            BoundSql pagedBoundSql = copyFromBoundSql(mappedStatement, boundSql, pagedSql);
            MappedStatement pagedMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(pagedBoundSql));
            invocation.getArgs()[0] = pagedMs;
        }

        return invocation.proceed();
    }

    private PagedParameter getPagedParameter(PagedParameter pagedParameter) {
        if (pagedParameter.getPage() == null) {
            // 默认值为第一页
            pagedParameter.setPage(1);
        }

        if (pagedParameter.getRows() == null) {
            // 设置单页行数默认值
            String defaultRows = SystemProperties.getProperty("defaultRows");
            if (StringUtils.isEmpty(defaultRows)) {
                pagedParameter.setRows(Integer.MAX_VALUE);
            } else {
                pagedParameter.setRows(Integer.parseInt(defaultRows));
            }
        }

        return pagedParameter;
    }

    /**
     * 复制MappedStatement对象
     */
    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (null != ms.getKeyProperties()) {
            if (ms.getKeyProperties().length > 0) {
                builder.keyProperty(ms.getKeyProperties()[0]);
            }
        }

        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 复制BoundSql对象
     */
    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    /**
     * 拦截器对应的封装原始对象的方法
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 设置注册拦截器时设定的属性
     */
    @Override
    public void setProperties(Properties p) {

    }

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    private class BoundSqlSqlSource implements SqlSource {

        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
