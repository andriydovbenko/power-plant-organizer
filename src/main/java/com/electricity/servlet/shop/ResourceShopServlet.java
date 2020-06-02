package com.electricity.servlet.shop;

import com.electricity.enumeration.PowerPlantType;
import com.electricity.enumeration.PurchasableResourceType;
import com.electricity.model.plant.PowerPlant;
import com.electricity.model.transaction.ResourceTransaction;
import com.electricity.model.user.User;
import com.electricity.service.session.UserSession;
import com.electricity.service.session.UserSessionService;
import com.electricity.service.session.impl.UserSessionServiceImpl;
import com.electricity.service.converter.StringsConverterIntoResourceType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.electricity.enumeration.AppViewPath.SHOP_RESOURCE;
import static com.electricity.enumeration.ContextAttribute.*;

@WebServlet(name = "ResourceShopServlet", urlPatterns = "/in/home/shop/resource")
public class ResourceShopServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();

        if (session != null) {
            String id = request.getParameter(PLANT_ID.getAttribute());
            String requestAmount = request.getParameter(AMOUNT.getAttribute());
            int amount = Integer.parseInt(requestAmount);

            PurchasableResourceType type = StringsConverterIntoResourceType
                    .convert(request.getParameter(RESOURCE_TYPE.getAttribute()));

            if (type != null) {
                ResourceTransaction transaction = new ResourceTransaction(id, amount, type);
                session.buyResource(transaction);
            }
        }

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserSessionService sessionService = new UserSessionServiceImpl(request);
        UserSession session = sessionService.getSession();

        if (session != null) {
            User user = session.getUser();
            request.setAttribute(USER.getAttribute(), user);
            List<PowerPlant> plants = session.getAllPowerPlants();

            List<PowerPlant> coalFiredPlants = plants.stream()
                    .filter(powerPlant -> powerPlant.getType().equals(PowerPlantType.COAL))
                    .collect(Collectors.toList());

            List<PowerPlant> nuclearPlants = plants.stream()
                    .filter(powerPlant -> powerPlant.getType().equals(PowerPlantType.NUCLEAR))
                    .collect(Collectors.toList());

            request.setAttribute("coalFiredPlants", coalFiredPlants);
            request.setAttribute("nuclearPlants", nuclearPlants);
        }

        request.getRequestDispatcher(SHOP_RESOURCE.getPath()).forward(request, response);
    }
}