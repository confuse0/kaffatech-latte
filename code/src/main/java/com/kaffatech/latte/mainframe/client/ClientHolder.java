
package com.kaffatech.latte.mainframe.client;

import com.kaffatech.latte.mainframe.client.model.Client;

/**
 * @author zhen.ling
 *
 */
public class ClientHolder {

	private static ThreadLocal<Client> CLIENT = new ThreadLocal<Client>();

	public static void set(Client client) {
		CLIENT.set(client);
	}

	public static Client get() {
		return CLIENT.get();
	}

	public static void clear() {
		CLIENT.remove();
	}
}
