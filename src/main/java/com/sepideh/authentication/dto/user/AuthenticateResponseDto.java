package com.sepideh.authentication.dto.user;

import java.io.Serializable;
import lombok.Data;

@Data
public class AuthenticateResponseDto implements Serializable {

  private String access;
  private String refresh;

}
