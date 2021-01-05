package com.hehedahehe.rest.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.servlet.*;
import java.io.IOException;


/**
 *
 * Servlet容器中，可以直接实用Filter机制Hystrix请求上下文
 *  <filter>
 *        <display-name>HystrixRequestContextServletFilter</display-name>
 *        <filter-name>HystrixRequestContextServletFilter</filter-name>
 *        <filter-class>com.netflix.hystrix.contrib.requestservlet.HystrixRequestContextServletFilter</filter-class>
 *      </filter>
 *      <filter-mapping>
 *        <filter-name>HystrixRequestContextServletFilter</filter-name>
 *        <url-pattern>/*</url-pattern>
 *     </filter-mapping>
 */
public class HystrixRequestContextServletFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			context.shutdown();
		}
	}
}
