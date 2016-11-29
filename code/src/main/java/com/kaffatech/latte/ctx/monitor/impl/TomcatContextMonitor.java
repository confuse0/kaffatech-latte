
package com.kaffatech.latte.ctx.monitor.impl;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.kaffatech.latte.ctx.monitor.model.ContextParameter;
import com.kaffatech.latte.ctx.monitor.ContextMonitor;

/**
 * @author zhen.ling
 *
 */
public class TomcatContextMonitor implements ContextMonitor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uuban.toffee.ctx.monitor.ContextMonitor#getParameter()
	 */
	@Override
	public ContextParameter getParameter() {
		ContextParameter param = null;
		try {
			ObjectName name = new ObjectName(
					"Catalina:type=Manager,context=/fist,host=localhost");
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			Object val = server.getAttribute(name, "activeSessions");

			if (val != null) {
				param = new ContextParameter();
				param.setActiveSessionNo(((Number) val).intValue());
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return param;
	}
}
