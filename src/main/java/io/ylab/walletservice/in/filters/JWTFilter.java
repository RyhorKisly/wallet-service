package io.ylab.walletservice.in.filters;

import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.in.utils.JWTTokenHandler;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filter doing operations with jwt token such like validation
 */
@WebFilter(urlPatterns = "/users/*")
@Loggable
public class JWTFilter extends HttpFilter {

    /**
     * define a field with a type {@link JWTTokenHandler} for further aggregation
     */
    private final JWTTokenHandler jwtHandler;

    public JWTFilter() {
        jwtHandler = new JWTTokenHandler();
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        final String header = req.getHeader("Authorization");
        if (!header.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            chain.doFilter(req, res);
            return;
        }
        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtHandler.validate(token)) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", jwtHandler.getUser(token));
        chain.doFilter(req, res);

    }
}
