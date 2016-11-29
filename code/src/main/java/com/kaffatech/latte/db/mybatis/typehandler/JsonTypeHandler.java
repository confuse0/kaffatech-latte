package com.kaffatech.latte.db.mybatis.typehandler;

import com.kaffatech.latte.commons.bean.transfer.util.TransferUtils;
import com.kaffatech.latte.commons.json.util.JsonUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lingzhen on 16/11/8.
 */
public class JsonTypeHandler<E> extends BaseTypeHandler<E> {

    private Class<E> type;

    public JsonTypeHandler(Class type) {
        if (type == null) {
            throw new IllegalArgumentException("type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JsonUtils.toJsonString(TransferUtils.transfer(parameter)));
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String value = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return convert(value);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String value = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return convert(value);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String value = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return convert(value);
        }
    }

    private E convert(String value) {
        // 根据字符串转成对象
        E obj = JsonUtils.toJsonObject(value, type);
        // 进行对象字段转换
        return TransferUtils.transfer(obj);
    }
}
