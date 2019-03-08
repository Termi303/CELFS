package uk.ac.bris.celfs.website;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.bris.celfs.services.UserSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserSecurityService userService;
  @Override protected void configure(HttpSecurity security) throws Exception {
    security
      .authorizeRequests().antMatchers("/resources/**").permitAll()
      .anyRequest().authenticated().and().formLogin().permitAll()
      .and()
      .formLogin().loginPage("/login").permitAll();

  }
@Bean
public DaoAuthenticationProvider authenticationProvider() {
  DaoAuthenticationProvider authProv = new DaoAuthenticationProvider();
  authProv.setUserDetailsService(userService);
  authProv.setPasswordEncoder(encoder());
  return authProv;
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
