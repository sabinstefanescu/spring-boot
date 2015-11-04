package tech.whitebox.sfa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tech.whitebox.sfa.security.api.ApiAuthenticator;
import tech.whitebox.sfa.security.multiauth.MultiAuthEntryPoint;
import tech.whitebox.sfa.security.multiauth.MultiAuthProvider;
import tech.whitebox.sfa.security.multiauth.MultiAuthenticationFilter;
import ro.whitebox.multiauth.client.MultiAuthWebserviceClient;

@EnableConfigurationProperties(MultiAuthProperties.class)
@Configuration
public class SecurityConfiguration {

    @Autowired
    private MultiAuthProperties multiAuthProperties;

    @Configuration
    @Order(0)
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @EnableWebMvcSecurity
    public static class StaticResourcesConfigurationAdapter extends WebSecurityConfigurerAdapter {


        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.requestMatchers()
                        .antMatchers("/delogare/**", "/metrics/**","/health/**","/fonts/**", "/css/**", "/img/**", "/js/**", "/partials/**", "/**/favicon.ico")
                        .and()
                    .authorizeRequests()
                        .antMatchers("/**").permitAll().and()
                        .anonymous();
            http.exceptionHandling().accessDeniedPage("/access-denied");

        }

    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private ApiAuthenticator apiAuthenticationProvider;

        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.authenticationProvider(apiAuthenticationProvider)
                .requestMatchers()
                    .antMatchers("/api/**")
                    .and()
                .authorizeRequests()
                    .antMatchers("/**").hasRole("API").and()
                    .httpBasic();

        }
    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private MultiAuthEntryPoint multiAuthEntryPoint;

        @Autowired
        private MultiAuthProvider multiAuthProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            MultiAuthenticationFilter multiAuthenticationFilter = new MultiAuthenticationFilter(authenticationManager(), multiAuthEntryPoint);


            http.csrf().disable();
            http.requestMatchers()
                        .antMatchers("/**")
                        .and()
                    .authorizeRequests()
                    .antMatchers("/**")
                        .permitAll()
                        .and()
                    .addFilterBefore(multiAuthenticationFilter, BasicAuthenticationFilter.class);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(multiAuthProvider);
        }
    }

    @Bean
    public MultiAuthWebserviceClient multiAuthWebserviceClient(){
        MultiAuthWebserviceClient client = new MultiAuthWebserviceClient();
        client.setMultiAuthServiceEndpointUrlString(multiAuthProperties.getWebserviceUrl());
        return client;
    }


}

