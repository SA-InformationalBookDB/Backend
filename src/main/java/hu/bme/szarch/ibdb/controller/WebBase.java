package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.filter.AuthenticationFilter;
import hu.bme.szarch.ibdb.filter.dto.UserInfo;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class WebBase {

    @Autowired
    private ObjectFactory<HttpSession> httpSessionFactory;

    protected UserInfo getUserInfo() {
        UserInfo userInfo = (UserInfo)httpSessionFactory.getObject().getAttribute(AuthenticationFilter.userInfoAttribute);

        if(userInfo == null) {
            throw new ServerException(Errors.INVALID_SESSION);
        }

        return userInfo;
    }

}
