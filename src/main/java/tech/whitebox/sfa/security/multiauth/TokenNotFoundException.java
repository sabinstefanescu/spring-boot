package tech.whitebox.sfa.security.multiauth;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Catalin.Bobe on 1/30/2015.
 */
public class TokenNotFoundException extends AuthenticationException {
    public TokenNotFoundException(String msg) {
        super(msg);
    }
}
