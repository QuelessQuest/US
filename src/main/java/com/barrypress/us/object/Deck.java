package com.barrypress.us.object;

import com.barrypress.us.db.Derby;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

    protected Integer type;
    protected List<Integer> draw;
    protected List<Integer> discard;
    protected Derby db;

    public Deck() {}

    public Deck(Derby db) {
        this.db = db;
    }

    public void setup(Integer gameID, Integer type) {
        this.type = type;
        this.draw = new ArrayList<>();
        this.discard = new ArrayList<>();
    }

    public void discard(Integer id) {
        discard.add(id);
    }

    public void reshuffle() {
        draw.addAll(discard);
        Collections.shuffle(draw, new Random(System.nanoTime()));
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Integer> getDraw() {
        return draw;
    }

    public void setDraw(List<Integer> draw) {
        this.draw = draw;
    }

    public List<Integer> getDiscard() {
        return discard;
    }

    public void setDiscard(List<Integer> discard) {
        this.discard = discard;
    }
}
