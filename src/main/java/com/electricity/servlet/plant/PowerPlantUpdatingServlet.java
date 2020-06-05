package com.electricity.servlet.plant;

import com.electricity.model.dto.impl.PowerPlantUpdatingDto;
import com.electricity.model.plant.PowerPlant;
import com.electricity.service.session.UserSession;
import com.electricity.service.session.UserSessionService;
import com.electricity.service.session.impl.UserSessionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.electricity.enumeration.path.AppViewPath.PLANTS_UPDATE;
import static com.electricity.enumeration.attribute.ContextAttribute.*;

@WebServlet(name = "PowerPlantUpdateServlet", urlPatterns = "/in/plant/update")
public class PowerPlantUpdatingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();

        if (session != null) {
            PowerPlantUpdatingDto dto = new PowerPlantUpdatingDto();
            dto.setId(request.getParameter(PLANT_ID.getAttribute()));
            dto.setCountry(request.getParameter(COUNTRY.getAttribute()));
            dto.setNumberOfEmployees(Integer.parseInt(request.getParameter(NUMBER_OF_EMPLOYEES.getAttribute())));
            dto.setWorking(request.getParameter(CHOICE.getAttribute()).equals(START.getAttribute()));

            session.updatePowerPlant(dto);
        }

        response.sendRedirect("/in/plants");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();

        if (session != null) {
            PowerPlant powerPlant = session.getPowerPlantById(request.getParameter(PLANT_ID.getAttribute()));
            request.setAttribute(POWER_PLANT.getAttribute(), powerPlant);
        }

        request.getRequestDispatcher(PLANTS_UPDATE.getPath()).forward(request, response);
    }
}