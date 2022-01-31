package com.bbconsult.wealthmanagment.domain.models;

import java.util.ArrayList;

public class LoadDashboardFavouritesXMLEvent {
    private final ArrayList<DashboardItem> dashboardItems;

    public LoadDashboardFavouritesXMLEvent(ArrayList<DashboardItem> dashboardItems) {
        this.dashboardItems = dashboardItems;
    }

    public ArrayList<DashboardItem> getAllDashboardItems() {
        return dashboardItems;
    }
}
