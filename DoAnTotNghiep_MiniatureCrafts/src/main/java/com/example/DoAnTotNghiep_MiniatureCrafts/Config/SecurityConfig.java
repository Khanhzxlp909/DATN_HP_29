//    package com.example.DoAnTotNghiep_MiniatureCrafts.Config;
//
//    import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Users;
//    import com.example.DoAnTotNghiep_MiniatureCrafts.Service.Security.UserService;
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.context.annotation.Bean;
//    import org.springframework.context.annotation.Configuration;
//    import org.springframework.security.authentication.AuthenticationManager;
//    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//    import org.springframework.security.config.http.SessionCreationPolicy;
//    import org.springframework.security.core.userdetails.User;
//    import org.springframework.security.core.userdetails.UserDetails;
//    import org.springframework.security.core.userdetails.UserDetailsService;
//    import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//    import org.springframework.security.web.SecurityFilterChain;
//    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//    import org.springframework.security.crypto.password.PasswordEncoder;
//    import org.springframework.web.cors.CorsConfiguration;
//    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//    import org.springframework.web.cors.CorsConfigurationSource;
//
//    import java.util.Arrays;
//    import java.util.List;
//
//    import static org.springframework.security.config.Customizer.withDefaults;
//
//    @Configuration
//    @EnableWebSecurity
//    public class SecurityConfig {
//
//        @Autowired
//        private UserService userService;
//
//        @Bean
//        public UserDetailsService userDetailsService() {
//            UserDetails admin = User
//                    .withUsername("admin")
//                    .password(passwordEncoder().encode("admin"))
//                    .roles("USER")
//                    .build();
//
//            return new InMemoryUserDetailsManager(admin);
//        }
//
////        @Bean
////        public UserDetailsService userDetailsService() {
////            List<Users> userList = userService.userRepository.findAll();
////            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////            PasswordEncoder encoder = passwordEncoder();
////
////            for (Users user : userList) {
////                String role = "USER"; // mặc định là USER
////                if (user.getRole().getCanCreate() || user.getRole().getCanUpdate() || user.getRole().getCanDelete()) {
////                    role = "ADMIN";
////                }
////
////                UserDetails userDetails = User
////                        .withUsername(user.getUsername())
////                        .password(encoder.encode(user.getPassword()))
////                        .roles(role) // Chỉ truyền "ADMIN" hoặc "USER" mà không có "ROLE_"
////                        .build();
////
////                manager.createUser(userDetails);
////            }
////
////            return manager;
////        }
//
//
//
//    //    @Bean
//    //    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    //        http
//    //                .csrf(csrf -> csrf.disable())
//    //                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//    //                .authorizeHttpRequests(auth -> auth
//    //                        // Cho phép truy cập các API mà không cần đăng nhập
//    //                        .requestMatchers("/shop/all**", "/shop/result/**").permitAll()
//    //                        .anyRequest().authenticated()
//    //                )
//    //                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//    //                .httpBasic(withDefaults());
//    //
//    //        return http.build();
//    //    }
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .csrf(csrf -> csrf.disable())
//                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                    .authorizeHttpRequests(auth -> auth
//                            // Cho phép truy cập các API mà không cần đăng nhập
//                            .requestMatchers("/shop/all**", "/shop/result/**").permitAll()
//                            .anyRequest().authenticated()
//                    )
//                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                    .httpBasic(withDefaults());
//
//            return http.build();
//        }
//
//        @Bean
//        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//            return authenticationConfiguration.getAuthenticationManager();
//        }
//
//        @Bean
//        public CorsConfigurationSource corsConfigurationSource() {
//            CorsConfiguration configuration = new CorsConfiguration();
//            configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Cho phép tất cả các pattern của origin
//            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Cho phép tất cả các phương thức HTTP
//            configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Cho phép các header cần thiết
//            configuration.setAllowCredentials(true); // Cho phép gửi cookie hoặc thông tin xác thực
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            source.registerCorsConfiguration("/**", configuration);
//            return source;
//        }
//    }
