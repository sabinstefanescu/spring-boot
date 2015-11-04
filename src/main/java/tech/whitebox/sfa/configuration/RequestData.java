package tech.whitebox.sfa.configuration;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import tech.whitebox.sfa.domain.users.User;

import java.io.Serializable;

/**
 * @author razvan.reff
 *
 */

@Component
@Scope(value= WebApplicationContext.SCOPE_REQUEST, proxyMode= ScopedProxyMode.TARGET_CLASS)
public class RequestData implements Serializable{

    private String token;

    private User user;

    public RequestData() {}

    public String getToken() {
        return token;
    }

    public void init(User user, String token){
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }


}
