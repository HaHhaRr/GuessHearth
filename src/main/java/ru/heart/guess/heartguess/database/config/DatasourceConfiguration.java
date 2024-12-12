package ru.heart.guess.heartguess.database.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.auth")
    public DataSourceProperties authDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.cards")
    public DataSourceProperties cardsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource authDataSource() {
        return authDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public DataSource cardsDataSource() {
        return cardsDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public JdbcTemplate authJdbcTemplate(@Qualifier("authDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate cardsJdbcTemplate(@Qualifier("cardsDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
