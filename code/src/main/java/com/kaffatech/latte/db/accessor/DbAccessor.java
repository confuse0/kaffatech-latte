package com.kaffatech.latte.db.accessor;

import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.Map;

/**
 * @author lingzhen on 16/9/9.
 */
public interface DbAccessor extends JdbcOperations {

    int getJdbcType(String tableName, String colName);

    long nextSequence(String seqName);

    long currSequence(String seqName);

    // ------------------------------ String Map -------------------------------

    Map<String, String> queryForStringMap(String sql, Object... args);

    Map<String, String> queryForStringMap(String sql, Object[] args, int[] argTypes);

    List<Map<String, String>> queryForStringMapList(String sql, Object... args);

    List<Map<String, String>> queryForStringMapList(String sql, Object[] args, int[] argTypes);


    // ------------------------------ 批量功能 -------------------------------

    void insertStringToTable(String tableName, List<Map<String, String>> recList);

    void insertStringToTable(final String tableName, final Map<String, String> record);

    void insertToTable(String tableName, List<Map<String, Object>> recList);

    void insertToTable(final String tableName, final Map<String, Object> record);

    void deleteFromTable(String tableName, Long... idList);
}
