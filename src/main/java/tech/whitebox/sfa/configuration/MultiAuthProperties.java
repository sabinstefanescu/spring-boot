package tech.whitebox.sfa.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Catalin.Bobe on 1/26/2015.
 */
@ConfigurationProperties(prefix = "spring.multiauth")
public class MultiAuthProperties {

    private String loginUrl;

    private String logoutUrl;

    private String headerUser;

    private String headerPassword;

    private String webserviceUrl;

    private String applicationName;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getHeaderUser() {
        return headerUser;
    }

    public void setHeaderUser(String headerUser) {
        this.headerUser = headerUser;
    }

    public String getHeaderPassword() {
        return headerPassword;
    }

    public void setHeaderPassword(String headerPassword) {
        this.headerPassword = headerPassword;
    }

    public String getWebserviceUrl() {
        return webserviceUrl;
    }

    public void setWebserviceUrl(String webserviceUrl) {
        this.webserviceUrl = webserviceUrl;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
