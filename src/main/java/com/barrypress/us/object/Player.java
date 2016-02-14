package com.barrypress.us.object;

public class Player {

    private String name;
    private Integer id;
    private Integer color;
    private Integer wealth;
    private Integer prestige;
    private String vocations;
    private String cards;
    private String politicians;
    private Integer favor;

    public Player(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
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

    public String getVocations() {
        return vocations;
    }

    public void setVocations(String vocations) {
        this.vocations = vocations;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public String getPoliticians() {
        return politicians;
    }

    public void setPoliticians(String politicians) {
        this.politicians = politicians;
    }

    public Integer getFavor() {
        return favor;
    }

    public void setFavor(Integer favor) {
        this.favor = favor;
    }
}
