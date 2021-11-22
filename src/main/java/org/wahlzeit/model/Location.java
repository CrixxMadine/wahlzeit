package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Location {

    private final Coordinate coordinate;

    public Location(Coordinate coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException("Coordinate argument was null");
        }
        this.coordinate = coordinate;
    }

    public static Location readFrom(ResultSet resultSet, Coordinate typeReference) throws SQLException {
        return new Location(typeReference.readFrom(resultSet));
    }

    public void writeOn(ResultSet rset) throws SQLException {
        this.coordinate.writeOn(rset);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
