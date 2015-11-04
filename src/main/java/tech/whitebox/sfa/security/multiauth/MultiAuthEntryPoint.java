package tech.whitebox.sfa.security.multiauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tech.whitebox.sfa.configuration.MultiAuthProperties;
import tech.whitebox.sfa.utilities.ApplicationConstants;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Catalin.Bobe.
 */
@Order
@Component
public class MultiAuthEntryPoint implements AuthenticationEntryPoint {


    @Autowired
    private MultiAuthProperties multiAuthProperties;

    @Autowired
    private ServerProperties serverProperties;

    private String redirectUrl;

    @PostConstruct
    public void init() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();

        if (serverProperties.getPort() == 80) {
            redirectUrl = String.format("http://%s%s%s","%s", serverProperties.getContextPath(),"%s");
        }

        redirectUrl = String.format("http://%s%s%s","%s", serverProperties.getContextPath(),"%s");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if (authException instanceof MultiAuthConnectionException) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, authException.getMessage());
            return;
        }

        if (request.getHeader(ApplicationConstants.ANGULAR_HEADER) == null){
            response.addHeader("Location", String.format("%s?serviceURL=%s", multiAuthProperties.getLoginUrl(), String.format(redirectUrl,request.getHeader("Host"),request.getServletPath())));
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        }
        else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
        }



    }


}
