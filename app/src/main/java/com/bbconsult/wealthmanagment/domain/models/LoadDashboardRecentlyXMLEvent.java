package com.bbconsult.wealthmanagment.domain.models;

import java.util.ArrayList;

public class LoadDashboardRecentlyXMLEvent {
    private ArrayList<DashboardItem> dashboardItems;

    public LoadDashboardRecentlyXMLEvent(ArrayList<DashboardItem> dashboardItems) {
        this.dashboardItems = dashboardItems;
    }

    public ArrayList<DashboardItem> getAllDashboardItems() {
        return dashboardItems;
    }
}
