
package com.kaffatech.latte.mainframe.web.view.vm;

import java.io.IOException;
import java.io.Writer;

import com.kaffatech.latte.commons.bean.transfer.util.EscapeHtmlUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.elasticsearch.common.lang3.StringUtils;

/**
 * @author zhen.ling
 *
 */
public class CutDirective extends Directive {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.directive.Directive#getName()
	 */
	@Override
	public String getName() {
		return "cut";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.directive.Directive#getType()
	 */
	@Override
	public int getType() {
		return LINE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.velocity.runtime.directive.Directive#render(org.apache.velocity
	 * .context.InternalContextAdapter, java.io.Writer,
	 * org.apache.velocity.runtime.parser.node.Node)
	 */
	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {
		// 获得参数
		String value = (String) ((SimpleNode) node.jjtGetChild(0))
				.value(context);
		Integer maxLen = (Integer) ((SimpleNode) node.jjtGetChild(1))
				.value(context);

		// 反转html转义
		value = EscapeHtmlUtils.reversalHtml(value);

		if (StringUtils.isEmpty(value) || value.length() <= maxLen) {
			value = EscapeHtmlUtils.escapeHtml(value);
		} else {
			// 剪裁
			value = EscapeHtmlUtils.escapeHtml(value.substring(0, maxLen))
					+ "...";
		}
		
		if(StringUtils.isEmpty(value)) {
			value = "";
		}
		writer.write(value);

		return true;
	}

}
