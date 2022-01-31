package com.bbconsult.wealthmanagment.domain.models;

import java.util.ArrayList;

public class LoadXMLEvent {
    private final ArrayList<Report> reports;

    public LoadXMLEvent(ArrayList<Report> reports) {
        this.reports = reports;

    }

    public ArrayList<Report> getAllReports() {
        return reports;
    }
}
