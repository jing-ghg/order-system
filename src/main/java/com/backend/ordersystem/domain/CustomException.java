package com.backend.ordersystem.domain;

public class CustomException {

    private String error;


    public CustomException(String error) {
        this.error = error;
    }


    public void setError(String error){

        this.error = error;
    }
    public String getError(){
        return error;

    }
}
