package com.barrypress.us.object;

import com.barrypress.us.util.Constants;

public class Column {

    private Integer wealth;
    private Integer value;
    private Constants.BonusType type;

    public Column() {
        value = 0;
        type = Constants.BonusType.WEALTH;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Constants.BonusType getType() {
        return type;
    }

    public void setType(Constants.BonusType type) {
        this.type = type;
    }

    public Integer getWealth() {
        return wealth;
    }

    public void setWealth(Integer wealth) {
        this.wealth = wealth;
    }
}
