package tech.whitebox.sfa.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Properties;

@EnableConfigurationProperties(HibernateProperties.class)
@Configuration
public class HibernateConfiguration {


    @Autowired
    private HibernateProperties hibernateProperties;

    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("ro.whitebox.cama.domain.entities");

        Properties properties = new Properties();
        properties.put("hibernate.show_sql", hibernateProperties.getShowSql());
        properties.put("hibernate.dialect", hibernateProperties.getDialect());

        properties.put("hibernate.use_sql_comments", hibernateProperties.getUseSqlComments());
        properties.put("hibernate.cache.use_query_cache", hibernateProperties.getUseQueryCache());
        properties.put("hibernate.cache.use_second_level_cache",hibernateProperties.getUseSecondLevel());
        properties.put("hibernate.generate_statistics", hibernateProperties.getGenerateStatistics());
        properties.put("hibernate.format_sql", hibernateProperties.getFormatSql());

        properties.put("flushMode", "ALWAYS");

        factoryBean.setHibernateProperties(properties);

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        return new HibernateTransactionManager(sessionFactory().getObject());
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper om = new HibernateAwareObjectMapper();

        SimpleFilterProvider filters = new SimpleFilterProvider();
        filters.setDefaultFilter(new SimpleBeanPropertyFilter.SerializeExceptFilter(new HashSet<String>()));

        om.setFilters(filters);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.disable(MapperFeature.USE_GETTERS_AS_SETTERS);

        return om;
    }
}
