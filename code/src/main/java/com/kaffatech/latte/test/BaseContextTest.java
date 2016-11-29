package com.kaffatech.latte.test;


import org.junit.Before;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author lingzhen on 16/8/29.
 */
public abstract class BaseContextTest extends AbstractJUnit4SpringContextTests {

    @Before
    public void baseBefore() {
        System.setProperty("unitTest", "true");
    }

}
