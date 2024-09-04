package com.graduationproject.isn.configs;

import com.graduationproject.isn.auth.PriceAlertAuthenticationProvider;
import com.graduationproject.isn.auth.filters.ExceptionHandlerAdviceFilter;
import com.graduationproject.isn.auth.filters.JwtAuthFilter;
import com.graduationproject.isn.domain.constants.AuthConstants;
import com.graduationproject.isn.repositories.IdentityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${spring.mvc.servlet.path}")
    private String servletPath;

    private final IdentityRepository identityRepository;

    private final PriceAlertAuthenticationProvider authenticationProvider;

    private final ExceptionHandlerAdviceFilter exceptionHandlerAdviceFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(http -> {
                http.anyRequest().permitAll();
            })
            .addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(exceptionHandlerAdviceFilter, LogoutFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider);
    }

    private JwtAuthFilter getAuthenticationFilter() {
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(identityRepository, getNoAuthRequestMatcher());
        return jwtAuthFilter;
    }

    private RequestMatcher getNoAuthRequestMatcher() {
        return new NegatedRequestMatcher(new AntPathRequestMatcher(
            servletPath + AuthConstants.NO_AUTH_PATHS));
    }
}