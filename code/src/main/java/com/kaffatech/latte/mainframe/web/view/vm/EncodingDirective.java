
package com.kaffatech.latte.mainframe.web.view.vm;

import com.kaffatech.latte.commons.net.http.util.UrlEncodingUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import java.io.IOException;
import java.io.Writer;

/**
 * @author wzm0903
 *
 */
public class EncodingDirective extends Directive {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.directive.Directive#getName()
	 */
	@Override
	public String getName() {
		return "encoding";
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
		String value = (String)((SimpleNode) node.jjtGetChild(0)).value(context);

		writer.write(UrlEncodingUtils.strictEncode(value));

		return true;
	}

}
