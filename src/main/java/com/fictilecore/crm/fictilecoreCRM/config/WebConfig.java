package com.fictilecore.crm.fictilecoreCRM.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configure global CORS settings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")          // allow all endpoints
                .allowedOrigins("*")        // allow all origins, change to your frontend URL in production
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)   // set true if using cookies/auth
                .maxAge(3600);
    }

    /**
     * Configure Jackson ObjectMapper globally
     * - Properly serialize Java 8 LocalDate/LocalDateTime
     * - Pretty-print JSON
     */
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.registerModule(new JavaTimeModule()); // support for Java 8 date/time
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO format
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
        return objectMapper;
    }
}

    

