package com.lemakhno.shopping.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lemakhno.shopping.constants.Endpoints;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String SECURED = "/products/**";
    private static final String COOKIE_JSESSIONID = "JSESSIONID";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        .csrf()
            .disable()
        .headers()
            .frameOptions().disable()
        .and()
        .authorizeHttpRequests()
            .requestMatchers(PathRequest.toH2Console())
                .permitAll()
		    .antMatchers(SECURED)
                .authenticated()
		.and()
            .formLogin()
            .loginPage(Endpoints.LOGIN_PAGE)
            .loginProcessingUrl(Endpoints.AUTHENTICATION)
            .defaultSuccessUrl(Endpoints.PRODUCTS_LIST, true)
            .permitAll()
        .and()
            .logout()
            .logoutUrl(Endpoints.LOGOUT)
            .deleteCookies(COOKIE_JSESSIONID)
		.and()
            .logout()
            .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
