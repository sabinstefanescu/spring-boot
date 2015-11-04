package tech.whitebox.sfa.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import tech.whitebox.sfa.configuration.RequestData;
import tech.whitebox.sfa.infrastructure.log.AppLogger;

import java.util.ArrayList;
import java.util.List;


@Component
public class ApiAuthenticator implements AuthenticationProvider {
    private AppLogger log = new AppLogger(this);
    
    @Autowired
    private RequestData requestData;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (isUserValid(username, password)) {
            log.info("AUTHENTICATED", String.format("User <%s>  authenticated successfully!", username));
            return getAuthToken(username, password);
        }
        log.warn("AUTHENTICATION_FAILED", String.format("User <%s> failed to authenticate!", username));

        return null;
    }

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);

    }

    private UsernamePasswordAuthenticationToken getAuthToken(String userName, String password) {
        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_API"));
        return new UsernamePasswordAuthenticationToken(userName, password, grantedAuths);
    }

    private boolean isUserValid(String username, String password) {
       /* if (username != null && password != null){
            return true;
        }
        return false;*/
        return true;
    }
}
