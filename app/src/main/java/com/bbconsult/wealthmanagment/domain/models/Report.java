package com.bbconsult.wealthmanagment.domain.models;

import java.util.ArrayList;

public class Report {
    private ArrayList<ComponentProperties> components = new ArrayList<>();
    private String name = "";

    private String urlToReport = "";


    public ArrayList<ComponentProperties> getComponents() {
        return components;
    }

    public void setReportComponents(ArrayList<ComponentProperties> components) {
        this.components = components;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlToReport() {
        return urlToReport;
    }

    public void setUrlToReport(String urlToReport) {
        this.urlToReport = urlToReport;
    }
}
