package com.sepideh.authentication.sercurity;

import com.sepideh.authentication.model.user.User;
import com.sepideh.authentication.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  // Constructor *****************************************************************************************************
  @Autowired
  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // Override methods ************************************************************************************************
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    try {
      Optional<User> user = userRepository.findByUsername(username);

      return user.orElseThrow(
          () -> new UsernameNotFoundException("No user found with username: " + username)
      );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

}
