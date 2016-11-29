
package com.kaffatech.latte.mainframe.web.view.vm;

import java.io.IOException;
import java.io.Writer;

import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

/**
 * @author wzm0903
 *
 */
public class WrapDirective extends Directive {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.directive.Directive#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "wrap";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.directive.Directive#getType()
	 */
	@Override
	public int getType() {
		// TODO Auto-generated method stub
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
		String value = ((SimpleNode) node.jjtGetChild(0)).value(context)
				.toString();

		String replaceStr = "";
		if (node.jjtGetNumChildren() > 1) {
			// 存在替换参数
			replaceStr = ((SimpleNode) node.jjtGetChild(1)).value(context)
					.toString();
		}

		if (!StringUtils.isEmpty(value)) {
			value = value.replace("\r", "").replace("\n", replaceStr);
		}
		writer.write(value);
		return true;
	}

}
