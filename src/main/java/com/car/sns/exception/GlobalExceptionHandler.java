package com.car.sns.exception;

import com.car.sns.exception.model.ErrorResult;
import com.car.sns.exception.model.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(CarHrSnsAppException e) {
        return ResponseEntity
                .status(e.getAppErrorCode().getStatus())
                .body(Result.error(e.getMessage()));
    }

    @ExceptionHandler(CarHrSnsAppException.class)
    public ResponseEntity<?> AwesomeAppExceptionHandler(CarHrSnsAppException appEx) {
        ErrorResult errorResult = new ErrorResult(appEx.getAppErrorCode(), appEx.getMessage());
        return ResponseEntity
                .status(appEx.getAppErrorCode().getStatus())
                .body(Result.error(errorResult));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatusCode status,
                                                                         final WebRequest request)
    {
        logger.info("HttpRequestMethodNotSupported : ", ex);
        ErrorResult errorResult = new ErrorResult(ex.getStatusCode(), ex.getMessage());
        return ResponseEntity
                .status(status)
                .headers(headers)
                .body(Result.error(errorResult));
    }
}
