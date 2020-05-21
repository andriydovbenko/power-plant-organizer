package com.electricity.servlet.user;

import com.electricity.model.user.User;
import com.electricity.service.session.UserSession;
import com.electricity.service.session.impl.UserSessionImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.electricity.enumeration.AppViewPath.*;
import static com.electricity.enumeration.ContextAttribute.LOGGED_IN;
import static com.electricity.service.login.LoggingService.*;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(LOGIN.getPath()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (isUserLoggedIn(request)) {
            request.getRequestDispatcher(HOME.getPath()).forward(request, response);
        }

        if (isUserWithLoginRegistered(request)) {
            checkUserAndCreateSession(request, response);
        } else {
            request.getRequestDispatcher(REGISTER.getPath()).forward(request, response);
        }
    }

    private void checkUserAndCreateSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final User user = getUser(request);

        if (isPasswordCorrect(request)) {
            final Cookie logCookie = new Cookie(LOGGED_IN.getAttribute(), user.getId());

            if (whetherTheSessionWasNotCreated(request)) {
                UserSession session = new UserSessionImpl(getAtomicUserRepository(request).get(), user);
                getUsersSession(request).put(user.getId(), session);
                session.start();
            }

            response.addCookie(logCookie);
            request.getRequestDispatcher(HOME.getPath()).forward(request, response);
        } else {
            request.getRequestDispatcher(LOGIN.getPath()).forward(request, response);
        }
    }
}