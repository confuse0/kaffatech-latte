package com.kaffatech.latte.ctx.base.config.impl;

import com.kaffatech.latte.ctx.base.SystemProperties;
import com.kaffatech.latte.ctx.base.config.ConfigParameterManager;
import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.commons.log.Log;
import com.kaffatech.latte.commons.log.util.LogUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lingzhen on 16/11/4.
 */
public class ConfigParameterManagerImpl implements ConfigParameterManager, Runnable {

    @Resource
    private DbAccessor dbAccessor;

    private Map<String, String> PARAM_MAP;

    @Override
    public String getParameter(String name) {
        if (PARAM_MAP == null) {
            syncConfigParameterAllInfo();
        }

        return PARAM_MAP.get(name);
    }

    @Override
    public void run() {
        LogUtils.info(Log.SCHEDULING_LOGGER, "BEGIN CACHE CONFIG PARAMETER JOB...");
        syncConfigParameterAllInfo();
        LogUtils.info(Log.SCHEDULING_LOGGER, "END CACHE CONFIG PARAMETER JOB...");
    }

    private void syncConfigParameterAllInfo() {
        final Map<String, String> temp = new HashMap<String, String>();
        dbAccessor.query(getAllSql(), new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                temp.put(rs.getString("NAME"), rs.getString("VALUE"));
                return null;
            }
        });
        PARAM_MAP = temp;
    }

    private String getAllSql() {
        String tbName = SystemProperties.getProperty("configParameterTable");
        if (StringUtils.isEmpty(tbName)) {
            tbName = "CONFIG_PARAMETER";
        }
        String getAllSql = "SELECT * FROM " + tbName;
        return getAllSql;
    }

}
