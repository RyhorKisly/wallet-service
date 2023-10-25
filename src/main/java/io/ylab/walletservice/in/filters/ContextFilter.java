package io.ylab.walletservice.in.filters;

import io.ylab.walletservice.aop.annotations.Loggable;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * Filter for setting character encoding and content type of the response being sent to the client
 */
@WebFilter(urlPatterns = "/*")
@Loggable
public class ContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html; charset=UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}