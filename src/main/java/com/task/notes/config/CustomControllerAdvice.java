package com.task.notes.config;

import com.task.notes.entity.Response;
import com.task.notes.entity.exception.EmptyInputException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<Response> handleEmptyInput(EmptyInputException emptyInputException) {

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("request",emptyInputException.toString()))
                        .message("Empty field send")
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .build()
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Response> handleEmptyInput(NoSuchElementException elementException) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("error","With that value doesn't data in application!"))
                        .message("")
                        .status(NOT_FOUND)
                        .statusCode(NOT_FOUND.value())
                        .build()
        );
    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<Object>("Please change http method type",NOT_FOUND);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Access denied!", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
