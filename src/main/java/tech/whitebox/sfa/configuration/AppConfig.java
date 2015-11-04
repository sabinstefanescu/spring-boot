package tech.whitebox.sfa.configuration;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpStatus;
import tech.whitebox.sfa.utilities.ApplicationConstants;

import javax.naming.ConfigurationException;

@Configuration
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() throws ConfigurationException {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();

        propertyConfigurer.setLocations(new AbstractResource[] {

                new PathResource(getConfigurationPath() + "cama.properties") });



        return propertyConfigurer;
    }

    public static String getConfigurationPath() throws ConfigurationException{
        if (System.getProperty(ApplicationConstants.CONFIGURATION_PATH) != null){
            return System.getProperty(ApplicationConstants.CONFIGURATION_PATH);
        }

        if (System.getenv(ApplicationConstants.CONFIGURATION_PATH) != null){
            return System.getenv(ApplicationConstants.CONFIGURATION_PATH);
        }

        throw new ConfigurationException("Missing configuration environment parameter(path). Please add an environment variable called: " + ApplicationConstants.CONFIGURATION_PATH);

    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new MyCustomizer();
    }


    private static class MyCustomizer implements EmbeddedServletContainerCustomizer {

        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/not-allowed"));
            container.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/access-denied"));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error"));
        }

    }
}
