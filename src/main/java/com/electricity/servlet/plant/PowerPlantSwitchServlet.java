package com.electricity.servlet.plant;

import com.electricity.model.plant.PowerPlant;
import com.electricity.service.session.UserSession;
import com.electricity.service.session.UserSessionService;
import com.electricity.service.session.impl.UserSessionServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.electricity.enumeration.attribute.ContextAttribute.PLANT_ID;

@WebServlet(name = "PowerPlantSwitchServlet", urlPatterns = "/in/plant/switch")
public class PowerPlantSwitchServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();

        if (session != null) {
            PowerPlant powerPlant = session.getPowerPlantById(request.getParameter(PLANT_ID.getAttribute()));
            boolean currentState = powerPlant.isWorking();
            powerPlant.setWorking(!currentState);
        }

        response.sendRedirect("/in/plants");
    }
}