package com.sepideh.authentication.exception.user;

import com.sepideh.authentication.exception.CustomException;

public class UsernameOrPasswordNotCorrectException extends CustomException {

  public UsernameOrPasswordNotCorrectException() {
    super("The username or password is not correct.");
  }
}
