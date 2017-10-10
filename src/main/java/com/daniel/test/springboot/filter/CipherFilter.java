package com.daniel.test.springboot.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/9/25 17:41
 */
public class CipherFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        StringBuffer requestURL = httpRequest.getRequestURL();

        System.out.println("filter !!!!!!! == " + requestURL);
    }

    @Override
    public void destroy() {
        System.out.println("filter say goodby");
    }
}
