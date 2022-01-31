package com.bbconsult.wealthmanagment.domain.models;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportStructure {
    private ArrayList<Report> reports = new ArrayList<>();

    private HashMap<String, ArrayList<Object>> reportsStructure = new HashMap();

    public HashMap<String, ArrayList<Object>> getReportsStructure() {
        return reportsStructure;
    }

    public void setReportsStructure(HashMap<String, ArrayList<Object>> reportsStructure) {
        this.reportsStructure = reportsStructure;
    }


    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }
}
