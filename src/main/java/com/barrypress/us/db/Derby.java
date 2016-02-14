package com.barrypress.us.db;

import com.barrypress.us.object.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Derby {

    public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private Connection conn;

    public Derby() {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection("jdbc:derby:/Users/lbarry/Documents/us/USDb", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:/Users/lbarry/Documents/us/USDb;shutdown=true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getCardsByName(Integer type, String name) {

        List<Integer> cards = new ArrayList<>();
        String query = "SELECT id FROM CARDS WHERE CARD_TYPE = ? AND NAME = ?";
        PreparedStatement pStmt = null;

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, type);
            pStmt.setString(2, name);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                cards.add(rs.getInt("id"));
            }

            rs.close();
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cards;
    }

    public List<Integer> getAllExcept(Integer type, String name) {
        return getAllExcept(type, name, false);
    }

    public List<Integer> getAllExcept(Integer type, String name, Boolean event) {

        List<Integer> cards = new ArrayList<>();
        String query = "SELECT id FROM CARDS WHERE CARD_TYPE = ? AND NAME != ?";
        if (!event) {
            query += " AND EVENT = 0";
        }

        PreparedStatement pStmt;

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, type);
            pStmt.setString(2, name);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                cards.add(rs.getInt("id"));
            }

            rs.close();
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cards;
    }

    public List<Integer> getEvents(Integer type) {

        List<Integer> cards = new ArrayList<>();
        String query = "SELECT id FROM CARDS WHERE CARD_TYPE = ? AND EVENT = 1";

        PreparedStatement pStmt;

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, type);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                cards.add(rs.getInt("id"));
            }

            rs.close();
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cards;
    }

    public Card getCard(Integer id) {

        Card card = new Card();

        String query = "SELECT NAME, PERMITS, ELECTION, WEALTH, PRESTIGE, VOCATION, VOCATION_VALUE, EFFECT FROM CARDS WHERE ID = ?";

        PreparedStatement pStmt;

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, id);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                card.setName(rs.getString("NAME"));
                card.setPermits(rs.getInt("PERMITS"));
                card.setElection(rs.getInt("ELECTION") != 0);
                card.setWealth(rs.getInt("WEALTH"));
                card.setPrestige(rs.getInt("PRESTIGE"));
                card.setEffect(Class.forName(rs.getString("EFFECT")));
                Vocation vocation = new Vocation();
                vocation.setName(rs.getString("VOCATION"));
                vocation.setValue(rs.getInt("VOCATION_VALUE"));
                card.setVocation(vocation);
            }
            rs.close();
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return card;
    }

    @SuppressWarnings("unchecked")
    public void writeDeck(Integer gameID, Integer type, List<Integer> draw, List<Integer> discard) {

        String drawString = draw.stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(","));

        String discardString = discard.stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(","));

        String query = "SELECT GAME_ID FROM DECKS WHERE GAME_ID = ? AND CARD_TYPE = ?";

        PreparedStatement pStmt;
        PreparedStatement pStmt2;

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, gameID);
            pStmt.setInt(2, type);

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                query = "UPDATE DECKS SET DRAW = ?, DISCARD = ? WHERE GAME_ID = ?";
                pStmt2 = conn.prepareStatement(query);
                pStmt2.setString(1, drawString);
                pStmt2.setString(2, discardString);
                pStmt2.setInt(3, gameID);
                pStmt2.executeUpdate();
                pStmt2.close();
            } else {
                query = "INSERT INTO DECKS (GAME_ID, DRAW, DISCARD, CARD_TYPE) VALUES (?, ?, ?, ?)";
                pStmt2 = conn.prepareStatement(query);
                pStmt2.setInt(1, gameID);
                pStmt2.setString(2, drawString);
                pStmt2.setString(3, discardString);
                pStmt2.setInt(4, type);
                pStmt2.executeUpdate();
                pStmt2.close();
            }
            pStmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveCity(Integer gameID, City city) {

        String columns = new Gson().toJson(city.getColumns(), new TypeToken<Collection<Column>>(){}.getType());
        String rows = new Gson().toJson(city.getRows(), new TypeToken<Collection<Row>>(){}.getType());
        String query = "SELECT GAME_ID FROM CITIES WHERE GAME_ID = ?";

        PreparedStatement pStmt;
        PreparedStatement pStmt2;

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, gameID);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                query = "UPDATE CITIES SET CITY_ROWS = ?, CITY_COLUMNS = ? WHERE GAME_ID = ?";
                pStmt2 = conn.prepareStatement(query);
                pStmt2.setString(1, rows);
                pStmt2.setString(2, columns);
                pStmt2.setInt(3, gameID);
                pStmt2.executeUpdate();
                pStmt2.close();
            } else {
                query = "INSERT INTO CITIES (GAME_ID, CITY_ROWS, CITY_COLUMNS) VALUES (?, ?, ?)";
                pStmt2 = conn.prepareStatement(query);
                pStmt2.setInt(1, gameID);
                pStmt2.setString(2, rows);
                pStmt2.setString(3, columns);
                pStmt2.executeUpdate();
                pStmt2.close();
            }
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer createGame() {

        String query = "SELECT MAX(ID) AS id FROM GAME";

        Statement stmt;
        PreparedStatement pStmt;
        Integer gameID = 0;

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                gameID = rs.getInt("id") + 1;
            }
            rs.close();
            stmt.close();
            query = "INSERT INTO GAME (ID, PHASE) values (?, ?)";
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, gameID);
            pStmt.setString(2, "SETUP");
            pStmt.executeUpdate();
            conn.commit();
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameID;
    }

    public Game loadGame(Integer gameID) {

        Game game = null;


        return game;
    }

    public String loadPlayers(Integer gameID) {

        String returnString = "";
        String query = "select active_player, phase, player_one, (select p.name from PLAYERS p, GAME g where p.id = g.player_one) as p1name, " +
                "player_two, (select p.name from PLAYERS p, GAME g where p.id = g.player_two) as p2name, " +
                "player_three, (select p.name from PLAYERS p, GAME g where p.id = g.player_three) as p3name, " +
                "player_four, (select p.name from PLAYERS p, GAME g where p.id = g.player_four) as p4name " +
                "from GAME g where g.id = ?";

        PreparedStatement pStmt;

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, gameID);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                Players players = new Players();
                players.setPlayerOne(rs.getString("player_one"));
                players.setPlayerTwo(rs.getString("player_two"));
                players.setPlayerThree(rs.getString("player_three"));
                players.setPlayerFour(rs.getString("player_four"));
                players.setActivePlayer(rs.getString("active_player"));
                players.setState(rs.getString("phase"));
                players.setP1Name(rs.getString("p1name"));
                players.setP2Name(rs.getString("p2name"));
                players.setP3Name(rs.getString("p3name"));
                players.setP4Name(rs.getString("p4name"));

                returnString = new Gson().toJson(players, Players.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnString;
    }

    public String loadCity(Integer gameID, String column) {

        String query = "SELECT " + column + " FROM CITIES WHERE GAME_ID = ?";
        String returnString = "";

        PreparedStatement pStmt;

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, gameID);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                returnString = rs.getString(column);
            }
            rs.close();
            pStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnString;
    }

    public void addPlayer(Integer gameID, String name) {

        String query = "SELECT MAX(ID) AS id FROM PLAYER";

        Statement stmt;
        PreparedStatement pStmt;
        Integer playerID = 0;

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                playerID = rs.getInt("id") + 1;
            }
            rs.close();
            stmt.close();
            /*    private Integer wealth;
    private Integer prestige;
    private List<Vocation> vocations;
    private List<Card> cards;
    private List<Politician> politicians;
    private Card favor;

    { "v" : [ { "voc" : "test", "val" : "1" } ] }
    1, 5, 7
    { "p" : [ { "name" : "Mayor", "ability" : "class", "bonus" : "class" } ] }
    */

            query = "INSERT INTO PLAYER (ID, NAME, WEALTH, PRESTIGE, VOCATIONS, CARDS, POLITICANS, FAVOR) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, playerID);
            pStmt.setString(2, name);
            pStmt.setInt(3, 0);
            pStmt.setInt(4, 0);
            pStmt.setString(5, "");
            pStmt.setString(6, "");
            pStmt.setString(7, "");
            pStmt.setString(8, "");

            pStmt.executeUpdate();
            pStmt.close();

            query = "SELECT PLAYERS FROM GAME WHERE ID = ?";
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, gameID);

            rs = pStmt.executeQuery();
            if (rs.next()) {
                String[] players = rs.getString("PLAYERS").split(",");
                List<String> pList = Arrays.stream(players).collect(Collectors.toList());
                if (!pList.contains(String.valueOf(playerID))) {
                    pList.add(String.valueOf(playerID));
                }

                String query2 = "UPDATE GAME SET PLAYERS = ? WHERE ID = ?";
                PreparedStatement pStmt2 = conn.prepareStatement(query2);
                pStmt2.setString(1, String.join(",", pList));
                pStmt2.setInt(2, gameID);

                pStmt2.executeUpdate();
                pStmt2.close();
            }
            rs.close();
            pStmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
