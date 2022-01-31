package com.bbconsult.wealthmanagment.domain.models;

import java.util.ArrayList;

public class ReportMenuItems {
    private ArrayList<PackageDirectory> listViewParentItems;

    public ReportMenuItems(ArrayList<PackageDirectory>  listViewParentItems) {
        this.listViewParentItems = listViewParentItems;

    }

    public ArrayList<PackageDirectory> getMenuItems() {
        return listViewParentItems;
    }
}
