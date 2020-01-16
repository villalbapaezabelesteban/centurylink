package com.ar.centurylink.exception;

import com.ar.centurylink.exception.api.ApiError;
import com.ar.centurylink.exception.entity.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ApiError apiError = new ApiError()
        .setStatus(HttpStatus.BAD_REQUEST)
        .setTimestamp(LocalDateTime.now())
        .setMessage(error)
        .setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError()
        .setStatus(HttpStatus.BAD_REQUEST)
        .setTimestamp(LocalDateTime.now())
        .setMessage("Existe uno o más errores en los campos del request body")
        .setDebugMessage(ex.getLocalizedMessage())
        .addValidationErrors(ex.getBindingResult().getFieldErrors())
        .addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = String.format("No existe la URL '%s' para el método '%s'", ex.getRequestURL(), ex.getHttpMethod());
        ApiError apiError = new ApiError()
        .setStatus(HttpStatus.BAD_REQUEST)
        .setTimestamp(LocalDateTime.now())
        .setMessage(message)
        .setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("El método '");
        builder.append(ex.getMethod());
        builder.append("' no es compatible con esta solicitud. Los métodos admitidos son ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append("'" + t + "' "));

       ApiError apiError = new ApiError()
        .setStatus(HttpStatus.METHOD_NOT_ALLOWED)
        .setTimestamp(LocalDateTime.now())
        .setMessage(builder.toString())
        .setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError()
        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        .setTimestamp(LocalDateTime.now())
        .setMessage(ex.getMessage())
        .setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError()
        .setStatus(HttpStatus.NOT_FOUND)
        .setTimestamp(LocalDateTime.now())
        .setMessage(ex.getMessage())
        .setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) {
        ApiError apiError = new ApiError()
        .setStatus(HttpStatus.BAD_REQUEST)
        .setTimestamp(LocalDateTime.now())
        .setMessage("Validation Failed")
        .setDebugMessage(ex.getLocalizedMessage())
        .addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError()
        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        .setTimestamp(LocalDateTime.now())
        .setMessage(ex.getRootCause().getMessage())
        .setDebugMessage(ex.getRootCause().getLocalizedMessage());

        if (ex.getCause() instanceof ConstraintViolationException) {
            apiError.setStatus(HttpStatus.CONFLICT)
            .setMessage("Database error");
        }
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}