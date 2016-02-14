package com.barrypress.us.object;

import com.barrypress.us.util.Constants;

import java.util.List;

public class Card {

    private String name;
    private List<Constants.Zone> zones;
    private Integer permits;
    private Boolean election;
    private Integer wealth;
    private Integer prestige;
    private Vocation vocation;
    private Class effect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Constants.Zone> getZones() {
        return zones;
    }

    public void setZones(List<Constants.Zone> zones) {
        this.zones = zones;
    }

    public Integer getPermits() {
        return permits;
    }

    public void setPermits(Integer permits) {
        this.permits = permits;
    }

    public Boolean getElection() {
        return election;
    }

    public void setElection(Boolean election) {
        this.election = election;
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

    public Vocation getVocation() {
        return vocation;
    }

    public void setVocation(Vocation vocation) {
        this.vocation = vocation;
    }

    public Class getEffect() {
        return effect;
    }

    public void setEffect(Class effect) {
        this.effect = effect;
    }
}
