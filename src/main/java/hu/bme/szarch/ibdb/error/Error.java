package hu.bme.szarch.ibdb.error;

import lombok.Getter;

@Getter
public class Error {

    private String key;
    private int code;

    public Error(String key, int code) {
        this.key = key;
        this.code = code;
    }
}
