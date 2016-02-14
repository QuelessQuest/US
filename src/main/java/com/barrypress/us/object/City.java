package com.barrypress.us.object;

import com.barrypress.us.db.Derby;
import com.barrypress.us.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class City {

    private List<Column> columns;
    private List<Row> rows;
    private Integer gameID;
    private Derby db;

    public City(Integer gameID, Derby db) {
        this.gameID = gameID;
        this.db = db;
    }

    public void setup() {

        columns = new ArrayList<>();
        rows = new ArrayList<>();

        Column column = new Column();
        column.setWealth(1);
        columns.add(column);

        column = new Column();
        column.setWealth(4);
        columns.add(column);

        column = new Column();
        column.setWealth(5);
        columns.add(column);

        column = new Column();
        column.setWealth(6);
        columns.add(column);

        column = new Column();
        column.setWealth(3);
        columns.add(column);

        column = new Column();
        column.setWealth(2);
        columns.add(column);

        Row row = new Row();
        row.setPrestige(3);
        row.setBlocks(createBlocks(0, 3, columns));
        rows.add(row);

        row = new Row();
        row.setPrestige(3);
        row.setBlocks(createBlocks(1, 3, columns));
        rows.add(row);

        row = new Row();
        row.setPrestige(1);
        row.setBlocks(createBlocks(2, 1, columns));
        rows.add(row);

        row = new Row();
        row.setPrestige(1);
        row.setBlocks(createBlocks(3, 1, columns));
        rows.add(row);

        row = new Row();
        row.setPrestige(2);
        row.setBlocks(createBlocks(4, 2, columns));
        rows.add(row);

        row = new Row();
        row.setPrestige(2);
        row.setBlocks(createBlocks(5, 2, columns));
        rows.add(row);

        addBuildings();

        save();
    }

    public void load() {

    }

    public void save() {
        db.saveCity(gameID, this);
    }

    private List<Block> createBlocks(Integer row, Integer prestige, List<Column> columns) {
        List<Block> blocks = new ArrayList<>();

        for (int column = 0; column < 6; column++) {
            Block block = new Block(row, column);
            block.setWealth(columns.get(column).getWealth());
            block.setPrestige(prestige);
            blocks.add(block);
        }

        return blocks;
    }

    private void addBuildings() {

        Block block = rows.get(0).getBlocks().get(0);
        block.getBuildings().get(3).setSize(1);
        block.getBuildings().get(3).setZone(Constants.Zone.RES);
        block.getBuildings().get(3).setName("StarterResOne");

        block = rows.get(0).getBlocks().get(5);
        block.getBuildings().get(2).setSize(1);
        block.getBuildings().get(2).setZone(Constants.Zone.RES);
        block.getBuildings().get(2).setName("StarterResTwo");

        block = rows.get(5).getBlocks().get(0);
        block.getBuildings().get(1).setSize(1);
        block.getBuildings().get(1).setZone(Constants.Zone.RES);
        block.getBuildings().get(1).setName("StarterResThree");

        block = rows.get(5).getBlocks().get(5);
        block.getBuildings().get(0).setSize(1);
        block.getBuildings().get(0).setZone(Constants.Zone.RES);
        block.getBuildings().get(0).setName("StarterResFour");

        block = rows.get(1).getBlocks().get(1);
        block.getBuildings().get(3).setSize(1);
        block.getBuildings().get(3).setZone(Constants.Zone.IND);
        block.getBuildings().get(3).setName("StarterIndOne");

        block = rows.get(2).getBlocks().get(2);
        block.getBuildings().get(3).setSize(1);
        block.getBuildings().get(3).setZone(Constants.Zone.IND);
        block.getBuildings().get(3).setName("StarterIndTwo");

        block = rows.get(3).getBlocks().get(3);
        block.getBuildings().get(0).setSize(1);
        block.getBuildings().get(0).setZone(Constants.Zone.CIV);
        block.getBuildings().get(0).setName("StarterCivOne");

        block = rows.get(4).getBlocks().get(4);
        block.getBuildings().get(0).setSize(1);
        block.getBuildings().get(0).setZone(Constants.Zone.CIV);
        block.getBuildings().get(0).setName("StarterCivTwo");

        block = rows.get(1).getBlocks().get(4);
        block.getBuildings().get(2).setSize(1);
        block.getBuildings().get(2).setZone(Constants.Zone.COM);
        block.getBuildings().get(2).setName("StarterComOne");

        block = rows.get(2).getBlocks().get(3);
        block.getBuildings().get(2).setSize(1);
        block.getBuildings().get(2).setZone(Constants.Zone.COM);
        block.getBuildings().get(2).setName("StarterComTwo");

        block = rows.get(3).getBlocks().get(2);
        block.getBuildings().get(1).setSize(1);
        block.getBuildings().get(1).setZone(Constants.Zone.COM);
        block.getBuildings().get(1).setName("StarterComThree");

        block = rows.get(4).getBlocks().get(1);
        block.getBuildings().get(1).setSize(1);
        block.getBuildings().get(1).setZone(Constants.Zone.COM);
        block.getBuildings().get(1).setName("StarterComFour");
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}
