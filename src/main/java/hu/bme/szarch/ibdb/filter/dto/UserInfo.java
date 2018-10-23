package hu.bme.szarch.ibdb.filter.dto;

import lombok.Getter;

@Getter
public class UserInfo {

    public UserInfo(String userId) {
        this.userId = userId;
    }

    private String userId;

}
