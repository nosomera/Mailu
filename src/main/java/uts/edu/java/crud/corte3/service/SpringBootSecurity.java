package uts.edu.java.crud.corte3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringBootSecurity  {

    private UserDetailsService userDetailService;

    @Autowired
    public void setUserDetailService(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    /*
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .requestMatchers("/administrador/**").hasRole("ADMIN")
                .requestMatchers("/productos/**").hasRole("ADMIN")
                .and()
            .formLogin()
                .loginPage("/usuario/login")
                .permitAll()
                .defaultSuccessUrl("/usuario/acceder");
    }
    */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .anyRequest().permitAll() // Permite todas las solicitudes
            )
            .formLogin()
                .loginPage("/usuario/login").permitAll(); // Permitir acceso a la página de inicio de sesión
        return http.build();
    }
}

