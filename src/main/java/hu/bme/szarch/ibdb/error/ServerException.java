package hu.bme.szarch.ibdb.error;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {

    private Error error;

    public ServerException(Error error) {
        this.error = error;
    }
}
