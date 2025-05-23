package com.example.homeserver.Service.Error;

import com.example.homeserver.DTO.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestResponse<Object>> badCredentials(BadCredentialsException e) {
        RestResponse<Object> response = new RestResponse<>();
        response.setError(e.getMessage());
        response.setMessage("Something went wrong");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getBody().getDetail());

        List<String> errors = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        res.setMessage(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> exception(Exception ex){
        RestResponse<Object> restResponse = new RestResponse<>();
        restResponse.setError(ex.getMessage());
        restResponse.setMessage("Something went wrong");
        restResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);

    }
}
