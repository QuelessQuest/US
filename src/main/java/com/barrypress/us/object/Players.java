package com.barrypress.us.object;

import java.util.ArrayList;
import java.util.List;

public class Players {

    private Integer activePlayer;
    private String phase;
    private List<Player> players;

    public Players() {
        players = new ArrayList<>();
    }

    public Integer getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Integer activePlayer) {
        this.activePlayer = activePlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
