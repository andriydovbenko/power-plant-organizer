package com.electricity.servlet;

import com.electricity.repository.UserRepository;
import com.electricity.service.registration.RegistrationService;
import com.electricity.service.registration.impl.RegistrationServiceImpl;
import com.electricity.service.session.UserSession;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static com.electricity.enumeration.attribute.ContextAttribute.*;

@WebListener()
public class ContextListener implements ServletContextListener {
    private AtomicReference<UserRepository> userRepository;
    private AtomicReference<RegistrationService> registrationService;
    private Map<String, UserSession> userSession;

    public ContextListener() {
        // default initialization
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.userRepository = new AtomicReference<>(new UserRepository());
        this.registrationService = new AtomicReference<>(new RegistrationServiceImpl(userRepository));
        this.userSession = new ConcurrentHashMap<>();

        ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute(USER_REPOSITORY.getAttribute(), userRepository);
        servletContext.setAttribute(REGISTRATION_SERVICE.getAttribute(), registrationService);
        servletContext.setAttribute(USER_SESSION.getAttribute(), userSession);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        userRepository = null;
        registrationService = null;
        userSession =null;
    }
}