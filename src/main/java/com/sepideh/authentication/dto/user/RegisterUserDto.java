package com.sepideh.authentication.dto.user;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserDto implements Serializable {

  @NotBlank
  private String username;
  private String password;

}
