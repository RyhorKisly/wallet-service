package io.ylab.walletservice.in.filters;

import io.ylab.walletservice.in.utils.JWTTokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter doing operations with jwt token such like validation
 */
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    /**
     * define a field with a type {@link JWTTokenHandler} for further aggregation
     */
    private final JWTTokenHandler jwtHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        final String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            if(req.getRequestURL().toString().contains("/register") || req.getRequestURL().toString().contains("/login")) {
                chain.doFilter(req, res);
                return;
            }
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
