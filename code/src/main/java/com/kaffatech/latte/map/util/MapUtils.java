
package com.kaffatech.latte.map.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kaffatech.latte.map.model.Address;
import com.kaffatech.latte.map.model.Location;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.kaffatech.latte.ctx.base.SystemProperties;
import com.kaffatech.latte.commons.net.http.util.HttpUtils;
import com.kaffatech.latte.commons.json.util.JsonUtils;
import com.kaffatech.latte.commons.toolkit.base.CollectionUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import com.kaffatech.latte.commons.net.http.util.UrlEncodingUtils;

/**
 * @author zhen.ling
 *
 */
public class MapUtils {

	private static final String AK = "lnzNNISYiHFkKqAjYoZfHvbX";

	private static final String DEV_AK = "lnzNNISYiHFkKqAjYoZfHvbX";

	public static Address getAddress(String keyword, String region) {
		List<Address> addressList = getAddressList(keyword, region);
		return CollectionUtils.isEmpty(addressList) ? null : addressList.get(0);
	}

	public static List<Address> getAddressList(String keyword, String region) {
		if (StringUtils.isEmpty(StringUtils.trim(keyword))) {
			return new ArrayList<Address>();
		}

		String url = "http://api.map.baidu.com/place/v2/search?query="
				+ UrlEncodingUtils.strictEncode(keyword) + "&region="
				+ UrlEncodingUtils.strictEncode(region)
				+ "&scope=1&output=json&ak=" + getAk();

		return HttpUtils.get(url, new ResponseHandler<List<Address>>() {

			@Override
			public List<Address> handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = response.getEntity();
					Map obj = JsonUtils.toJsonObject(
							EntityUtils.toString(httpEntity), Map.class);
					Integer status = Integer.parseInt(obj.get("status")
							.toString());
					if (status != 0) {
						throw new IllegalStateException("getAddressList error:"
								+ "[" + status + "]" + obj);
					}

					List<Address> list = convert((List) obj.get("results"));
					return list;
				} else {
					throw new IllegalStateException(
							"getLocalLocation unknown error:"
									+ response.getStatusLine().getStatusCode());
				}
			}
		});
	}

	private static List<Address> convert(List result) {
		if (CollectionUtils.isEmpty(result)) {
			return new ArrayList<Address>();
		}

		List<Address> addressList = new ArrayList<Address>();
		for (Object each : result) {
			Map item = (Map) each;
			Address address = new Address();
			address.setName((String) item.get("name"));
			address.setDetail((String) item.get("address"));

			Map locMap = (Map) item.get("location");
			Location location = new Location();
			location.setLat(locMap == null ? null : (Double) locMap.get("lat"));
			location.setLng(locMap == null ? null : (Double) locMap.get("lng"));
			address.setLocation(location);

			addressList.add(address);
		}

		return addressList;
	}

	/**
	 * @return
	 */
	public static String getAk() {
		if (SystemProperties.isProduction()) {
			return AK;
		}
		return DEV_AK;
	}

	public static void main(String[] args) {
		System.out.println(getAddressList("渔阳置业大厦b座12321rws", "北京"));
	}
}
