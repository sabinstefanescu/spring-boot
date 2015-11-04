package tech.whitebox.sfa.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import java.util.Arrays;
import java.util.List;

@Configuration
public class IgnoredPathsFromSecurityConfiguration implements WebSecurityConfigurer<WebSecurity> {

    private static List<String> IGNORED_PATHS = Arrays.asList("/fonts/**", "/css/**", "/img/**", "/js/**", "/partials/**", "/**/favicon.ico");


    public void init(WebSecurity builder) throws Exception {
        WebSecurity.IgnoredRequestConfigurer ignoring = builder.ignoring();
        ignoring.antMatchers(IGNORED_PATHS.toArray(new String[0]));

    }

    public void configure(WebSecurity builder) throws Exception {

    }
}