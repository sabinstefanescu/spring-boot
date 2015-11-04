package tech.whitebox.sfa.infrastructure.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Catalin.Bobe on 1/30/2015.
 */
public class InvalidTokenException extends AuthenticationException {
    public InvalidTokenException(String msg) {
        super(msg);
    }
}
