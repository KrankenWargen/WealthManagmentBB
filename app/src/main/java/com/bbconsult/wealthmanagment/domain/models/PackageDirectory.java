package com.bbconsult.wealthmanagment.domain.models;

import java.util.ArrayList;

public class PackageDirectory {
    private final ArrayList<PackageDirectory> childItemList;
    private String name;
    private String type = "";
    private String parent = "";
    private String firstComponent = "err";

    public PackageDirectory() {
        childItemList = new ArrayList<PackageDirectory>();
    }

    public ArrayList<PackageDirectory> getChildItemList() {
        return childItemList;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstComponent() {
        return firstComponent;
    }

    public void setFirstComponent(String firstComponent) {
        this.firstComponent = firstComponent;
    }
}
