package com.assessment.user.management.app.security.configuration;//package com.assessment.user.management.app.security.configuration;

import com.assessment.user.management.app.OAuthConfig.CustomOAuth2User;
import com.assessment.user.management.app.OAuthConfig.CustomOAuth2UserService;
import com.assessment.user.management.app.configuration.PasswordConfig;
import com.assessment.user.management.app.security.jwt.JWTAuthenticationEntryPoint;
import com.assessment.user.management.app.security.jwt.JWTAuthenticationFilter;
import com.assessment.user.management.app.security.services.CustomUserDetailsService;
import com.assessment.user.management.app.service.impl.UserServiceImpl;
import jakarta.servlet.Filter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.UriComponentsBuilder;




import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        proxyTargetClass = true
)
public class SecurityConfig {

    private final JWTAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;

    private final UserServiceImpl userService;

@Autowired
    public SecurityConfig(JWTAuthenticationEntryPoint authenticationEntryPoint, CustomUserDetailsService customUserDetailsService,
                          CustomOAuth2UserService oAuth2UserService, UserServiceImpl userService) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.customUserDetailsService = customUserDetailsService;
        this.oAuth2UserService = oAuth2UserService;
        this.userService = userService;
    }


    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(configurer -> configurer.configure(http))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers(
                                "/api/**",
                                "/api/users/**",
                                "/api/auth/**",
                                "/swagger-ui*/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/swagger-ui.html",
                                "/api-docs",
                                "/v3/api-docs/**",
                                "/webjars/**",
                                "/authenticate",
                                "/logout"
                        ).permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .authorizationEndpoint(endpointConfigurer -> endpointConfigurer
                                .baseUri("/oauth2/authorize"))
                        .userInfoEndpoint(configurer -> configurer.userService(oAuth2UserService))
                        .redirectionEndpoint(redirectConfigurer -> redirectConfigurer.baseUri("/oauth2/callback/*"))
                        .successHandler((request, response, authentication) -> {
                            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                            userService.processOAuthUser(oauthUser, authentication); // call a method in your service
                            response.sendRedirect("/api/auth/index");
                        })
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                String target = CookieUtils.getCookie(request, "redirect_uri")
                                        .map(Cookie::getValue).orElse("/");

                                target = UriComponentsBuilder.fromUriString(target)
                                        .queryParam("error", exception.getLocalizedMessage())
                                        .build().toUriString();

                                getRedirectStrategy().sendRedirect(request, response, target);
                            }
                        })
                        .loginPage("/oauth2/authorization/google"))
                .logout(configurer -> configurer
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();

    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new PasswordConfig().passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        userService.processOAuthPostLogin(oauthUser.getEmail());

        response.sendRedirect("/api/auth/");
    }
}





//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    ...
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
//                .userService(oauthUserService);
//    }
//
//    @Autowired
//    private CustomOAuth2UserService oauthUserService;
//
//
//}