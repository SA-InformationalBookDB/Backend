package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.filter.AuthenticationFilter;
import hu.bme.szarch.ibdb.filter.dto.UserInfo;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class WebBase {

    @Autowired
    private ObjectFactory<HttpServletRequest> httpRequestFactory;

    protected UserInfo getUserInfo() {

        UserInfo userInfo = (UserInfo) httpRequestFactory.getObject().getAttribute(AuthenticationFilter.userInfoAttribute);

        if(userInfo == null) {
            throw new ServerException(Errors.INVALID_SESSION);
        }

        return userInfo;
    }

}
