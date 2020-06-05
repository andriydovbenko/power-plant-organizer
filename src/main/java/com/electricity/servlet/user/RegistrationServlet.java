package com.electricity.servlet.user;

import com.electricity.enumeration.alert.AlertCode;
import com.electricity.enumeration.attribute.ContextAttribute;
import com.electricity.model.user.User;
import com.electricity.repository.UserRepository;
import com.electricity.service.password.encryption.PasswordCryptographer;
import com.electricity.service.password.validation.PasswordValidator;
import com.electricity.service.registration.RegistrationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;

import static com.electricity.enumeration.alert.AlertCode.*;
import static com.electricity.enumeration.path.AppViewPath.LOGIN;
import static com.electricity.enumeration.path.AppViewPath.REGISTER;
import static com.electricity.enumeration.attribute.ContextAttribute.*;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/register")
public class RegistrationServlet extends HttpServlet {
    private PasswordValidator validator;

    @Override
    public void init() {
        final int minPasswordLength = 8;
        final int maxPasswordLength = 24;

        validator = PasswordValidator.buildValidator(
                false, true, true, minPasswordLength, maxPasswordLength);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTER.getPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter(PASSWORD.getAttribute());
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD.getAttribute());

        User user = new User(request.getParameter(ContextAttribute.LOGIN.getAttribute()), password);
        user.setFirstName(request.getParameter(FIRST_NAME.getAttribute()));
        user.setLastName(request.getParameter(LAST_NAME.getAttribute()));

        boolean isPasswordAppropriate = user.getPassword().equals(confirmPassword) && validatePassword(password);

        if (isPasswordAppropriate) {
            checkLoginAndRegisterUser(user, response, request);
        } else {
            showAlert(response, request, INCORRECT_PASSWORD);
        }
    }

    private boolean validatePassword(String password) {
        return validator.validatePassword(password);
    }

    private void checkLoginAndRegisterUser(User user, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        encryptPasswordAndSetToUser(user);

        @SuppressWarnings("unchecked") final AtomicReference<UserRepository> userRepoAtomicRef =
                (AtomicReference<UserRepository>) getServletContext().getAttribute(USER_REPOSITORY.getAttribute());

        @SuppressWarnings("unchecked") final AtomicReference<RegistrationService> registerServiceAtomicRef =
                (AtomicReference<RegistrationService>) getServletContext().getAttribute(REGISTRATION_SERVICE.getAttribute());

        if (userRepoAtomicRef.get().isLoginFree(user.getLogin())) {
            registerServiceAtomicRef.get().register(user);
            request.getRequestDispatcher(LOGIN.getPath()).forward(request, response);
        } else {
            showAlert(response, request, OCCUPIED_LOGIN);
        }
    }

    private void encryptPasswordAndSetToUser(User user) {
        String encryptedPassword = PasswordCryptographer.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
    }

    private void showAlert(HttpServletResponse response, HttpServletRequest request, AlertCode alertCode)
            throws IOException, ServletException {
        PrintWriter pw = response.getWriter();
        pw.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
        pw.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
        pw.println("<script>");
        pw.println("$(document).ready(function(){");

        if (INCORRECT_PASSWORD.equals(alertCode)) {
            pw.println("swal ( 'Password is incorrect. Password and Confirm Password should match. Password should " +
                    "have at least: one lower- and upper-case letter, one digit and consist of 8-24 characters' ,  ' ' ,  'error' );");
        } else if (OCCUPIED_LOGIN.equals(alertCode)) {
            pw.println("swal ( 'User with such login is already registered !' ,  ' ' ,  'error' );");
        }
        pw.println("});");
        pw.println("</script>");

        RequestDispatcher rd = request.getRequestDispatcher(REGISTER.getPath());
        rd.include(request, response);
    }
}