package com.electricity.servlet.user;

import com.electricity.model.user.User;
import com.electricity.service.password.encryption.PasswordCryptographer;
import com.electricity.service.session.UserSession;
import com.electricity.service.session.UserSessionService;
import com.electricity.service.session.impl.UserSessionServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.electricity.enumeration.path.AppViewPath.OFFICE;
import static com.electricity.enumeration.attribute.ContextAttribute.*;

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

        RequestDispatcher rd = request.getRequestDispatcher(OFFICE.getPath());
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();

        if (session != null) {
            User user = session.getUser();
            String decryptedPassword = PasswordCryptographer.decrypt(user.getPassword());
            assert decryptedPassword != null;
            boolean isPasswordCorrect = decryptedPassword.equals(request.getParameter(PASSWORD.getAttribute()));

            if (isPasswordCorrect) {
                user.setFirstName(request.getParameter(FIRST_NAME.getAttribute()));
                user.setLastName(request.getParameter(LAST_NAME.getAttribute()));
            } else {
                showIncorrectPasswordAlert(response);
            }
        }

        doGet(request,response);
    }

    private void showIncorrectPasswordAlert(HttpServletResponse response)
            throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
        pw.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
        pw.println("<script>");
        pw.println("$(document).ready(function(){");
        pw.println("swal ( 'Password is incorrect !' ,  ' ' ,  'error' );");
        pw.println("});");
        pw.println("</script>");
    }
}