package com.example.cinemaressys.exception;

public class MyException extends RuntimeException{
    public MyException(String message) {
        super(message);
    }
    public MyException(String message, Exception e) {
        super(message);
    }
}
