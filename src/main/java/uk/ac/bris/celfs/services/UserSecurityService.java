package uk.ac.bris.celfs.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import uk.ac.bris.celfs.database.User;
import java.util.Collection;
import java.util.ArrayList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

  @Autowired
  UserService userService;

/*
Based on
https://stackoverflow.com/questions/36589933/using-custom-class-instead-of-user-class-in-spring-security-for-encoding-and-pas
*/

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      System.out.println("Request for user " + username);
      User user = userService.getUserFromUsername(username);
      if (user==null) {
        throw new UsernameNotFoundException("No such user");
      }

      Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority(user.getUserType().toString()));

      org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getEncryptedPassword(), authorities);
      return springUser;
  }

}
