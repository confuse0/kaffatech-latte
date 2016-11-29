package com.kaffatech.latte.ctx.cc.impl;

import com.kaffatech.latte.ctx.cc.ConfigCenterManager;
import com.kaffatech.latte.ctx.cc.model.ConfigCenter;
import com.kaffatech.latte.ctx.cc.model.Constants;
import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.commons.toolkit.base.DateUtils;
import com.kaffatech.latte.commons.net.util.IpAddressUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lingzhen on 16/11/22.
 */
public class ConfigCenterManagerImpl implements ConfigCenterManager {

    private static final String QUERY_SQL = "SELECT * FROM CONFIG_CENTER WHERE ID = ?";

    private static final String INSERT_SQL = "INSERT INTO CONFIG_CENTER (ID, MASTER, LAST_ACCESS_TIME, VER, CREATE_TIME, UPDATE_TIME) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL = "UPDATE CONFIG_CENTER SET MASTER = ?, VER = ?, LAST_ACCESS_TIME = ?, UPDATE_TIME = ? WHERE ID = ?";

    @Resource
    private DbAccessor dbAccessor;

    @Override
    public String takeMaster() {
        ConfigCenter cc = getConfigCenter();
        if (cc == null) {
            cc = elect();
        }
        return cc.getMaster();
    }

    /**
     * 选举
     */
    private ConfigCenter elect() {
        // 查询配置中心主服务器
        ConfigCenter cc = getConfigCenter();

        Date now = new Date();
        if (cc == null || StringUtils.isEmpty(cc.getMaster())) {
            // 还没有主服务器,需要选举
            ConfigCenter inserted = new ConfigCenter();
            inserted.setId(1L);
            inserted.setMaster(IpAddressUtils.getHostAddress());
            inserted.setLastAccessTime(now);
            inserted.setVer(1L);
            cc = inserted;

            dbAccessor.update(INSERT_SQL, new Object[] {inserted.getId(), inserted.getMaster(), inserted.getLastAccessTime(), inserted.getVer(), now, now});
        } else if (DateUtils.addSeconds(cc.getLastAccessTime(), Constants.SERVER_MAX_HEARTBEAT).compareTo(now) < 0) {
            // 主服务器可能当机了,需要重新选举
            ConfigCenter updated = new ConfigCenter();
            updated.setId(cc.getId());
            updated.setMaster(IpAddressUtils.getHostAddress());
            updated.setLastAccessTime(now);
            updated.setVer(cc.getVer() + 1L);
            cc = updated;

            dbAccessor.update(UPDATE_SQL, updated.getMaster(), updated.getLastAccessTime(), updated.getVer(), now, updated.getId());
        } else {
            // 主服务器正常,不需要选举
        }

        return cc;
    }

    private ConfigCenter getConfigCenter() {
        ConfigCenter cc = dbAccessor.queryForObject(QUERY_SQL, new Object[]{1L}, ConfigCenter.class);

        return cc;
    }
}
