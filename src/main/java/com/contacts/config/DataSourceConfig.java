package com.contacts.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties properties, Environment env) throws URISyntaxException {
        String databaseUrl = env.getProperty("DATABASE_URL");

        if (StringUtils.hasText(databaseUrl)) {
            URI uri = new URI(databaseUrl);

            if (!databaseUrl.startsWith("jdbc:")) {
                int port = uri.getPort() == -1 ? 5432 : uri.getPort();
                properties.setUrl(String.format("jdbc:postgresql://%s:%d%s", uri.getHost(), port, uri.getPath()));
            } else if (!StringUtils.hasText(properties.getUrl())) {
                properties.setUrl(databaseUrl);
            }

            if (!StringUtils.hasText(properties.getUsername())) {
                String userInfo = uri.getUserInfo();
                if (userInfo != null && userInfo.contains(":")) {
                    properties.setUsername(userInfo.split(":", 2)[0]);
                }
            }

            if (!StringUtils.hasText(properties.getPassword())) {
                String userInfo = uri.getUserInfo();
                if (userInfo != null && userInfo.contains(":")) {
                    properties.setPassword(userInfo.split(":", 2)[1]);
                }
            }
        }

        return properties.initializeDataSourceBuilder().build();
    }
}
