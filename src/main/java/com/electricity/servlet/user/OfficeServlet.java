package com.electricity.servlet.user;

import com.electricity.model.user.User;
import com.electricity.service.session.UserSession;
import com.electricity.service.session.UserSessionService;
import com.electricity.service.session.impl.UserSessionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.electricity.enumeration.AppViewPath.OFFICE;
import static com.electricity.enumeration.ContextAttribute.*;

@WebServlet(name = "OfficeServlet", urlPatterns = "/in/office")
public class OfficeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();
        if (session != null) {
            User user = session.getUser();
            request.setAttribute(USER.getAttribute(), user);
        }

        request.getRequestDispatcher(OFFICE.getPath()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();

        if (session != null) {
            User user = session.getUser();
            boolean isPasswordCorrect = user.getPassword().equals(request.getParameter(PASSWORD.getAttribute()));

            if (isPasswordCorrect) {
                user.setFirstName(request.getParameter(FIRST_NAME.getAttribute()));
                user.setLastName(request.getParameter(LAST_NAME.getAttribute()));
            }
        }

        doGet(request, response);
    }
}