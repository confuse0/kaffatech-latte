package com.kaffatech.latte.test.db;

import com.kaffatech.latte.db.accessor.DbAccessor;
import com.kaffatech.latte.test.BaseContextTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lingzhen on 16/9/9.
 */
@ContextConfiguration(locations = {"classpath:core.xml"})
public class DbAccessorTest extends BaseContextTest {

    @Resource
    private DbAccessor dbAccessor;

    @Test
    public void testInsert() {
        Map<String, Object> rec = new HashMap<String, Object>();
        rec.put("ID", "321");
        rec.put("NAME", "alexling");
        dbAccessor.insertToTable("SIMPLE", rec);
    }
}
