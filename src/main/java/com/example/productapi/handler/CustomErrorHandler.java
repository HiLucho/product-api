package com.example.productapi.handler;

import com.example.productapi.model.CustomError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CustomErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        Exception exception = new Exception(ex);

        return this.createResponseEntity(HttpStatus.BAD_REQUEST, exception, errors, request);
    }

    private ResponseEntity<Object> createResponseEntity(
            HttpStatus httpStatus, Exception ex, List<String> errors, WebRequest request) {
        CustomError errorResponse = CustomError.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(errors)
                .path(request.getDescription(true))
                .build();
        return handleExceptionInternal(ex, errorResponse,
                new HttpHeaders(), httpStatus, request);
    }
}
