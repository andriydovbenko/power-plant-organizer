package com.electricity.service.session;

import javax.servlet.http.Cookie;

public interface UserSessionService {

    Cookie getCookie();

    UserSession getSession();

    void removeSession();
}