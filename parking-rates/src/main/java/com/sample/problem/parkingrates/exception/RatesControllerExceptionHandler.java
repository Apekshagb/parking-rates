package com.sample.problem.parkingrates.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RatesControllerExceptionHandler {

    private String errorCode;
    private String message;

    public RatesControllerExceptionHandler(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    @ControllerAdvice
    public class ApiExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(code = HttpStatus.BAD_REQUEST)
        @ResponseBody
        public RatesControllerExceptionHandler handleValidationError(MethodArgumentNotValidException ex) {
            BindingResult bindingResult = ex.getBindingResult();
            FieldError fieldError = bindingResult.getFieldError();
            String defaultMessage = fieldError.getDefaultMessage();
            return new RatesControllerExceptionHandler("VALIDATION_FAILED", defaultMessage);
        }
    }

}
