package com.barrypress.us.object;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private Integer wealth;
    private Integer prestige;
    private List<Vocation> vocations;
    private List<Card> cards;
    private List<Politician> politicians;
    private Card favor;

    public Player(String name) {
        this.name = name;
        wealth = 0;
        prestige = 0;
        vocations = new ArrayList<>();
        cards = new ArrayList<>();
        politicians = new ArrayList<>();
        favor = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setVocations(List<Vocation> vocations) {
        this.vocations = vocations;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Politician> getPoliticians() {
        return politicians;
    }

    public void setPoliticians(List<Politician> politicians) {
        this.politicians = politicians;
    }

    public Card getFavor() {
        return favor;
    }

    public void setFavor(Card favor) {
        this.favor = favor;
    }
}
