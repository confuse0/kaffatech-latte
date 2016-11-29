
package com.kaffatech.latte.mainframe.web.spring.web.util;

import java.util.HashMap;
import java.util.Map;

import com.kaffatech.latte.commons.bean.transfer.util.TransferUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author zhen.ling
 *
 */
public class ModelAndViewUtils {

	public static ModelAndView create(String viewName) {
		return create(viewName, null, null);
	}

	public static ModelAndView create(String viewName, Object res) {
		return create(viewName, res, null);
	}

	public static ModelAndView create(String viewName, Object res, Object req) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 默认进行html转义
		model.put("res", TransferUtils.transfer(res));
		model.put("req", req);

		return new ModelAndView(viewName, model);
	}

	public static ModelAndView createForRedirect(String url) {
		return new ModelAndView(new RedirectView(url));
	}

}
