// package com.team_quddy.quddy.global.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// @EnableWebMvc
// public class WebConfig implements WebMvcConfigurer {
// @Override
// public void addCorsMappings(final CorsRegistry registry) {
// registry.addMapping("/**")
// .allowedOrigins("https://localhost:5173", "http://localhost:5173",
// "https://quddy.xyz",
// "http://quddy.xyz")
// .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
// .allowCredentials(true)
// .maxAge(3000);
// }
// }