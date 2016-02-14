package com.barrypress.us.object;

import com.barrypress.us.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Row {

    private Integer prestige;
    private Integer value;
    private Constants.BonusType type;
    private List<Block> blocks;

    public Row() {
        blocks = new ArrayList<>();
        value = 0;
        type = Constants.BonusType.PRESTIGE;
    }

    public Integer getPrestige() {
        return prestige;
    }

    public void setPrestige(Integer prestige) {
        this.prestige = prestige;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public Constants.BonusType getType() {
        return type;
    }

    public void setType(Constants.BonusType type) {
        this.type = type;
    }
}
