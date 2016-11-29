package com.kaffatech.latte.ctx.session.db;

import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.ctx.id.IdGenerator;
import com.kaffatech.latte.commons.json.util.JsonUtils;
import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;
import com.kaffatech.latte.commons.toolkit.uuid.UuidUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.session.SessionRepository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author lingzhen on 16/10/11.
 */
public class DbSessionRepository implements SessionRepository<DbSession> {

    private static final String CREATE_SQL = "INSERT INTO DB_SESSION (ID, SESSION_ID, CREATION_TIME, LAST_ACCESSED_TIME) VALUES (?,?,?,?)";

    private static final String SAVE_SQL = "UPDATE DB_SESSION SET LAST_ACCESSED_TIME = ?, DATA = ? WHERE SESSION_ID = ?";

    private static final String GET_SQL = "SELECT * FROM DB_SESSION WHERE SESSION_ID = ?";

    private static final String DELETE_SQL = "DELETE FROM DB_SESSION WHERE SESSION_ID = ?";

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private DbAccessor sessionDbAccessor;

    /**
     * 时间间隔
     */
    private int maxInactiveIntervalInSeconds;

    @Override
    public DbSession createSession() {
        DbSession dbSession = new DbSession();
        dbSession.setId(UuidUtils.generate());
        dbSession.setMaxInactiveIntervalInSeconds(maxInactiveIntervalInSeconds);

        sessionDbAccessor.update(CREATE_SQL, idGenerator.next(), dbSession.getId(), dbSession.getCreationTime(), dbSession.getLastAccessedTime());

        return dbSession;
    }

    @Override
    public void save(DbSession dbSession) {
        dbSession.setLastAccessedTime(System.currentTimeMillis());
        sessionDbAccessor.update(SAVE_SQL, dbSession.getLastAccessedTime(), dbSession.getData(), dbSession.getId());
    }

    @Override
    public DbSession getSession(String sid) {
        DbSession dbSession = sessionDbAccessor.queryForObject(GET_SQL, new Object[]{sid}, new RowMapper<DbSession>() {

            @Override
            public DbSession mapRow(ResultSet rs, int rowNum) throws SQLException {
                DbSession row = new DbSession();
                row.setId(rs.getString("SESSION_ID"));
                row.setCreationTime(rs.getLong("CREATION_TIME"));
                row.setLastAccessedTime(rs.getLong("LAST_ACCESSED_TIME"));
                Map<String, Object> sessionData = JsonUtils.toJsonObject(rs.getString("DATA"), Map.class);
                // 转换成线程安全的MAP
                row.setData(CollectionUtils.convertToSafeMap(sessionData));
                return row;
            }
        });
        return dbSession;
    }

    @Override
    public void delete(String sid) {
        sessionDbAccessor.update(DELETE_SQL, sid);
    }

    public void setMaxInactiveIntervalInSeconds(int maxInactiveIntervalInSeconds) {
        this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
    }
}
