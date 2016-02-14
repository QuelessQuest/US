package com.barrypress.us.object;

import com.barrypress.us.util.Constants;

public class Building {

    private Constants.Zone zone;
    private Integer size;
    private Integer owner;
    private String name;

    public Building() {
        this.name = "NONE";
        this.owner = 0;
    }

    public Constants.Zone getZone() {
        return zone;
    }

    public void setZone(Constants.Zone zone) {
        this.zone = zone;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
