package hu.bme.szarch.ibdb.error;

public class Errors {

    public static final Error INVALID_ACCESS_TOKEN = new Error("INVALID_ACCESS_TOKEN", 101);
    public static final Error INVALID_LOGIN_EXCEPTION = new Error("INVALID_LOGIN", 102);
    public static final Error INVALID_AUTHORIZATION_CODE = new Error("INVALID_AUTHORIZATION_CODE", 103);

}
