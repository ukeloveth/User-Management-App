package com.assessment.user.management.app.security.configuration;

import java.util.Optional;
import java.util.stream.Stream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class CookieUtils {

    public static Optional<Cookie> getCookie(final HttpServletRequest request, final String name) {

        return Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Stream.of(cookies)
                        .filter(cookie -> cookie.getName().equals(name))
                        .findAny()
                );
    }
}




//import com.assessment.user.management.app.OAuthConfig.CustomOAuth2User;
//import com.assessment.user.management.app.OAuthConfig.CustomOAuth2UserService;
//import com.assessment.user.management.app.configuration.PasswordConfig;
//import com.assessment.user.management.app.security.jwt.JWTAuthenticationEntryPoint;
//import com.assessment.user.management.app.security.services.CustomUserDetailsService;
//import com.assessment.user.management.app.service.impl.UserServiceImpl;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import java.io.IOException;
//
//@Configuration
//@EnableWebSecurity
//
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final JWTAuthenticationEntryPoint authenticationEntryPoint;
//    private final CustomUserDetailsService customUserDetailsService;
//    private final CustomOAuth2UserService oAuth2UserService;
//
//    private final UserServiceImpl userService;
//
//
//    public SecurityConfig(JWTAuthenticationEntryPoint authenticationEntryPoint, CustomUserDetailsService customUserDetailsService,
//                          CustomOAuth2UserService oAuth2UserService, UserServiceImpl userService) {
//        this.authenticationEntryPoint = authenticationEntryPoint;
//        this.customUserDetailsService = customUserDetailsService;
//        this.oAuth2UserService = oAuth2UserService;
//        this.userService = userService;
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/", "/login", "/oauth/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .oauth2Login()
//                .loginPage("/login")
//                .userInfoEndpoint()
//                .userService(oAuth2UserService);
//        http.oauth2Login()
//                .loginPage("/login")
//                .userInfoEndpoint()
//                .userService(oAuth2UserService)
//                .and()
//                .successHandler(new AuthenticationSuccessHandler() {
//
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                                        Authentication authentication) throws IOException, ServletException {
//
//                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
//
//                        userService.processOAuthPostLogin(oauthUser.getEmail());
//
//                        response.sendRedirect("/list");
//                    }
//                });
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public AuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider =
//                new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(new PasswordConfig().passwordEncoder());
//        provider.setUserDetailsService(customUserDetailsService);
//        return provider;
//    }
//
//
//}
