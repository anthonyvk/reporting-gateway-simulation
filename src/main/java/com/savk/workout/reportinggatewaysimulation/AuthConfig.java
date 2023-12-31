package com.savk.workout.reportinggatewaysimulation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**")
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> {
                            try {
                                auth
                                        .requestMatchers("/secured/**")
                                        .authenticated()
                                        .and()
                                        .x509()
                                        .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                                        .userDetailsService(userDetailsService());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                )
                .build();


//        return http.authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .x509()
//                .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
//                .userDetailsService(userDetailsService())
//                .and().build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if (username != null) {
                    return new User(username, "",
                            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
                }
                throw new UsernameNotFoundException("User not found!");
            }
        };
    }

}
