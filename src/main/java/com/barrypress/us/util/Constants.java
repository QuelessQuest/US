package com.barrypress.us.util;

import com.barrypress.us.db.Derby;

public class Constants {

    public static final Integer CARD_PERMIT = 1;
    public static final Integer CARD_TOWN = 2;
    public static final Integer CARD_CITY = 3;
    public static final Integer CARD_METRO = 4;

    public static final String URBAN_RENEWAL = "Urban Renewal";
    public static final String AIRPORT = "Airport";
    public static final String SPORTS_TEAM = "Sports Team";
    public static final String OLYMPIC_GAMES = "Olympic Games";

    public enum Zone { CIV, RES, IND, COM, PARK }
    public enum BonusType { WEALTH, PRESTIGE }

    public static Derby db = new Derby();
}
