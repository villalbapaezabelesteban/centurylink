package com.ar.centurylink.exception.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiValidationError extends ApiSubError {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError() {
        super();
    }

    public ApiValidationError setObject(String object) {
        this.object = object;
        return this;
    }

    public ApiValidationError setField(String field) {
        this.field = field;
        return this;
    }

    public ApiValidationError setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
        return this;
    }

    public ApiValidationError setMessage(String message) {
        this.message = message;
        return this;
    }
}