package com.bbconsult.wealthmanagment.domain.models;

public class DashboardItem {
    private String reportUrl = "";
    private String firstComponent = "";
    private String date = "";

    private String img = "";

    public String getFirstComponent() {
        return firstComponent;
    }

    public void setFirstComponent(String firstComponent) {
        this.firstComponent = firstComponent;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
