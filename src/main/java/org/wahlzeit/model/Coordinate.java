package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contract to work with different types of coordinates
 */
public interface Coordinate {

    /**
     * Returns the cartesian representation of this coordinate (not null)
     * @return instance of CartesianCoordinate
     */
    CartesianCoordinate asCartesianCoordinate();

    /**
     * Calculates the direct Cartesian distance between this coordinate and another
     * @param other  Coordinate for distance calculation, not null
     * @return direct Cartesian distance
     * @throws ArithmeticException when distance would be infinite (unchecked)
     * @throws IllegalArgumentException when provided argument was null (unchecked)
     */
    double getCartesianDistance(Coordinate other) throws ArithmeticException, IllegalArgumentException;


    /**
     * Returns the spheric representation of this coordinate (not null)
     * @return instance of SphericCoordinate
     * @throws ArithmeticException when conversion can not be calculated due to arithmetic errors (unchecked)
     */
    SphericCoordinate asSphericCoordinate() throws ArithmeticException;

    /**
     * Calculates the great central angle between two coordinates using float64 precision
     * For reference see: https://en.wikipedia.org/wiki/Great-circle_distance
     * @return The calculated great central angle
     * @throws ArithmeticException when distance would be infinite (unchecked)
     * @throws IllegalArgumentException when other coordinate is null (unchecked)
     */
    double getCentralAngle(Coordinate other) throws ArithmeticException, IllegalArgumentException;

    /**
     * Compares this coordinate with another instance of Coordinate
     * Returns true when the finite distance between this and other coordinate is less or equal to threshold epsilon
     * For not finite distance between coordinates returns true when all cartesian fields are equal
     * It is valid to compare different types of coordinates, like Cartesian and Spheric
     * @param other  Coordinate for comparison, may be null
     */
    boolean isEqual(Coordinate other);

    /**
     * Read coordinates from database
     * Uses cartesian coordinates as default representation but may be overridden by subclass
     * @param resultSet SQL result set containing column labels for cartesian coordinate
     * @return instance of CartesianCoordinate (not null)
     * @throws SQLException rethrown from result set
     * @throws IllegalArgumentException when resultSet is null (unchecked)
     */
    Coordinate readFrom(ResultSet resultSet) throws SQLException, IllegalArgumentException;

    /**
     * Store coordinates to database
     * Uses cartesian coordinates for default representation but may be overridden by subclass
     * @param resultSet SQL result set containing column labels for cartesian coordinate
     * @throws SQLException rethrown from resultSet
     * @throws IllegalArgumentException when resultSet is null (unchecked)
     */
    void writeOn(ResultSet resultSet) throws SQLException, IllegalArgumentException;
}
