package tech.whitebox.sfa.security.multiauth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class MultiAuthToken extends AbstractAuthenticationToken {

    private String token;

    private String overwriteUsername;

    public MultiAuthToken() {
        super(null);
    }

    public MultiAuthToken(List<GrantedAuthority> grantedAuthorities) {
        super(grantedAuthorities);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOverwriteUsername() {
        return overwriteUsername;
    }

    public void setOverwriteUsername(String overwriteUsername) {
        this.overwriteUsername = overwriteUsername;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
