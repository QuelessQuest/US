package com.barrypress.us.object;

import com.barrypress.us.db.Derby;
import com.barrypress.us.util.Constants;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PermitDeck extends Deck {

    public PermitDeck(Derby db) {
        super(db);
    }

    @Override
    public void setup(Integer gameID, Integer type) {
        super.setup(gameID, type);

        discard = db.getCardsByName(Constants.CARD_PERMIT, Constants.URBAN_RENEWAL);

        List<Integer> allCards = db.getAllExcept(Constants.CARD_PERMIT, Constants.URBAN_RENEWAL);
        Collections.shuffle(allCards, new Random(System.nanoTime()));

        List<Integer> bottom = allCards.subList(0, 13);
        bottom.addAll(db.getEvents(Constants.CARD_PERMIT));
        Collections.shuffle(bottom, new Random(System.nanoTime()));

        draw.addAll(allCards.subList(14, allCards.size()));
        draw.addAll(bottom);

        db.writeDeck(gameID, Constants.CARD_PERMIT, draw, discard);
    }
}
