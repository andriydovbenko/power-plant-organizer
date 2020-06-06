package com.electricity.servlet.shop;

import com.electricity.enumeration.plant.PowerPlantType;
import com.electricity.model.dto.impl.PowerPlantCreatingDto;
import com.electricity.model.user.User;
import com.electricity.service.converter.StringConverterIntoPlantType;
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

import static com.electricity.enumeration.path.AppViewPath.SHOP_PLANTS;
import static com.electricity.enumeration.attribute.ContextAttribute.*;

@WebServlet(name = "PowerPlantShopServlet", urlPatterns = "/in/home/shop/plant")
public class PowerPlantShopServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();

        if (session != null) {

            PowerPlantCreatingDto dto = new PowerPlantCreatingDto();
            String requestType = request.getParameter(TYPE.getAttribute());
            String working = request.getParameter(START.getAttribute());

            dto.setWorking(working != null);
            dto.setCountry(request.getParameter(COUNTRY.getAttribute()));

            PowerPlantType type = StringConverterIntoPlantType.convert(requestType);

            int numberOfEmployees = Integer.parseInt(request.getParameter(NUMBER_OF_EMPLOYEES.getAttribute()));

            if (type != null) {
                dto.setType(type);
                dto.setNumberOfEmployees(numberOfEmployees);
                session.buyNewPowerPlant(dto);
            }

            doGet(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();

        if (session != null) {
            User user = session.getUser();
            request.setAttribute(USER.getAttribute(), user);
        }

        RequestDispatcher rd = request.getRequestDispatcher(SHOP_PLANTS.getPath());
        rd.include(request, response);
    }
}