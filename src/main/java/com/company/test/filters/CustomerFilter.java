package com.company.test.filters;

import com.company.test.security.CustomerAuthenticationImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
public class CustomerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.debug("Inside CustomerFilter");
        byte[] bytes = Base64.getDecoder().decode(httpServletRequest.getHeader("Authorization"));
        String s = new String(bytes, StandardCharsets.UTF_8);
        SecurityContext securityContext = new SecurityContextImpl();
        CustomerAuthenticationImpl auth = new CustomerAuthenticationImpl();
        securityContext.setAuthentication(auth);
        auth.setName(s);
        SecurityContextHolder.setContext(securityContext);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
