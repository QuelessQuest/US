package com.barrypress.us.main;

import com.barrypress.us.db.Derby;
import com.barrypress.us.object.Game;
import com.barrypress.us.util.Constants;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class GameResource extends ServerResource {

    private String json;
    Derby db;

    @Override
    public void doInit() {
        db = Constants.db;
    }

    @Get
    public Representation doGet() {

        String action = (String) getRequestAttributes().get("action");
        Game game;

        if (action.equalsIgnoreCase("create")) {
            game = new Game(db);
            game.setup();
        }
        if (action.equalsIgnoreCase("load")) {
            Integer gameId = Integer.parseInt((String) getRequestAttributes().get("id"));
            String type = (String) getRequestAttributes().get("type");

            switch (type) {
                case "CityRows":
                    json = db.loadCity(gameId, "CITY_ROWS");
                    break;
                case "CityColumns":
                    json = db.loadCity(gameId, "CITY_COLUMNS");
                    break;
                case "Players":
                    json = db.loadPlayers(gameId);
                    break;
                default:
            }
        }

        return new StringRepresentation(json, MediaType.APPLICATION_JSON);
    }
}
