
package com.kaffatech.latte.db.mybatis.typehandler;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.kaffatech.latte.commons.bean.model.num.Money;
import com.kaffatech.latte.commons.toolkit.base.math.MoneyUtils;

/**
 * @author zhen.ling
 *
 */
public class MoneyTypeHandler extends BaseTypeHandler<Money> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Money parameter, JdbcType jdbcType) throws SQLException {
		ps.setBigDecimal(i, MoneyUtils.toBigDecimal(parameter));
	}

	@Override
	public Money getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		BigDecimal value = rs.getBigDecimal(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			return MoneyUtils.createMoney(value);
		}
	}

	@Override
	public Money getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		BigDecimal value = rs.getBigDecimal(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			return MoneyUtils.createMoney(value);
		}
	}

	@Override
	public Money getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		BigDecimal value = cs.getBigDecimal(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			return MoneyUtils.createMoney(value);
		}
	}
}
