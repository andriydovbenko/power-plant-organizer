package com.electricity.servlet.user;

import com.electricity.enumeration.alert.AlertCode;
import com.electricity.model.user.User;
import com.electricity.service.session.UserSession;
import com.electricity.service.session.impl.UserSessionImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.electricity.enumeration.alert.AlertCode.INCORRECT_LOGIN;
import static com.electricity.enumeration.alert.AlertCode.INCORRECT_PASSWORD;
import static com.electricity.enumeration.path.AppViewPath.HOME;
import static com.electricity.enumeration.path.AppViewPath.LOGIN;
import static com.electricity.enumeration.attribute.ContextAttribute.LOGGED_IN;
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
            showAlert(response, request, INCORRECT_LOGIN);
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
            showAlert(response, request, INCORRECT_PASSWORD);
        }
    }

    private void showAlert(HttpServletResponse response, HttpServletRequest request, AlertCode alertCode)
            throws IOException, ServletException {
        PrintWriter pw = response.getWriter();
        pw.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
        pw.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
        pw.println("<script>");
        pw.println("$(document).ready(function(){");

        if (INCORRECT_LOGIN.equals(alertCode)) {
            pw.println("swal ( 'Login is incorrect' ,  ' ' ,  'error' );");
        } else if (INCORRECT_PASSWORD.equals(alertCode)) {
            pw.println("swal ( 'Password is incorrect!' ,  ' ' ,  'error' );");
        }
        pw.println("});");
        pw.println("</script>");

        RequestDispatcher rd = request.getRequestDispatcher(LOGIN.getPath());
        rd.include(request, response);
    }
}