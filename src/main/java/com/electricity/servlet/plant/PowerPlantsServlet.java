package com.electricity.servlet.plant;

import com.electricity.model.plant.PowerPlant;
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
import java.util.List;

import static com.electricity.enumeration.path.AppViewPath.PLANTS;
import static com.electricity.enumeration.attribute.ContextAttribute.*;

@WebServlet(name = "PowerPlantServlet", urlPatterns = "/in/plants")
public class PowerPlantsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);

        UserSession session = sessionService.getSession();
        if (session != null) {
            List<PowerPlant> powerPlants = session.getAllPowerPlants();
            User user = session.getUser();

            request.setAttribute(POWER_PLANTS.getAttribute() , powerPlants);
            request.setAttribute(USER.getAttribute(), user);
        }

        response.setIntHeader("Refresh", 2);
        request.getRequestDispatcher(PLANTS.getPath()).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();

        if (session != null) {
            session.removePowerPlantById(request.getParameter(PLANT_ID.getAttribute()));
        }

        response.sendRedirect("/in/plants");
    }
}