package com.electricity.servlet.user;

import com.electricity.enumeration.ContextAttribute;
import com.electricity.model.user.User;
import com.electricity.repository.UserRepository;
import com.electricity.service.password.encryption.PasswordCryptographer;
import com.electricity.service.password.validation.PasswordValidator;
import com.electricity.service.registration.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static com.electricity.enumeration.AppViewPath.LOGIN;
import static com.electricity.enumeration.AppViewPath.REGISTER;
import static com.electricity.enumeration.ContextAttribute.*;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/register")
public class RegistrationServlet extends HttpServlet {
    private PasswordValidator validator;

    @Override
    public void init() {
        final int minPasswordLength = 4;
        final int maxPasswordLength = 24;

        validator = PasswordValidator.buildValidator(
                false, false, false, minPasswordLength, maxPasswordLength);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTER.getPath()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirectUrl;
        String password = request.getParameter(PASSWORD.getAttribute());
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD.getAttribute());

        User user = new User(request.getParameter(ContextAttribute.LOGIN.getAttribute()), password);
        user.setFirstName(request.getParameter(FIRST_NAME.getAttribute()));
        user.setLastName(request.getParameter(LAST_NAME.getAttribute()));

        boolean isPasswordAppropriate = user.getPassword().equals(confirmPassword) && validatePassword(password);

        if (isPasswordAppropriate) {
            redirectUrl = checkLoginAndRegisterUser(user);
        } else {
            redirectUrl = REGISTER.getPath();
        }
        request.getRequestDispatcher(redirectUrl).forward(request, response);
    }

    private boolean validatePassword(String password) {
        return validator.validatePassword(password);
    }

    private String checkLoginAndRegisterUser(User user) {
        String redirectUrl;

        encryptPasswordAndSetToUser(user);

        @SuppressWarnings("unchecked") final AtomicReference<UserRepository> userRepoAtomicRef =
                (AtomicReference<UserRepository>) getServletContext().getAttribute(USER_REPOSITORY.getAttribute());

        @SuppressWarnings("unchecked") final AtomicReference<RegistrationService> registerServiceAtomicRef =
                (AtomicReference<RegistrationService>) getServletContext().getAttribute(REGISTRATION_SERVICE.getAttribute());

        if (userRepoAtomicRef.get().isLoginFree(user.getLogin())) {
            registerServiceAtomicRef.get().register(user);
            redirectUrl = LOGIN.getPath();
        } else {
            redirectUrl = REGISTER.getPath();
        }
        return redirectUrl;
    }

    private void encryptPasswordAndSetToUser(User user) {
        String encryptedPassword = PasswordCryptographer.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);
    }
}