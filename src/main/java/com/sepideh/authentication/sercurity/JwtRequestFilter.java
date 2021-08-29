package com.sepideh.authentication.sercurity;

import com.sepideh.notebook.model.User;
import com.sepideh.notebook.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final static String KEY_HEADER_TOKEN = "Authorization";

    private final CustomUserDetailsService userService;
    private final JwtUtil jwtUtil;

    // Constructor *****************************************************************************************************
    public JwtRequestFilter(CustomUserDetailsService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    //******************************************************************************************************************
    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader(KEY_HEADER_TOKEN);

        if (requestTokenHeader != null) {
            String username = null;

            try {
                username = jwtUtil.getUsernameFromToken(requestTokenHeader);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            } catch (SignatureException e) {
                System.out.println("JWT signature does not match locally computed signature");
            }

            boolean isTokenValid = !jwtUtil.isTokenExpired(requestTokenHeader);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && isTokenValid) {
                User user = (User) userService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                user, null, user.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
