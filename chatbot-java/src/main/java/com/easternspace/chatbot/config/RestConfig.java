package com.easternspace.chatbot.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;

/**
 * Servlet Filter implementation class CorsFilter
 */
//@Configuration
public class RestConfig {


//	public void destroy() {
//		// TODO Auto-generated method stub
//	}
//
//	/**
//	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
//	 */
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		HttpServletResponse response2 = (HttpServletResponse) response;
//		response2.setHeader("Access-Control-Allow-Origin", "*");
//		response2.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH, HEAD");
//		response2.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,Authorization");
//
//		// pass the request along the filter chain
//		chain.doFilter(request, response2);
//	}
//
//	/**
//	 * @see Filter#init(FilterConfig)
//	 */
//	public void init(FilterConfig fConfig) throws ServletException {
//		// TODO Auto-generated method stub
//   }

}
