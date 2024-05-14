package com.epam.course.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtTokenFilter extends GenericFilterBean {
    private static final String BEARER = "Bearer";

    private CourseUserDetailsService courseUserDetailsService;

    public JwtTokenFilter(CourseUserDetailsService courseUserDetailsService) {
        this.courseUserDetailsService = courseUserDetailsService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("Checking JWT token");

        String headerValue = ((HttpServletRequest)req).getHeader("Authorization");
        getBearerToken(headerValue).flatMap(token -> courseUserDetailsService.loadUserByJwtToken(token)).ifPresent(userDetails ->
            SecurityContextHolder.getContext().setAuthentication(
                    new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities())));

        filterChain.doFilter(req, res);
    }

    private Optional<String> getBearerToken(String headerVal) {
        if (headerVal != null && headerVal.startsWith(BEARER)) {
            return Optional.of(headerVal.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }
}
