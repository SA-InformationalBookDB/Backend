package hu.bme.szarch.ibdb.filter;

import hu.bme.szarch.ibdb.domain.Role;
import hu.bme.szarch.ibdb.filter.dto.UserInfo;
import hu.bme.szarch.ibdb.service.TokenService;
import hu.bme.szarch.ibdb.service.UserService;
import hu.bme.szarch.ibdb.service.dto.user.UserInfoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthenticationFilter extends GenericFilterBean {

    @Value("${ibdb.security.non-filtered-prefixes}")
    private String[] nonFilteredPrefixes;

    private TokenService tokenService;

    private UserService userService;

    public static final String userInfoAttribute = "x-ibdb-user";

    public AuthenticationFilter(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        if(shouldFilter(request)) {
            if(filter(request, response)) {
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean shouldFilter(HttpServletRequest request) {
        return !hasNonFilteredPrefix(request.getRequestURI());
    }

    private boolean filter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String accessToken = request.getHeader("Authorization");

        if(accessToken == null) {
            response.sendError(401, "NO_AUTHORIZATION_TOKEN_PROVIDED");
            return true;
        }

        Optional<String> userId = tokenService.getUserIdByAccessToken(accessToken);

        if(!userId.isPresent()) {
            response.sendError(401, "USER_NOT_FOUND");
            return true;
        }

        UserInfoResult userResult = userService.getUserInfo(userId.get());

        if(!userResult.isEnabled()) {
            response.sendError(403, "FORBIDDEN_USER");
        }

        if(isAdminEndpoint(request.getRequestURI()) && !userResult.getRole().equals(Role.ADMIN)) {
            response.sendError(401, "NO_ADMIN_PERMISSIONS");
            return true;
        } else if(!userResult.getRole().equals(Role.USER) && !userResult.getRole().equals(Role.ADMIN)) {
            response.sendError(401, "NO_PERMISSIONS");
            return true;
        }

        request.getSession().setAttribute(userInfoAttribute, new UserInfo(userId.get()));
        return false;
    }

    private boolean isAdminEndpoint(String uri) {
        return uri.toLowerCase().contains("admin");
    }

    private boolean hasNonFilteredPrefix(String uri) {
        return Arrays.stream(nonFilteredPrefixes).anyMatch(uri::startsWith);
    }

}
