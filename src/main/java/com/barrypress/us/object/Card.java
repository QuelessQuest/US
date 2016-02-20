package com.barrypress.us.object;

import java.util.ArrayList;
import java.util.List;

public class Card {

    private String name;
    private Integer permits;
    private Boolean election;
    private Integer wealth;
    private Integer prestige;
    private List<Vocation> vocations;
    private String effect;
    private List<Integer> permitType;

    public Card() {
        permitType = new ArrayList<>();
        vocations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Vocation> getVocations() {
        return vocations;
    }

    public void setVocation(List<Vocation> vocations) {
        this.vocations = vocations;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public List<Integer> getPermitType() {
        return permitType;
    }

    public void setPermitType(List<Integer> permitType) {
        this.permitType = permitType;
    }
}
