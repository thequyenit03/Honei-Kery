package com.example.service.config;

import com.example.service.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Tắt CSRF vì dùng JWT
                .authorizeHttpRequests(auth -> auth
                        // 1. PUBLIC: Ai cũng vào được (Không cần đăng nhập)
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/logout").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/products**", "/api/categories**").permitAll()

                        // 2. ADMIN ONLY: Chỉ Admin mới được Thêm/Sửa/Xóa
                        // Lưu ý: hasRole("ADMIN") tương đương với việc kiểm tra quyền "ROLE_ADMIN" trong Database
                        .requestMatchers(HttpMethod.GET, "/api/admin/products/**", "/api/admin/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/admin/products/**", "/api/admin/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/admin/products/**", "/api/admin/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/admin/products/**", "/api/admin/categories/**").hasRole("ADMIN")

                        // 3. AUTHENTICATED: Các chức năng còn lại chỉ cần Đăng nhập (User/Admin đều được)
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. Cho phép các domain của Frontend
        // Sử dụng setAllowedOriginPatterns("*") cho phép mọi domain (localhost, vercel, mobile...)
        // đều có thể gọi API. Đây là cách an toàn để dùng "*" kèm với allowCredentials(true).
        configuration.setAllowedOriginPatterns(List.of("*"));

        // 2. Cho phép các method
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));

        // 3. Cho phép Header
        // Quan trọng: Phải cho phép "Authorization" để Client gửi JWT lên được
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "x-auth-token"));

        // 4. Cho phép gửi Credentials (Cookie, Auth)
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}