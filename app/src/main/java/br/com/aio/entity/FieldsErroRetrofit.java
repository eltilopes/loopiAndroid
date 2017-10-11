package br.com.aio.entity;

import java.util.List;

/**
 * Created by bezerra.junior on 17/08/2015.
 */
public class FieldsErroRetrofit {

    private List<FieldError> errors;

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }
}
