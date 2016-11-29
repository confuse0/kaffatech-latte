
package com.kaffatech.latte.db.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kaffatech.latte.commons.bean.model.date.Time;
import com.kaffatech.latte.commons.toolkit.base.TimeUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * @author zhen.ling
 *
 */
public class TimeTypeHandler extends BaseTypeHandler<Time> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Time parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.toShortString());
	}

	@Override
	public Time getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String value = rs.getString(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			return TimeUtils.parseToTime(value);
		}
	}

	@Override
	public Time getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String value = rs.getString(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			return TimeUtils.parseToTime(value);
		}
	}

	@Override
	public Time getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String value = cs.getString(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			return TimeUtils.parseToTime(value);
		}
	}
}
