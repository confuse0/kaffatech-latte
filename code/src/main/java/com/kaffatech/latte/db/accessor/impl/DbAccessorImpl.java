package com.kaffatech.latte.db.accessor.impl;

import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.commons.toolkit.base.ArrayUtils;
import com.kaffatech.latte.commons.toolkit.base.DateUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author lingzhen on 16/9/9.
 */
public class DbAccessorImpl extends JdbcTemplate implements DbAccessor {

    private Map<String, Map<String, Integer>> colMetadata = new ConcurrentHashMap<String, Map<String, Integer>>();

    public int getJdbcType(String tableName, String colName) {
        Map<String, Integer> md = colMetadata.get(tableName);
        if (md == null) {
            md = getTableColMetadata(tableName);
            colMetadata.put(tableName, md);
        }
        return md.get(colName);
    }

    private Map<String, Integer> getTableColMetadata(String tableName) {
        final Map<String, Integer> md = new LinkedHashMap<String, Integer>();
        String sql = "SELECT * FROM " + tableName + " WHERE rownum = 1";
        query(sql, new ResultSetExtractor() {
            @Override
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                ResultSetMetaData rsmd = rs.getMetaData();
                int cntSize = rsmd.getColumnCount();
                for (int i = 1; i <= cntSize; i++) {
                    int colType = rsmd.getColumnType(i);
                    md.put(rsmd.getColumnName(i), colType);
                }
                return null;
            }
        });
        return md;
    }

    public void insertStringToTable(String tableName, List<Map<String, String>> recList) {
        insertToTable(tableName, (List) recList);
    }

    public void insertStringToTable(String tableName, Map<String, String> record) {
        insertToTable(tableName, (Map) record);
    }

    public void insertToTable(String tableName, List<Map<String, Object>> recList) {
        for (Map<String, Object> rec : recList) {
            insertToTable(tableName, rec);
        }

    }

    public void insertToTable(String tableName, Map<String, Object> record) {
        String sql = getInsertSql(tableName, record);
        PreparedStatementSetter setter = getStringPreparedStatementSetter(tableName, record);
        update(sql, setter);
    }

    private String getInsertSql(final String tableName, final Map<String, Object> record) {
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append("INSERT INTO ");
        sqlSb.append(tableName);
        sqlSb.append(" (");
        for (Map.Entry<String, Object> col : record.entrySet()) {
            sqlSb.append(col.getKey());
            sqlSb.append(",");
        }
        sqlSb.deleteCharAt(sqlSb.length() - 1);
        sqlSb.append(") VALUES (");

        for (Map.Entry<String, Object> col : record.entrySet()) {
            sqlSb.append("?");
            sqlSb.append(",");
        }
        sqlSb.deleteCharAt(sqlSb.length() - 1);
        sqlSb.append(")");
        return sqlSb.toString();
    }

    public void deleteFromTable(String tableName, Long... idList) {
        String sql = getDeleteSql(tableName, idList);
        update(sql);
    }

    private String getDeleteSql(String tableName, Long... idList) {
        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append("DELETE FROM ");
        sqlSb.append(tableName);
        if (!ArrayUtils.isEmpty(idList)) {
            sqlSb.append(" WHERE ID IN (");
            for (Long id : idList) {
                sqlSb.append(id);
                sqlSb.append(",");
            }
            sqlSb.deleteCharAt(sqlSb.length() - 1);
            sqlSb.append(")");
        }

        return sqlSb.toString();
    }

    public Map<String, String> queryForStringMap(String sql, Object... args) {
        Map<String, Object> result = queryForMap(sql, args);
        return convertToStringMap(result);
    }

    public Map<String, String> queryForStringMap(String sql, Object[] args, int[] argTypes) {
        Map<String, Object> result = queryForMap(sql, args, argTypes);
        return convertToStringMap(result);
    }

    public List<Map<String, String>> queryForStringMapList(String sql, Object... args) {
        List<Map<String, Object>> result = queryForList(sql, args);
        return convertToStringMapList(result);
    }

    public List<Map<String, String>> queryForStringMapList(String sql, Object[] args, int[] argTypes) {
        List<Map<String, Object>> result = queryForList(sql, args, argTypes);
        return convertToStringMapList(result);
    }

    private List<Map<String, String>> convertToStringMapList(List<Map<String, Object>> recList) {
        List<Map<String, String>> strMapList = new ArrayList<Map<String, String>>();
        for (Map<String, Object> record : recList) {
            strMapList.add(convertToStringMap(record));
        }
        return strMapList;
    }

    private Map<String, String> convertToStringMap(Map<String, Object> record) {
        Map<String, String> strMap = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            strMap.put(entry.getKey(), convertToString(entry.getValue()));
        }
        return strMap;
    }

    private String convertToString(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        }
        return value.toString();
    }

    private PreparedStatementSetter getStringPreparedStatementSetter(final String tableName, final Map<String, Object> record) {
        PreparedStatementSetter setter = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int colIdx = 1;
                for (Map.Entry<String, Object> col : record.entrySet()) {
                    String colName = col.getKey();
                    Object colValue = col.getValue();
                    int colType = getJdbcType(tableName, colName);

                    if (colValue == null) {
                        // 插入null
                        ps.setNull(colIdx, colType);
                    } else {
                        // 数据不为空
                        switch (colType) {
                            case Types.DECIMAL:
                                if (colValue instanceof BigDecimal) {
                                    ps.setBigDecimal(colIdx, (BigDecimal) colValue);
                                } else {
                                    ps.setBigDecimal(colIdx, convertToBigDecimal(colValue));
                                }
                                break;
                            case Types.DATE:
                            case Types.TIMESTAMP:
                                if (colValue instanceof java.sql.Date) {
                                    ps.setTimestamp(colIdx, new Timestamp(((java.sql.Date) colValue).getTime()));
                                } else if (colValue instanceof java.util.Date) {
                                    ps.setTimestamp(colIdx, new Timestamp(((java.util.Date) colValue).getTime()));
                                } else {
                                    ps.setTimestamp(colIdx, convertToSqlTimestamp(colValue));
                                }
                                break;
                            default:
                                ps.setObject(colIdx, colValue, colType);
                                break;
                        }
                    }
                    colIdx++;
                }
            }
        };
        return setter;
    }

    private BigDecimal convertToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }

        return new BigDecimal(value.toString());
    }

    private Timestamp convertToSqlTimestamp(Object value) {
        if (value == null) {
            return null;
        }

        java.util.Date utilDate = DateUtils.parseToDate(value.toString());
        return new Timestamp(utilDate.getTime());
    }

}
