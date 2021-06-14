package com.company.test.filters;

import com.company.test.security.CustomerAuthenticationImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Optional;

@Slf4j
@Component
public class CustomerFilter extends OncePerRequestFilter {

    @Value("${whitelist.url}")
    private String whiteListedUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.debug("Filtering incoming request for Authorization");
        if(!isWhiteListed(httpServletRequest.getRequestURI())) {
            attachRoleToSession(httpServletRequest.getHeader("Authorization"));
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private Boolean isWhiteListed(String uri) {
        return uri.equals(whiteListedUrl);
    }

    private void attachRoleToSession(String authorization) {
        byte[] bytes = Base64.getDecoder().decode(Optional.ofNullable(authorization)
                .orElse("UkVBREVS"));
        String s = new String(bytes, StandardCharsets.UTF_8);
        SecurityContext securityContext = new SecurityContextImpl();
        CustomerAuthenticationImpl auth = new CustomerAuthenticationImpl();
        securityContext.setAuthentication(auth);
        auth.setName(s);
        SecurityContextHolder.setContext(securityContext);
    }
}
