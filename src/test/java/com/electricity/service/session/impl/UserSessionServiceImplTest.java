package com.electricity.service.session.impl;

import com.electricity.service.session.UserSession;
import com.electricity.service.session.UserSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.electricity.enumeration.ContextAttribute.USER_SESSION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class UserSessionServiceImplTest {
    private static final String EXPECTED_COOKIE_NAME = "loggedIn";
    private static final String EXPECTED_COOKIE_VALUE = "testValue";

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final UserSession userSession = mock(UserSession.class);
    private final ServletContext servletContext = mock(ServletContext.class);

    private final Map<String, UserSession> usersSessions = spy(new HashMap<>());

    private UserSessionService service;

    @BeforeEach
    public void beforeEach() {
        Cookie cookie = new Cookie(EXPECTED_COOKIE_NAME, EXPECTED_COOKIE_VALUE);
        Cookie[] cookies = new Cookie[]{cookie};

        usersSessions.put(EXPECTED_COOKIE_VALUE, userSession);

        when(request.getCookies()).thenReturn(cookies);
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute(USER_SESSION.getAttribute())).thenReturn(usersSessions);
        when(usersSessions.get(EXPECTED_COOKIE_VALUE)).thenReturn(userSession);

        service = new UserSessionServiceImpl(request);
    }

    @Test
    void should_verify_initialization_of_user_service() {
        verify(request, times(1)).getCookies();
        verify(request, times(1)).getServletContext();
        verify(servletContext, only()).getAttribute(USER_SESSION.getAttribute());
        verify(usersSessions, times(1)).get(EXPECTED_COOKIE_VALUE);
    }

    @Test
    void should_get_cookie() {
        Cookie cookie = service.getCookie();

        assertEquals(EXPECTED_COOKIE_NAME, cookie.getName());
        assertEquals(EXPECTED_COOKIE_VALUE, cookie.getValue());
    }

    @Test
    void should_get_session() {
        UserSession session = service.getSession();

        assertSame(userSession, session);
    }

    @Test
    void should_remove_session() {
        when(usersSessions.remove(EXPECTED_COOKIE_VALUE)).thenReturn(userSession);

        service.removeSession();

        verify(usersSessions, times(1)).remove(EXPECTED_COOKIE_VALUE);
    }
}