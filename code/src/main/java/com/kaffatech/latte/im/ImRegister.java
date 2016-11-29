
package com.kaffatech.latte.im;

import com.kaffatech.latte.im.dmo.ImRegistInfo;
import com.kaffatech.latte.im.dmo.ImUser;

/**
 * @author zhen.ling
 *
 */
public interface ImRegister {

	/**
	 * 获取token
	 * 
	 * @return
	 */
	String getToken();

	/**
	 * IM 注册
	 * 
	 * @param user
	 * @return
	 */
	ImUser regiest(ImRegistInfo registInfo);

	/**
	 * 获取IM用户
	 * 
	 * @param username
	 * @return
	 */
	ImUser getImUser(String username);

	/**
	 * 修改昵称
	 * 
	 * @param username
	 * @param nickname
	 */
	void changeNickname(String username, String nickname);

}
