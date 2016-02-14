package com.barrypress.us.object;

import com.barrypress.us.db.Derby;
import com.barrypress.us.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Integer id;
    private PermitDeck permitDeck;
    private TownDeck townDeck;
    private CityDeck cityDeck;
    private MetroDeck metroDeck;
    private Derby db;
    private City city;
    private List<Player> players;

    public Game(Derby db) {
        this.db = db;
    }

    public void setup() {
        permitDeck = new PermitDeck(db);
        townDeck = new TownDeck(db);
        cityDeck = new CityDeck(db);
        metroDeck = new MetroDeck(db);
        players = new ArrayList<>();

        id = db.createGame();

        //permitDeck.setup(id, Constants.CARD_PERMIT);
        townDeck.setup(id, Constants.CARD_TOWN);
        cityDeck.setup(id, Constants.CARD_CITY);
        metroDeck.setup(id, Constants.CARD_METRO);

        city = new City(id, db);
        city.setup();
    }

    public void save() {

    }

    public Player addPlayer(String name) {

        Player player = new Player(name);
        players.add(player);

        db.addPlayer(id, name);
        return player;
    }
}
