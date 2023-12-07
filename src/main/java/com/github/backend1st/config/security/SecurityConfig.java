package com.github.backend1st.config.security;

import com.github.backend1st.web.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
      http.headers().frameOptions().sameOrigin()
              .and()
              .formLogin().disable()
              .csrf().disable()//실습위해 비활성화
              .cors().configurationSource(corsConfigurationSource())
              .and()
              .httpBasic().disable()
              .rememberMe().disable()//패스워드기억
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .authorizeRequests()
              .antMatchers("/api/all").hasRole("ADMIN")
              .antMatchers("/api/logout-success").hasAnyRole("USER","ADMIN")//permitAll 이전에 위치해야 적용됨
              .antMatchers("/resources/static/**","/api/*").permitAll()//로그인안한경우, 어드민, 유저 총 3가지경우
              .and()
              .exceptionHandling()
              .authenticationEntryPoint(new CustomAuthenticationEntryPoint())//인증실패
              .accessDeniedHandler(new CustomerAccessDeniedHandler())
              .and()
              .logout()
              .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
              .logoutSuccessUrl("/api/logout-success")//로그아웃
              .invalidateHttpSession(true)//쿠키 제거
              .and()
              .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        logger.debug("Security configuration applied.");
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedOrigins(List.of("http://localhost:63342"));
        corsConfiguration.setAllowCredentials(true);//token 주고받을때필요함
        corsConfiguration.addExposedHeader("TOKEN");// token
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","PUT","POST","PATCH","DELETE","OPTIONS"));
        corsConfiguration.setMaxAge(3600L);//허용시간 1시간

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration); //모든 api에 적용
        return source;
    }
}