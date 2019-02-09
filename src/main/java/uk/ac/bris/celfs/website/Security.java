package uk.ac.bris.celfs.website;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
  @Override protected void configure(HttpSecurity security) throws Exception {
    security
      .authorizeRequests()
      .anyRequest().permitAll()
      /*
      .antMatchers("/").permitAll()
      .antMatchers("/login").permitAll()
      .antMatchers("/resources/**").permitAll()
      .anyRequest().authenticated()
      */
      ;
  }

}
