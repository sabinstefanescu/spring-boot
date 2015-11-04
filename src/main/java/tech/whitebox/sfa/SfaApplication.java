package tech.whitebox.sfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackages = {"tech.whitebox"})
@EnableAutoConfiguration
@EnableAsync
@EnableSpringConfigured
//@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableCaching(mode = AdviceMode.ASPECTJ, order = Ordered.HIGHEST_PRECEDENCE)
public class SfaApplication {



    public static void main(String[] args) {
        SpringApplication.run(SfaApplication.class, args);
    }
}
