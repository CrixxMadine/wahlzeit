package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Location {

    private final Coordinate coordinate;

    /**
     * Constructor
     * @param coordinate Valid instance of coordinate describing the location
     * @throws IllegalArgumentException when provided Coordinate was null (unchecked)
     */
    public Location(Coordinate coordinate) throws IllegalArgumentException {
        if (coordinate == null) {
            throw new IllegalArgumentException("Coordinate argument was null");
        }
        this.coordinate = coordinate;
    }

    /**
     *
     * @param resultSet Result set containing record for the expected coordinate
     * @param typeReference Any instance of the expected Coordinate type. Value is ignored, is only used for type reference
     * @return New instance of location with coordinate type provided
     * @throws SQLException Any exception related to database access occurs (checked)
     * @throws IllegalArgumentException When any provided argument was null (unchecked)
     */
    public static Location readFrom(ResultSet resultSet, Coordinate typeReference) throws SQLException, IllegalArgumentException {
        if (resultSet == null) {
            throw new IllegalArgumentException("Provided result set was null!");
        }

        if (typeReference == null) {
            throw new IllegalArgumentException("No coordinate type reference was provided!");
        }

        return new Location(typeReference.readFrom(resultSet));
    }

    /**
     *
     * @param resultSet
     * @throws SQLException Any exception related to database access occurs (checked)
     * @throws IllegalArgumentException When any provided argument was null (unchecked)
     */
    public void writeOn(ResultSet resultSet) throws SQLException, IllegalArgumentException {
        if (resultSet == null) {
            throw new IllegalArgumentException("Provided resultSet was null!");
        }

        this.coordinate.writeOn(resultSet);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
