
package com.kaffatech.latte.db.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.SQLException;

/**
 * @author zhen.ling
 *
 */
public class BaseDataSource extends DruidDataSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String DB_PWD_NAME = "dbPassword";

	private static String host = "182.92.0.161";// SSH服务器
	private static String user = "root";// SSH连接用户名
	private static String password = "d4e39b77";// SSH连接密码
	private static int port = 22;// SSH访问端口

	static String rhost = "127.0.0.1";// 远程MySQL服务器

	private static int lport = 3309;// 本地端口
	private static int rport = 3306;// 远程MySQL服务端口

	public void init() throws SQLException {
		// ssh();
		super.init();
	}

	private static void ssh() {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			System.out.println(session.getServerVersion());// 这里打印SSH服务器版本信息
			int assinged_port = session.setPortForwardingL(lport, rhost, rport);
			System.out.println("localhost:" + assinged_port + " -> " + host
					+ ":" + rport);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
