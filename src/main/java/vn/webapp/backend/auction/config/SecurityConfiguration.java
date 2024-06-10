package vn.webapp.backend.auction.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import vn.webapp.backend.auction.enums.Role;
import vn.webapp.backend.auction.filter.JwtAuthenticationFilter;
import vn.webapp.backend.auction.security.Endpoints;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    @CrossOrigin
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        configurer -> configurer
                                .requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers("/ws/**").permitAll()
                                .requestMatchers(HttpMethod.GET, Endpoints.PUBLIC_GET_ENDPOINTS).permitAll()
                                .requestMatchers(HttpMethod.POST, Endpoints.PUBLIC_POST_ENDPOINTS).permitAll()
                                .requestMatchers(HttpMethod.PUT, Endpoints.PUBLIC_PUT_ENDPOINTS).permitAll()
                                .requestMatchers(HttpMethod.GET, Endpoints.MANAGER_GET_ENDPOINTS).hasAuthority(Role.MANAGER.name())
                                .requestMatchers(HttpMethod.POST, Endpoints.MANAGER_POST_ENDPOINTS).hasAuthority(Role.MANAGER.name())
                                .requestMatchers(HttpMethod.PUT, Endpoints.MANAGER_PUT_ENDPOINTS).hasAuthority(Role.MANAGER.name())
                                .requestMatchers(HttpMethod.DELETE, Endpoints.MANAGER_DELETE_ENDPOINTS).hasAuthority(Role.MANAGER.name())
                                .requestMatchers(HttpMethod.GET, Endpoints.ADMIN_GET_ENDPOINTS).hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, Endpoints.ADMIN_POST_ENDPOINTS).hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, Endpoints.ADMIN_PUT_ENDPOINTS).hasAuthority(Role.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .httpBasic(Customizer.withDefaults())
//                .formLogin(formLogin ->
//                        formLogin.loginPage("/api/v1/auth/login").permitAll()
//                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        return http.build();
    }
}