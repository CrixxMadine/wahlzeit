package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contract to work with different types of coordinates
 */
public interface Coordinate {
    CartesianCoordinate asCartesianCoordinate();
    double getCartesianDistance(Coordinate other);
    SphericCoordinate asSphericCoordinate();
    double getCentralAngle(Coordinate other);
    boolean isEqual(Coordinate other);

    Coordinate readFrom(ResultSet rset) throws SQLException;
    void writeOn(ResultSet rset) throws SQLException;
}
