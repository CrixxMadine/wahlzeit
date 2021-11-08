package org.wahlzeit.model;

public class Location {

    private final Coordinate coordinate;

    public Location(Coordinate coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException("Coordinate argument was null");
        }
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
