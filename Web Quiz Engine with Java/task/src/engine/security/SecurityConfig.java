package engine.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(Customizer.withDefaults())     // Default Basic auth config
                .csrf(configurer -> configurer.disable()) // for POST requests via Postman
                .headers(headers -> headers.frameOptions().disable()) // For the H2 console
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(HttpMethod.POST,"/actuator/shutdown").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/quizzes").authenticated()
                        .antMatchers(HttpMethod.POST, "/api/quizzes/**").authenticated()
                        .antMatchers(HttpMethod.GET, "/api/quizzes").authenticated()
                        .antMatchers(HttpMethod.GET, "/api/quizzes/**").authenticated()
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
