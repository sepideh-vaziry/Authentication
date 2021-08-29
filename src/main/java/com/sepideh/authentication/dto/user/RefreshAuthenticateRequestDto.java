package com.sepideh.authentication.dto.user;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshAuthenticateRequestDto implements Serializable {

  @NotBlank
  private String refresh;

}
