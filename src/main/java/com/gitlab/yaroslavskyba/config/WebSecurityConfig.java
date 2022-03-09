package com.gitlab.yaroslavskyba.config;

import com.gitlab.yaroslavskyba.security.JwtTokenFilter;
import com.gitlab.yaroslavskyba.util.ControllerPath;
import com.gitlab.yaroslavskyba.util.RoleName;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenFilter jwtTokenFilter;
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(JwtTokenFilter jwtTokenFilter, UserDetailsService userDetailsService) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests()
            .antMatchers(HttpMethod.POST, ControllerPath.PRODUCTS).hasAuthority(RoleName.ADMIN)
            .antMatchers(HttpMethod.PUT, ControllerPath.PRODUCT).hasAuthority(RoleName.ADMIN)
            .antMatchers(HttpMethod.DELETE, ControllerPath.PRODUCT, ControllerPath.USER).hasAuthority(RoleName.ADMIN)
            .antMatchers(ControllerPath.ROLES, ControllerPath.ROLE, ControllerPath.USERS).hasAuthority(RoleName.ADMIN)
            .antMatchers(HttpMethod.POST, ControllerPath.REVIEWS).hasAnyAuthority(RoleName.ADMIN, RoleName.USER)
            .antMatchers(HttpMethod.PUT, ControllerPath.REVIEW, ControllerPath.USER).hasAnyAuthority(RoleName.ADMIN, RoleName.USER)
            .antMatchers(HttpMethod.DELETE, ControllerPath.REVIEW).hasAnyAuthority(RoleName.ADMIN, RoleName.USER)
            .antMatchers(ControllerPath.ORDERS, ControllerPath.ORDER).hasAnyAuthority(RoleName.ADMIN, RoleName.USER)
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and().exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/"))
            .and().csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
