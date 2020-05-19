package com.electricity.enumeration;

public enum AppViewPath {
    REGISTER("/view/jsp/register.jsp"),
    LOGIN("/view/jsp/login.jsp"),
    HOME("/view/jsp/home.jsp"),
    OFFICE("/view/jsp/office.jsp"),
    PLANTS("/view/jsp/powerPlants.jsp"),
    PLANTS_UPDATE("/view/jsp/powerPlantUpdate.jsp"),
    SHOP_RESOURCE("/view/jsp/shopResource.jsp"),
    SHOP_PLANTS("/view/jsp/shopPlants.jsp"),
    LOGOUT("/view/jsp/logout.jsp");

    private final String path;

    AppViewPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}