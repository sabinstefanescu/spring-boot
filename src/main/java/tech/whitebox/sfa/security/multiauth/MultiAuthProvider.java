package tech.whitebox.sfa.security.multiauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ro.whitebox.multiauth.client.MultiAuthWebserviceClient;
import tech.whitebox.sfa.configuration.MultiAuthProperties;
import tech.whitebox.sfa.configuration.RequestData;
import tech.whitebox.sfa.domain.users.User;
import tech.whitebox.sfa.infrastructure.exceptions.InvalidTokenException;

@Component
public class MultiAuthProvider implements AuthenticationProvider {


    @Autowired
    private RequestData requestData;

    @Autowired
    private MultiAuthWebserviceClient multiAuthWebserviceClient;

    @Autowired
    private MultiAuthProperties multiAuthProperties;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication == null) {
            throw new IllegalArgumentException("Authentication object is null");
        }

        MultiAuthToken multiAuthToken = (MultiAuthToken) authentication;

        if (multiAuthToken.getToken() != null) {

            User user = null;

            requestData.init(user, multiAuthToken.getToken());

            MultiAuthToken grantedAuthentication = new MultiAuthToken();
            grantedAuthentication.setToken(multiAuthToken.getToken());
            grantedAuthentication.setOverwriteUsername(multiAuthToken.getOverwriteUsername());

            grantedAuthentication.setAuthenticated(true);

            SecurityContextHolder.getContext().setAuthentication(grantedAuthentication);

            return grantedAuthentication;
        } else {
            throw new InvalidTokenException("Token not found");
        }
    }

    public boolean supports(Class<?> authentication) {
        return MultiAuthToken.class.isAssignableFrom(authentication);
    }


}
