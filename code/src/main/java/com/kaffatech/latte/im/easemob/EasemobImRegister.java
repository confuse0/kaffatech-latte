
package com.kaffatech.latte.im.easemob;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kaffatech.latte.commons.json.util.JsonUtils;
import com.kaffatech.latte.commons.net.http.util.HttpUtils;
import com.kaffatech.latte.im.ImRegister;
import com.kaffatech.latte.im.dmo.ImRegistInfo;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.kaffatech.latte.commons.bean.util.BeanUtils;
import com.kaffatech.latte.im.dmo.GetTokenReq;
import com.kaffatech.latte.im.dmo.ImUser;

/**
 * @author zhen.ling
 *
 */
public class EasemobImRegister implements ImRegister {

	private static final String ORG = "sweet";
	private static final String APP = "sweet";

	private static String authorization = "Bearer ";

	public void init() {
		getToken();
	}

	@Override
	public String getToken() {
		String url = "https://a1.easemob.com/" + ORG + "/" + APP + "/token";

		String token = HttpUtils.postJson(url,
				JsonUtils.toJsonString(new GetTokenReq()),
				new ResponseHandler<String>() {

					@Override
					public String handleResponse(HttpResponse response)
							throws ClientProtocolException, IOException {

						if (response.getStatusLine().getStatusCode() == 200) {
							HttpEntity httpEntity = response.getEntity();
							Map obj = JsonUtils.toJsonObject(
									EntityUtils.toString(httpEntity), Map.class);
							String token = (String) obj.get("access_token");
							return token;
						} else {
							HttpEntity httpEntity = response.getEntity();
							Map obj = JsonUtils.toJsonObject(
									EntityUtils.toString(httpEntity), Map.class);
							String error = (String) obj.get("error");

							throw new IllegalStateException(
									"get im token fail:" + error);
						}
					}
				});
		authorization = "Bearer " + token;
		return token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uuban.toffee.im.ImRegister#regiest(com.uuban.toffee.im.dmo.ImRegistInfo
	 * )
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ImUser regiest(final ImRegistInfo registInfo) {
		String url = "https://a1.easemob.com/" + ORG + "/" + APP + "/users";

		ImUser user = HttpUtils.postJson(url,
				JsonUtils.toJsonString(registInfo),
				new ResponseHandler<ImUser>() {

					@Override
					public ImUser handleResponse(HttpResponse response)
							throws ClientProtocolException, IOException {

						if (response.getStatusLine().getStatusCode() == 200) {
							HttpEntity httpEntity = response.getEntity();
							Map obj = JsonUtils.toJsonObject(
									EntityUtils.toString(httpEntity), Map.class);
							List list = (List) obj.get("entities");
							Object imUserMap = list.get(0);
							ImUser user = BeanUtils.convert(imUserMap,
									ImUser.class);
							return user;
						} else {
							HttpEntity httpEntity = response.getEntity();
							Map obj = JsonUtils.toJsonObject(
									EntityUtils.toString(httpEntity), Map.class);
							String error = (String) obj.get("error");

							throw new IllegalStateException(
									"register im error:" + error);
						}
					}
				}, createHeader());

		user.setPassword(registInfo.getPassword());
		return user;
	}

	@Override
	public ImUser getImUser(String username) {
		String url = "https://a1.easemob.com/" + ORG + "/" + APP + "/users/"
				+ username;

		ImUser user = HttpUtils.get(url, new ResponseHandler<ImUser>() {

			@Override
			public ImUser handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = response.getEntity();
					Map obj = JsonUtils.toJsonObject(
							EntityUtils.toString(httpEntity), Map.class);
					List list = (List) obj.get("entities");
					Object imUserMap = list.get(0);
					ImUser user = BeanUtils.convert(imUserMap, ImUser.class);
					return user;
				} else {
					HttpEntity httpEntity = response.getEntity();
					Map obj = JsonUtils.toJsonObject(
							EntityUtils.toString(httpEntity), Map.class);
					String error = (String) obj.get("error");

					if ("service_resource_not_found".equals(error)) {
						// 查无此用户
						return null;
					}

					throw new IllegalStateException("get im user error:"
							+ error);
				}
			}
		}, createHeader());

		return user;
	}

	@Override
	public void changeNickname(String username, String nickname) {
		String url = "https://a1.easemob.com/" + ORG + "/" + APP + "/users/"
				+ username;

		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("nickname", nickname);

		ImUser user = HttpUtils.putJson(url, JsonUtils.toJsonString(parameter),
				new ResponseHandler<ImUser>() {

					@Override
					public ImUser handleResponse(HttpResponse response)
							throws ClientProtocolException, IOException {

						if (response.getStatusLine().getStatusCode() == 200) {
							HttpEntity httpEntity = response.getEntity();
							Map obj = JsonUtils.toJsonObject(
									EntityUtils.toString(httpEntity), Map.class);
							List list = (List) obj.get("entities");
							Object imUserMap = list.get(0);
							ImUser user = BeanUtils.convert(imUserMap,
									ImUser.class);
							return user;
						} else {
							HttpEntity httpEntity = response.getEntity();
							Map obj = JsonUtils.toJsonObject(
									EntityUtils.toString(httpEntity), Map.class);
							String error = (String) obj.get("error");

							throw new IllegalStateException(
									"change im nickname error:" + error);
						}
					}
				}, createHeader());
	}

	private Header createHeader() {
		return new BasicHeader("Authorization", authorization);
	}

	public static void main(String[] args) {
		EasemobImRegister reg = new EasemobImRegister();
		reg.authorization = "Bearer YWMtxITwdIBKEeSuwLU4jXd4bQAAAUtoabkNKwAbpi7a40RJbT-zNcLPhjmXl44";
		System.out.println(reg.getImUser("chat_user_00"));
		// reg.changeNickname("chat_user_0", "alexling");

		// ImRegistInfo regInfo = new ImRegistInfo();
		// regInfo.setUsername("chat_user_0");
		// regInfo.setPassword("123456");
		// ImUser imUser = reg.regiest(regInfo);
		// System.out.println(imUser);
	}

}
