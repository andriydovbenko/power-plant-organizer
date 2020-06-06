package com.electricity.service.login;

import com.electricity.enumeration.attribute.ContextAttribute;
import com.electricity.model.user.User;
import com.electricity.repository.UserRepository;
import com.electricity.service.password.encryption.PasswordCryptographer;
import com.electricity.service.session.UserSession;
import com.electricity.service.session.UserSessionService;
import com.electricity.service.session.impl.UserSessionServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.electricity.enumeration.attribute.ContextAttribute.*;

public class LoggingService {

    private LoggingService() {
    }

    public static boolean isUserLoggedIn(HttpServletRequest request) {
        UserSessionService sessionService = new UserSessionServiceImpl(request);

        return sessionService.getCookie() != null;
    }

    public static boolean isUserWithLoginRegistered(HttpServletRequest request) {

        return !getAtomicUserRepository(request)
                .get()
                .isLoginFree(request.getParameter(ContextAttribute.LOGIN.getAttribute()));
    }

    public static boolean isPasswordCorrect(HttpServletRequest request) {
        final User user = getUser(request);
        boolean isPasswordCorrect;

        if (user == null) {
            isPasswordCorrect = false;
        } else {
            String decryptedPassword = PasswordCryptographer.decrypt(user.getPassword());
            assert decryptedPassword != null;
            isPasswordCorrect = decryptedPassword.equals(request.getParameter(PASSWORD.getAttribute()));
        }

        return isPasswordCorrect;
    }

    public static AtomicReference<UserRepository> getAtomicUserRepository(HttpServletRequest request) {
        @SuppressWarnings("unchecked") final AtomicReference<UserRepository> userRepoAtomicRef =
                (AtomicReference<UserRepository>) request.getServletContext()
                        .getAttribute(USER_REPOSITORY.getAttribute());

        return userRepoAtomicRef;
    }

    public static User getUser(HttpServletRequest request) {

        return getAtomicUserRepository(request)
                .get()
                .getUserByLogin(request.getParameter(ContextAttribute.LOGIN.getAttribute()));
    }

    public static Map<String, UserSession> getUsersSession(HttpServletRequest request) {
        @SuppressWarnings("unchecked") final Map<String, UserSession> usersSessions = (Map<String, UserSession>)
                request.getServletContext().getAttribute(USER_SESSION.getAttribute());

        return usersSessions;
    }

    public static boolean whetherTheSessionWasNotCreated(HttpServletRequest request) {
        User user = getUser(request);

        return getUsersSession(request).get(user.getId()) == null;
    }
}