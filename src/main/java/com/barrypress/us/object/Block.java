package com.barrypress.us.object;

import java.util.ArrayList;
import java.util.List;

public class Block {

    private Integer row;
    private Integer column;
    private List<Building> buildings;
    private Integer wealth;
    private Integer prestige;

    public Block() {
        wealth = 0;
        prestige = 0;
        buildings = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Building building = new Building();
            buildings.add(building);
        }
    }

    public Block(Integer row, Integer column) {
        wealth = 0;
        prestige = 0;
        buildings = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Building building = new Building();
            buildings.add(building);
        }
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getWealth() {
        return wealth;
    }

    public void setWealth(Integer wealth) {
        this.wealth = wealth;
    }

    public Integer getPrestige() {
        return prestige;
    }

    public void setPrestige(Integer prestige) {
        this.prestige = prestige;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
