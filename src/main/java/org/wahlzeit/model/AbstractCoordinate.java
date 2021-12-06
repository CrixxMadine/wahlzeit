package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the base class for Coordinate implementations
 * Used cartesian representation as default to read and write coordinates from database
 */
public abstract class AbstractCoordinate implements Coordinate {


    /**
     * If two coordinates have a finite distance smaller or equal epsilon they are considered equal
     */
    public static final double EPSILON_DISTANCE = 0.0001;


    /**
     * Calculates cartesian distance by deferring it to implementation of CartesianCoordinate
     */
    @Override
    public double getCartesianDistance(Coordinate other) {
        var thisCartesian = safeConvertToCartesian(this);
        return thisCartesian.getCartesianDistance(other);
    }

    protected static CartesianCoordinate safeConvertToCartesian(Coordinate any) {
        if (any == null) {
            throw new IllegalArgumentException("Provided argument was null");
        }

        var anyCartesian = any.asCartesianCoordinate();

        // Someone could override method in subclass
        if (anyCartesian == null) {
            throw new IllegalStateException("Cartesian representation of provided coordinate was null");
        }

        return anyCartesian;
    }

    /**
     * Calculates cartesian distance by deferring it to implementation of SphericCoordinate
     */
    @Override
    public double getCentralAngle(Coordinate other) {
        var thisSpheric = safeConvertToSpheric(this);
        return thisSpheric.getCentralAngle(other);
    }

    protected static SphericCoordinate safeConvertToSpheric(Coordinate any) {
        if (any == null) {
            throw new IllegalArgumentException("Provided argument was null");
        }

        var anySpheric = any.asSphericCoordinate();

        // Someone could override method in subclass
        if (anySpheric == null) {
            throw new IllegalStateException("Spheric representation of provided coordinate was null");
        }

        return anySpheric;
    }

    @Override
    public boolean isEqual(Coordinate other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        double distance = this.getCartesianDistance(other);

        if (Double.isFinite(distance)) {
            var absoluteDistance = distance;
            if (distance < 0) {
                absoluteDistance = -distance;
            }
            return absoluteDistance <= EPSILON_DISTANCE;
        }
        else {
            var thisCartesian = safeConvertToCartesian(this);
            return thisCartesian.compareCartesianValuesExplicitly(other);
        }
    }


    /**
     * Default reading of coordinate from database as cartesian coordinate
     */
    @Override
    public Coordinate readFrom(ResultSet resultSet) throws SQLException {
        var thisCartesian = safeConvertToCartesian(this);

        return thisCartesian.readFrom(resultSet);
    }

    /**
     * Default writing of coordinate to database as cartesian coordinate
     */
    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        var thisCartesian = safeConvertToCartesian(this);
        thisCartesian.writeOn(resultSet);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        return isEqual((Coordinate) other);
    }

    /**
     * Default hashcode that
     */
    @Override
    public int hashCode() {
        var cartesian = safeConvertToCartesian(this);
        return cartesian.hashCode();
    }
}
