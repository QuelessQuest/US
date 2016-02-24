package com.barrypress.us.db;

import com.barrypress.us.object.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

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

    public String getAllCards() {

        String query = "SELECT C.NAME, C.PERMITS, C.ELECTION, C.WEALTH, C.PRESTIGE, V.NAME AS VNAMES, C.VOCATION_VALUE, C.EFFECT, C.CARD_TYPE, C.PERMIT_TYPE " +
                "FROM CARDS C, VOCATIONS V WHERE C.VOCATION = V.ID";

        Statement stmt;
        String returnString = "";
        List<Card> cards = new ArrayList<>();

        try {
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Card card = new Card();
                card.setName(rs.getString("NAME"));
                card.setPermits(rs.getInt("PERMITS"));
                card.setElection(rs.getBoolean("ELECTION"));
                card.setWealth(rs.getInt("WEALTH"));
                card.setPrestige(rs.getInt("PRESTIGE"));
                card.setEffect(rs.getString("EFFECT"));

                List<Vocation> vocations = new ArrayList<>();
                List<String> vocationValues = Arrays.asList(String.valueOf(rs.getInt("VOCATION_VALUE")).split(""));
                List<String> vocationNames = Arrays.asList(rs.getString("VNAMES").split(","));

                for (int i = 0; i < vocationNames.size(); i++) {
                    Vocation vocation = new Vocation();
                    vocation.setName(vocationNames.get(i));
                    vocation.setValue(Integer.parseInt(vocationValues.get(i)));
                    vocations.add(vocation);
                }

                card.setVocation(vocations);
                card.setPermitType(Arrays.asList(String.valueOf(rs.getInt("PERMIT_TYPE")).split("")).stream().map(Integer::valueOf).collect(Collectors.toList()));
                cards.add(card);
            }

            returnString = new Gson().toJson(cards, List.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnString;
    }

    public String getCards(Integer deck, String items) {

        String query = "SELECT C.NAME, C.PERMITS, C.ELECTION, C.WEALTH, C.PRESTIGE, V.NAME AS VNAMES, C.VOCATION_VALUE, C.EFFECT, C.CARD_TYPE, C.PERMIT_TYPE " +
                "FROM CARDS C, VOCATIONS V WHERE C.CARD_TYPE = ? AND C.VOCATION = V.ID AND C.ID IN (" + items + ")";

        PreparedStatement pStmt;
        String returnString = "";
        List<Card> cards = new ArrayList<>();

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, deck);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                Card card = new Card();
                card.setName(rs.getString("NAME"));
                card.setPermits(rs.getInt("PERMITS"));
                card.setElection(rs.getBoolean("ELECTION"));
                card.setWealth(rs.getInt("WEALTH"));
                card.setPrestige(rs.getInt("PRESTIGE"));
                card.setEffect(rs.getString("EFFECT"));

                List<Vocation> vocations = new ArrayList<>();
                List<String> vocationValues = Arrays.asList(String.valueOf(rs.getInt("VOCATION_VALUE")).split(""));
                List<String> vocationNames = Arrays.asList(rs.getString("VNAMES").split(","));

                for (int i = 0; i < vocationNames.size(); i++) {
                    Vocation vocation = new Vocation();
                    vocation.setName(vocationNames.get(i));
                    vocation.setValue(Integer.parseInt(vocationValues.get(i)));
                    vocations.add(vocation);
                }

                card.setVocation(vocations);
                card.setPermitType(Arrays.asList(String.valueOf(rs.getInt("PERMIT_TYPE")).split("")).stream().map(Integer::valueOf).collect(Collectors.toList()));
                cards.add(card);
            }

            returnString = new Gson().toJson(cards, List.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnString;
    }

    public String getDecks(Integer gameId) {

        String query = "SELECT DRAW, DISCARD, CARD_TYPE FROM DECKS WHERE GAME_ID = ?";

        PreparedStatement pStmt;
        List<Deck> decks = new ArrayList<>();

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, gameId);

            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                String draw = rs.getString("DRAW");
                String discard = rs.getString("DISCARD");

                Deck deck = new Deck();
                deck.setType(rs.getInt("CARD_TYPE"));
                deck.setDraw(Arrays.asList(draw.split(",")).stream().map(Integer::valueOf).collect(Collectors.toList()));
                deck.setDiscard(StringUtils.isEmpty(discard) ? new ArrayList<>() : Arrays.asList(discard.split(",")).stream().map(Integer::valueOf).collect(Collectors.toList()));

                decks.add(deck);
            }

            rs.close();
            pStmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Gson().toJson(decks, List.class);
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
        String query = "SELECT player_one, player_two, player_three, player_four, active_player, phase, " +
                "(select u.name from USERS u, PLAYERS p, GAME g where p.id = g.player_one and p.user_id = u.id) as p1name, " +
                "(select u.name from USERS u, PLAYERS p, GAME g where p.id = g.player_two and p.user_id = u.id) as p2name, " +
                "(select u.name from USERS u, PLAYERS p, GAME g where p.id = g.player_three and p.user_id = u.id) as p3name, " +
                "(select u.name from USERS u, PLAYERS p, GAME g where p.id = g.player_four and p.user_id = u.id) as p4name " +
                "from GAME g where g.id = ?";

        PreparedStatement pStmt;

        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setInt(1, gameID);
            ResultSet rs = pStmt.executeQuery();

            String q2 = "SELECT id, color, wealth, prestige, favor, permits, vocations, offices FROM PLAYERS WHERE id = ?";

            Players players = new Players();
            if (rs.next()) {
                Player player1 = new Player(rs.getInt("player_one"), rs.getString("p1name"));
                Player player2 = new Player(rs.getInt("player_two"), rs.getString("p2name"));
                Player player3 = new Player(rs.getInt("player_three"), rs.getString("p3name"));
                Player player4 = new Player(rs.getInt("player_four"), rs.getString("p4name"));

                players.setActivePlayer(rs.getInt("active_player"));
                players.setPhase(rs.getString("phase"));
                players.getPlayers().add(player1);
                players.getPlayers().add(player2);
                players.getPlayers().add(player3);
                players.getPlayers().add(player4);
            }

            rs.close();
            pStmt.close();
            pStmt = conn.prepareStatement(q2);

            for (Player player : players.getPlayers()) {
                pStmt.setInt(1, player.getId());
                rs = pStmt.executeQuery();

                if (rs.next()) {
                    player.setColor(rs.getInt("color"));
                    player.setWealth(rs.getInt("wealth"));
                    player.setPrestige(rs.getInt("prestige"));
                    player.setFavor(rs.getInt("favor"));
                    player.setCards(rs.getString("permits"));
                    player.setVocations(rs.getString("vocations"));
                    player.setPoliticians(rs.getString("offices"));
                }

                rs.close();
            }

            returnString = new Gson().toJson(players, Players.class);

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
