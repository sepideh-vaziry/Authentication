package com.sepideh.authentication.dto.user;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto implements Serializable {

  private Long id;

  @NotBlank
  private String username;
  private Timestamp createdAt;
  private Timestamp updatedAt;

}
