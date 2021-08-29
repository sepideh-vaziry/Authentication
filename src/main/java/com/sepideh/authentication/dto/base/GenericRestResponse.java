package com.sepideh.authentication.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericRestResponse<T> extends ResponseEntity<T> {

  private String message;
  private String errorMessage;

  private Integer totalPages;
  private Long count;

  public GenericRestResponse() {
    super(HttpStatus.OK);
  }

  public GenericRestResponse(T body, String message, HttpStatus status) {
    super(body, status);
    this.message = message;
  }

  public GenericRestResponse(HttpStatus status, String message, String errorMessage) {
    super(status);
    this.message = message;
    this.errorMessage = errorMessage;
  }

  public GenericRestResponse(T body, HttpStatus status, int totalPages, Long count) {
    super(body, status);
    this.totalPages = totalPages;
    this.count = count;
  }
}
