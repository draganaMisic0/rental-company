package org.etfbl.iprental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CORSFilter {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOriginPatterns(List.of("http://localhost:*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                "Origin", "Content-Type", "Accept", "Authorization",
                "Origin", "Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers",
                "Content-Disposition", "Access-Control-Allow-Origin"));
        corsConfiguration.setExposedHeaders(Arrays.asList(
                "Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials",
                "Content-Disposition"));
        corsConfiguration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
