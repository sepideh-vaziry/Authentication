package com.sepideh.authentication.exception;

public abstract class CustomException extends RuntimeException {
  public CustomException() {
  }

  public CustomException(String message) {
    super(message);
  }

  public static class NotImplementedException extends CustomException {
    public NotImplementedException() {
      super("Not Implemented");
    }
  }

  public static class RequiredFieldException extends CustomException {

    public RequiredFieldException(String fieldName) {
      super(String.format("Please insert %s", fieldName));
    }

  }

  public static class BadRequestException extends CustomException {

    public BadRequestException(String message) {
      super(message);
    }

  }

  public static class AccessDeniedException extends CustomException {

    public AccessDeniedException(String message) {
      super(message);
    }

  }

}
