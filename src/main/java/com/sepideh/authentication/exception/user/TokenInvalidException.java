package com.sepideh.authentication.exception.user;

import com.sepideh.authentication.exception.CustomException;

public class TokenInvalidException extends CustomException {

  public TokenInvalidException() {
    super("Token is invalid");
  }
}
