package com.sepideh.authentication.dto.content;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostSimpleDto implements Serializable {

  private Long id;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  @NotBlank
  private String title;
  private String description;

}
