package tech.whitebox.sfa.security.multiauth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;
import tech.whitebox.sfa.infrastructure.exceptions.NoAccessException;
import tech.whitebox.sfa.infrastructure.log.AppLogger;
import tech.whitebox.sfa.utilities.ApplicationConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MultiAuthenticationFilter extends GenericFilterBean {


    private static final String MULTIAUTH_TOKEN = "token";

    private static final String OVERWRITE_USERNAME_HEADER = "X-Overwrite-Username";

    private AuthenticationManager authenticationManager;

    private AuthenticationEntryPoint authenticationEntryPoint;

    private AppLogger logger = new AppLogger(this);


    public MultiAuthenticationFilter(AuthenticationManager authenticationManager,
                                     AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String token = getRequestSecurityToken(request);
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String overwriteUsername = getOverwriteUsername(request);

        try {
            MultiAuthToken authRequest = new MultiAuthToken();
            authRequest.setToken(token);
            authRequest.setOverwriteUsername(overwriteUsername);

            Authentication authResult = authenticationManager.authenticate(authRequest);

        } catch (AuthenticationException failed) {
            authenticationEntryPoint.commence(request, response, failed);
            return;
        } catch (NoAccessException e){
            logger.error("USER_NOT_ALLOWED", e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(request, response);
    }

    private String getRequestSecurityToken(HttpServletRequest servletRequest){
        if (servletRequest.getParameter(MULTIAUTH_TOKEN) != null){
            return servletRequest.getParameter(MULTIAUTH_TOKEN);
        }

        if (servletRequest.getHeader(ApplicationConstants.AUTH_TOKEN_HEADER) != null){
            return servletRequest.getHeader(ApplicationConstants.AUTH_TOKEN_HEADER);
        }

        return null;
    }

    private String getOverwriteUsername(HttpServletRequest servletRequest){

        if (servletRequest.getHeader(OVERWRITE_USERNAME_HEADER) != null){
            return servletRequest.getHeader(OVERWRITE_USERNAME_HEADER);
        }

        return null;
    }


    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.authenticationManager, "An AuthenticationManager is required");
    }

}
