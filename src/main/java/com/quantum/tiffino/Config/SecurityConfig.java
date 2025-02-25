package com.quantum.tiffino.Config;

import com.quantum.tiffino.Security.JwtAuthenticationFilter;
import com.quantum.tiffino.Security.JwtTokenUtil;
import com.quantum.tiffino.Services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/token").permitAll()
                        .requestMatchers("/api/admin/register").hasRole("SUPERADMIN")
                        .requestMatchers("/admin/dashboard").hasRole("ADMIN")
                        .requestMatchers("/api/register").permitAll()
                                .requestMatchers("/api/{id}/restaurants").permitAll()
                                .requestMatchers("/api/{id}/restaurants/{restaurantId}").permitAll()
                                .requestMatchers("/api/users/{id}").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/forgot-password", "/api/reset-password").permitAll()
                        .requestMatchers("/api/locations").permitAll()
                        .requestMatchers("/api/restaurants/create").permitAll()
                                .requestMatchers("/api/restaurants/update/{id}").permitAll()
                                .requestMatchers("/api/restaurants/{id}").permitAll()
                                .requestMatchers("/api/restaurants/add-delivery-person/{restaurantId}/{deliveryPersonId}").permitAll()
//                        .requestMatchers("/menus/create").permitAll()
                                .requestMatchers("/api/menus").permitAll()
                                .requestMatchers("/api/menus/{menuId}/uploadImage").permitAll()
                                .requestMatchers("/api/menus/{menuId}").permitAll()
                                .requestMatchers("/orders").permitAll()
                                .requestMatchers("/api/regions").permitAll()
                                .requestMatchers("/api/memberships/assign/{user_id}").permitAll()
                                .requestMatchers("/api/mealplans").permitAll()
                                .requestMatchers("/api/ingredients").permitAll()
                                .requestMatchers("/api/meal-availability").permitAll()
                                .requestMatchers("/api/tickets").permitAll()
                                .requestMatchers("/meal-customizations/").permitAll()
                                .requestMatchers("/giftcard/").permitAll()
                                .requestMatchers("/api/delivery-persons/create").permitAll()
                                .requestMatchers("/deliveries").permitAll()
                                .requestMatchers("/api/meal-replacement-policies").permitAll()
                                .requestMatchers("/rewards/add").permitAll()
                                .requestMatchers("/api/coupons").permitAll()
                                .requestMatchers("/payments").permitAll()
                                .requestMatchers("/api/reviews").permitAll()
                                .requestMatchers("/referrals").permitAll()
                                .requestMatchers("/api/bills").permitAll()
                                .requestMatchers("/api/subscriptions/create").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenUtil, customUserDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
