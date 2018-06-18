package myapp.servlets;

public class MissingParameterException extends Exception {

    public MissingParameterException(String paramName) {
        super("request body does not have param with name : " + paramName);
    }
}
