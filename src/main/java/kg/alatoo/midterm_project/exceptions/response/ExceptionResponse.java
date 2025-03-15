package kg.alatoo.midterm_project.exceptions.response;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(HttpStatus status, String message) {}
