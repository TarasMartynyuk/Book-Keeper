package myapp.servlets;

import http.server.Method;

public class WrongMethodException extends IllegalStateException {
    public WrongMethodException(String reciever,  Method actual, Method expected) {
        super(reciever + " got a request with method: " + actual +
                ". The method must be " + expected);
    }
}
