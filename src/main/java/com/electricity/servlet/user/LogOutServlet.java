package com.electricity.servlet.user;

import com.electricity.service.session.UserSession;
import com.electricity.service.session.UserSessionService;
import com.electricity.service.session.impl.UserSessionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.electricity.enumeration.path.AppViewPath.LOGIN;
import static com.electricity.enumeration.path.AppViewPath.LOGOUT;

@WebServlet(name = "LogOutServlet", urlPatterns = "/in/home/logout")
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(LOGOUT.getPath()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);

        UserSession userSession = sessionService.getSession();
        Cookie logCookie = sessionService.getCookie();
        if (logCookie != null) {
            userSession.stopAndSave();

            sessionService.removeSession();

            logCookie.setValue("");
            logCookie.setPath("/");
            logCookie.setMaxAge(0);
            response.addCookie(logCookie);
        }

        request.getRequestDispatcher(LOGIN.getPath()).forward(request, response);
    }
}