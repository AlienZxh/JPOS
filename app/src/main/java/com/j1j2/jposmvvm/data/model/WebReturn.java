package com.j1j2.jposmvvm.data.model;

import com.google.gson.annotations.Expose;

/**
 * Created by 兴昊 on 2015/8/24.
 */
public class WebReturn<T> {

    private boolean Value;
    private T Detail;
    private String ErrorMessage;
    @Expose
    private String AlertMessage;

    public boolean isValue() {
        return Value;
    }

    public void setValue(boolean value) {
        Value = value;
    }

    public T getDetail() {
        return Detail;
    }

    public void setDetail(T detail) {
        Detail = detail;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getAlertMessage() {
        return AlertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        AlertMessage = alertMessage;
    }

    @Override
    public String toString() {
        return "WebReturn{" +
                "Value=" + Value +
                ", Detail=" + Detail +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                ", AlertMessage='" + AlertMessage + '\'' +
                '}';
    }
}
