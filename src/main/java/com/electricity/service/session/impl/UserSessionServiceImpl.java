package com.electricity.service.session.impl;

import com.electricity.service.session.UserSession;
import com.electricity.service.session.UserSessionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.electricity.enumeration.attribute.ContextAttribute.USER_SESSION;

public class UserSessionServiceImpl implements UserSessionService {
    private Cookie logCookie;
    private UserSession userSession;
    private HttpServletRequest request;

    public UserSessionServiceImpl(HttpServletRequest request) {
        fetchCookieFromRequest(request);
        fetchUserSessionFromRequest(request);
    }

    private void fetchCookieFromRequest(HttpServletRequest request) {
        this.request = request;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loggedIn")) {
                    this.logCookie = cookie;
                }
            }
        }
    }

    private void fetchUserSessionFromRequest(HttpServletRequest request) {
        if (logCookie != null) {
            @SuppressWarnings("unchecked") final Map<String, UserSession> usersSessions = (Map<String, UserSession>)
                    request.getServletContext().getAttribute(USER_SESSION.getAttribute());

            String cookieKey = logCookie.getValue();

            userSession = usersSessions.get(cookieKey);
        }
    }

    @Override
    public Cookie getCookie() {
        return logCookie;
    }

    @Override
    public UserSession getSession() {
        return userSession;
    }

    @Override
    public void removeSession() {
        @SuppressWarnings("unchecked") final Map<String, UserSession> usersSessions = (Map<String, UserSession>)
                request.getServletContext().getAttribute(USER_SESSION.getAttribute());

        if (logCookie != null) {
            usersSessions.remove(logCookie.getValue());
        }
    }
}