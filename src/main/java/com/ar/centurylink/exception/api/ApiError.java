package com.ar.centurylink.exception.api;

import com.ar.centurylink.exception.util.LowerCaseClassNameResolver;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    public ApiError() {
        super();
    }

    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(FieldError fieldError) {
        ApiValidationError apiValidationError = new ApiValidationError()
        .setObject(fieldError.getObjectName())
        .setField(fieldError.getField())
        .setRejectedValue(fieldError.getRejectedValue())
        .setMessage(fieldError.getDefaultMessage());
        this.addSubError(apiValidationError);
    }

    public ApiError addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
        return this;
    }

    private void addValidationError(ObjectError objectError) {
        ApiValidationError apiValidationError = new ApiValidationError()
        .setObject(objectError.getObjectName())
        .setMessage(objectError.getDefaultMessage());
        this.addSubError(apiValidationError);
    }

    public ApiError addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
        return this;
    }

    private void addValidationError(ConstraintViolation<?> cv) {
        ApiValidationError apiValidationError = new ApiValidationError()
        .setObject(cv.getRootBeanClass().getSimpleName())
        .setField(((PathImpl) cv.getPropertyPath()).getLeafNode().asString())
        .setRejectedValue(cv.getInvalidValue())
        .setMessage(cv.getMessage());
        this.addSubError(apiValidationError);
    }

    public ApiError addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ApiError setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ApiError setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ApiError setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiError setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
        return this;
    }
}