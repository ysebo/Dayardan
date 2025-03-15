package kg.alatoo.midterm_project.exceptions.handler;

import kg.alatoo.midterm_project.exceptions.NotFoundException;
import kg.alatoo.midterm_project.exceptions.NotLegalArgumentException;
import kg.alatoo.midterm_project.exceptions.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse notFound(NotFoundException notFoundException) {
    return new ExceptionResponse(HttpStatus.NOT_FOUND, notFoundException.getMessage());
  }
  @ExceptionHandler(NotLegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse illegalArgument(NotLegalArgumentException illegalArgumentException) {
    return new ExceptionResponse(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage());
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
    String errorMessage = ex.getBindingResult().getFieldErrors().stream()
        .map(FieldError::getDefaultMessage)
        .findFirst()
        .orElse("Validation failed");

    return new ExceptionResponse(HttpStatus.BAD_REQUEST, errorMessage);
  }


}
