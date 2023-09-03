package com.example.demoroles.config;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
//@EnableWebSecurity
@EnableMethodSecurity //(prePostEnabled = true)
public class WebSecurityConfig {

    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

//  private static final String[] AUTH_WHITE_LIST = {
//            "/**"
//  };

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    //@Qualifier("unauthorizedEntryPoint")
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    //@Qualifier("accessDeniedHandler")
    private CustomAccessDeniedHandler accessDeniedHandler;

//    Se reemplaza en el atributo con el mismo nombre con @Autowired
//    @Bean
//    public CustomAccessDeniedHandler accessDeniedHandler(){
//        return new CustomAccessDeniedHandler();
//    }


//    @Autowired  //@Override (if class "extends GlobalAuthenticationConfigurerAdapter")
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
    @Bean
    public BCryptPasswordEncoder encoder(){ return new BCryptPasswordEncoder(); }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(encoder());
//
//        return authProvider;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() { return new JwtAuthenticationFilter(); }
    @Bean
    //Order1
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(unauthorizedEntryPoint)
                                 .accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/users/authenticate", "/users/register", "/error").permitAll()
                                //.requestMatchers(HttpMethod.POST,"/api/auth/signin/**").permitAll()
                                //.requestMatchers(AUTH_WHITE_LIST).permitAll()
                                .requestMatchers(antMatcher(h2ConsolePath + "/**")).permitAll()
                                //.requestMatchers("/v3/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll() // Swagger 3
                                .anyRequest().authenticated());
//              http.authenticationProvider(authenticationProvider());
//              http.logout(logout -> logout
//                                .logoutUrl("/logout")
//                                .invalidateHttpSession(true)
//                                .deleteCookies("JSESSIONID")
//                                .logoutSuccessUrl(LOGIN_URL + "?logout"));

        // This will allow frames with same origin which is much more safe
        // fix H2 database console: Refused to display ' in a frame because it set 'X-Frame-Options' to 'deny'
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)); // (Lambda) (frameOption -> frameOption.sameOrigin()));

        // Add JWT token filter
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
