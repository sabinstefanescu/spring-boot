package tech.whitebox.sfa.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Catalin.Bobe.
 */
@ConfigurationProperties(prefix = "spring.hibernate")
public class HibernateProperties {


    private String showSql = "true";
    private String dialect = "org.hibernate.dialect.Oracle10gDialect";
    private String useSqlComments = "true";
    private String useQueryCache = "false";
    private String useSecondLevel = "false";
    private String generateStatistics = "false";
    private String formatSql = "true";


    public String getShowSql() {
        return showSql;
    }

    public void setShowSql(String showSql) {
        this.showSql = showSql;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getUseSqlComments() {
        return useSqlComments;
    }

    public void setUseSqlComments(String useSqlComments) {
        this.useSqlComments = useSqlComments;
    }

    public String getUseQueryCache() {
        return useQueryCache;
    }

    public void setUseQueryCache(String useQueryCache) {
        this.useQueryCache = useQueryCache;
    }

    public String getUseSecondLevel() {
        return useSecondLevel;
    }

    public void setUseSecondLevel(String useSecondLevel) {
        this.useSecondLevel = useSecondLevel;
    }

    public String getGenerateStatistics() {
        return generateStatistics;
    }

    public void setGenerateStatistics(String generateStatistics) {
        this.generateStatistics = generateStatistics;
    }

    public String getFormatSql() {
        return formatSql;
    }

    public void setFormatSql(String formatSql) {
        this.formatSql = formatSql;
    }
}
