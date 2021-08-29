package com.sepideh.authentication.service.user;

import com.sepideh.authentication.exception.user.UsernameOrPasswordNotCorrectException;
import com.sepideh.authentication.sercurity.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {

  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;

  public AuthenticateService(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
  }

  public String getToken(String username) {
    return jwtUtil.getToken(username);
  }

  public String getRefreshToken(String username) {
    return jwtUtil.getRefreshToken(username);
  }

  public String getUsername(String token) {
    return jwtUtil.getUsernameFromToken(token);
  }

  public boolean isTokenExpired(String token) {
    return jwtUtil.isTokenExpired(token);
  }

  public void authenticateCheck(String username, String password) {
    try {
      authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (BadCredentialsException | InternalAuthenticationServiceException | UsernameNotFoundException e) {
      throw new UsernameOrPasswordNotCorrectException();
    }
  }

}
