package hu.bme.szarch.ibdb.service;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public abstract class TokenGenerator {

    protected String generateRandomToken(int length) {
        return RandomStringUtils.random(length, 0, 0,
                true, true, null, new SecureRandom());
    }

}
