package com.fmi.wdj.booklibrary.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "config")
@Configuration
@Data
public class AppConfig {
    private LoggerConfig loggerConfig = new LoggerConfig();

    // Need to implement a logger or use a logging library :)
    @ConfigurationProperties(prefix = "logger")
    @Configuration
    @Data
    static class LoggerConfig {
        private String level;
    }

    @ConfigurationProperties(prefix = "book")
    @Configuration
    @Data
    static class BookConfig {
        private int isbnLength;
    }
}
