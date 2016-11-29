
package com.kaffatech.latte.db.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.kaffatech.latte.commons.bean.model.type.TypeBean;
import com.kaffatech.latte.commons.toolkit.base.EnumUtils;

/**
 * @author zhen.ling
 *
 */
public class TypeCodeTypeHandler<E extends TypeBean> extends BaseTypeHandler<E> {

	private Class<E> type;
	private final Map<String, E> enums;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TypeCodeTypeHandler(Class type) {
		if (type == null) {
			throw new IllegalArgumentException("type argument cannot be null");
		}

		List<E> enumList = EnumUtils.getEnumList(type);
		Map<String, E> temp = new HashMap<String, E>();
		for (E each : enumList) {
			temp.put(each.getCode(), each);
		}
		this.type = type;
		enums = temp;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter,
			JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getCode());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String code = rs.getString(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			try {
				return enums.get(code);
			} catch (Exception ex) {
				throw new IllegalArgumentException("Cannot convert " + code
						+ " to " + type.getSimpleName() + " by code value.", ex);
			}
		}
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String code = rs.getString(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			try {
				return enums.get(code);
			} catch (Exception ex) {
				throw new IllegalArgumentException("Cannot convert " + code
						+ " to " + type.getSimpleName() + " by code value.", ex);
			}
		}
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String code = cs.getString(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			try {
				return enums.get(code);
			} catch (Exception ex) {
				throw new IllegalArgumentException("Cannot convert " + code
						+ " to " + type.getSimpleName() + " by code value.", ex);
			}
		}
	}
}
