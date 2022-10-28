package com.zsy.producer.config.security;

import com.zsy.producer.config.security.filter.JwtRequestFilter;
import com.zsy.producer.config.security.filter.LoginFilter;
import com.zsy.producer.service.redis.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author 郑书宇
 * @create 2022/10/26 20:40
 * @desc
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource(name="myUserDetailsService")
    private UserDetailsService userDetailsService;

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/user/login","/user/registered").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(loginFilter());
        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().disable();
        http.csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public TokenService tokenService(){
        return new TokenService();
    }

    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter=new LoginFilter(super.authenticationManager(),tokenService);
        loginFilter.setFilterProcessesUrl("/user/login");
        return loginFilter;
    }

    public JwtRequestFilter jwtRequestFilter() throws Exception {
        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(redisTemplate, authenticationManagerBean(), userDetailsService);
        jwtRequestFilter.setNoLoginRequiredUri(noLoginRequiredUris());
        return jwtRequestFilter;
    }

    public List<String> noLoginRequiredUris(){
        return Arrays.asList(
                "/user/login",
                "/user/registered"
        );
    }

}
