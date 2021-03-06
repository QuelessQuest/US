package com.barrypress.us.object;

import com.barrypress.us.db.Derby;
import com.barrypress.us.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CityDeck extends Deck {

    public CityDeck(Derby db) {
        super(db);
    }

    @Override
    public void setup(Integer gameID, Integer type) {
        super.setup(gameID, type);

        List<Integer> allCards = db.getAllExcept(Constants.CARD_CITY, Constants.SPORTS_TEAM, true);
        Collections.shuffle(allCards, new Random(System.nanoTime()));

        List<Integer> bottom = new ArrayList<>();
        bottom.addAll(allCards.subList(0, 15));
        List<Integer> middle = new ArrayList<>();
        middle.addAll(allCards.subList(15, 21));
        middle.addAll(db.getCardsByName(Constants.CARD_CITY, Constants.SPORTS_TEAM));
        Collections.shuffle(middle, new Random(System.nanoTime()));

        draw.addAll(allCards.subList(21, allCards.size()));
        draw.addAll(middle);
        draw.addAll(bottom);

        db.writeDeck(gameID, Constants.CARD_CITY, draw, discard);
    }
}
