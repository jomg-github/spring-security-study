package just.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(
                        new MvcRequestMatcher(introspector, "/"),
                        new MvcRequestMatcher(introspector,"/info")
                )
                .permitAll()
                .requestMatchers(
                        new MvcRequestMatcher(introspector, "/admin")
                )
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
        );


        http.formLogin();
        http.httpBasic();

        return http.build();
    }
}
