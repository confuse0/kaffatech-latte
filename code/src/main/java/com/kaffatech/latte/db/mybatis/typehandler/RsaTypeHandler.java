
package com.kaffatech.latte.db.mybatis.typehandler;

import com.kaffatech.latte.security.util.KeyUtils;
import com.kaffatech.latte.security.util.RsaUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhen.ling
 *
 */
public class RsaTypeHandler extends BaseTypeHandler<String> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			String parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, RsaUtils.encryptByPublicKey(KeyUtils.getCryptPrivateKey(), parameter));
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String value = rs.getString(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			return RsaUtils.decryptByPrivateKey(KeyUtils.getCryptPrivateKey(), value);
		}
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String value = rs.getString(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			return RsaUtils.decryptByPrivateKey(KeyUtils.getCryptPrivateKey(), value);
		}
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String value = cs.getString(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			return RsaUtils.decryptByPrivateKey(KeyUtils.getCryptPrivateKey(), value);
		}
	}
}
