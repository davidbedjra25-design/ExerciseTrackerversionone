package ca.umanitoba.comp2450.exercisetracker.model;

public class WorldMap {
    private final Map worldMap;

    public WorldMap() {
        this.worldMap = new Map.MapBuilder().width(20).height(20).build();
        this.worldMap.createGrid();
    }

    public Map getWorldMap() {
        return this.worldMap;
    }

}
