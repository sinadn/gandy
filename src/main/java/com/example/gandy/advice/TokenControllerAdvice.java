package com.example.gandy.advice;


import com.example.gandy.entity.ErrorMessage;
import com.example.gandy.exception.DeleteImageException;
import com.example.gandy.exception.TokenRefreshException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.SignatureException;


@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new ErrorMessage(
                20,
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = Exception.class)
    public ProblemDetail handleSecurityException(Exception ex) {
        ProblemDetail problemDetail= null;

        if (ex instanceof BadCredentialsException){
             problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getMessage());
            problemDetail.setProperty("access_denied_reason","Authentication failure!");
        }

        if (ex instanceof AccessDeniedException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(402),ex.getMessage());
            problemDetail.setProperty("access_denied_reason","not_authorized!");
        }
        if (ex instanceof SignatureException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401),ex.getMessage());
            problemDetail.setProperty("access_denied_reason","JWT signature not valid !");
        }
        if (ex instanceof ExpiredJwtException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),ex.getMessage());
            problemDetail.setProperty("access_denied_reason","JWT token already expired !");
        }
        if (ex instanceof DeleteImageException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(405),ex.getMessage());
            problemDetail.setProperty("access_denied_reason","The desired item was not deleted!");
        }
        if (ex instanceof ServletException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(406),ex.getMessage());
            problemDetail.setProperty("access_denied_reason","ServletException!");
        }
        if (ex instanceof IOException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(407),ex.getMessage());
            problemDetail.setProperty("access_denied_reason","IOException!");
        }
        if (ex instanceof NullPointerException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(409),ex.getMessage());
            problemDetail.setProperty("access_denied_reason","NullPointerException!");
        }
        if (ex instanceof org.springframework.security.access.AccessDeniedException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(408),ex.getMessage());
            problemDetail.setProperty("access_denied_reason","AccessDeniedException!");
        }
        else {
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),ex.getMessage());
            problemDetail.setProperty("access_denied_reason",ex.getClass());
        }
        return problemDetail;
    }


}