package com.sepideh.authentication.controller.user;

import com.sepideh.authentication.dto.base.GenericRestResponse;
import com.sepideh.authentication.dto.user.AuthenticateRequestDto;
import com.sepideh.authentication.dto.user.AuthenticateResponseDto;
import com.sepideh.authentication.dto.user.RefreshAuthenticateRequestDto;
import com.sepideh.authentication.exception.user.TokenInvalidException;
import com.sepideh.authentication.service.user.AuthenticateService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/auth")
public class AuthenticateController {

  private final AuthenticateService authenticateService;

  public AuthenticateController(AuthenticateService authenticateService) {
    this.authenticateService = authenticateService;
  }

  @PostMapping()
  public GenericRestResponse<AuthenticateResponseDto> login(
      @RequestBody @Valid AuthenticateRequestDto authenticateRequestDto
  ) {
    authenticateService.authenticateCheck(
        authenticateRequestDto.getUsername(),
        authenticateRequestDto.getPassword()
    );

    //Generate token and refresh token
    AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto();
    authenticateResponseDto.setAccess(
        authenticateService.getToken(authenticateRequestDto.getUsername())
    );
    authenticateResponseDto.setRefresh(
        authenticateService.getRefreshToken(authenticateRequestDto.getUsername())
    );

    return new GenericRestResponse<>(authenticateResponseDto, "Success", HttpStatus.OK);
  }

  @PostMapping("/refresh")
  public GenericRestResponse<AuthenticateResponseDto> refresh(
      @RequestBody @Valid RefreshAuthenticateRequestDto refreshAuthenticateRequestDto
  ) {
    //Get username from refresh token
    String username = null;
    String refreshToken = refreshAuthenticateRequestDto.getRefresh();

    try {
      username = authenticateService.getUsername(refreshToken);
    } catch (IllegalArgumentException e) {
      System.out.println("Unable to get JWT Token");
    } catch (ExpiredJwtException e) {
      System.out.println("JWT Token has expired");
    } catch (SignatureException e) {
      System.out.println("JWT signature does not match locally computed signature");
    }

    //Check token is valid or not
    if (username == null || authenticateService.isTokenExpired(refreshToken)) {
      throw new TokenInvalidException();
    }

    //Generate token and refresh token
    AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto();
    authenticateResponseDto.setAccess(authenticateService.getToken(username));
    authenticateResponseDto.setRefresh(authenticateService.getRefreshToken(username));

    return new GenericRestResponse<>(authenticateResponseDto, "Success", HttpStatus.OK);
  }

}
