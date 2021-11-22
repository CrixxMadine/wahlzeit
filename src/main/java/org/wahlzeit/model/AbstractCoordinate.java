package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class AbstractCoordinate implements Coordinate {

    private static class ColumnLabels {
        public static final String X_COORDINATE = "x_coordinate";
        public static final String Y_COORDINATE = "y_coordinate";
        public static final String Z_COORDINATE = "z_coordinate";
    }

    /**
     * If two coordinates have a finite distance smaller or equal epsilon they are considered equal
     */
    public static final double EPSILON_DISTANCE = 0.0001;


    /**
     * Calculates the direct Cartesian distance between this coordinate and another
     * @param other  Coordinate for distance calculation, not null
     * @return direct Cartesian distance
     * @throws IllegalArgumentException  when provided argument was null
     */
    @Override
    public double getCartesianDistance(Coordinate other) {
        if (other == null) {
            throw new IllegalArgumentException("Coordinate was null, provide valid argument");
        }

        var thisCartesian = this.asCartesianCoordinate();
        var otherCartesian = other.asCartesianCoordinate();

        double delta_x = otherCartesian.getX() - thisCartesian.getX();
        double delta_y = otherCartesian.getY() - thisCartesian.getY();
        double delta_z = otherCartesian.getZ() - thisCartesian.getZ();

        return Math.sqrt(
                Math.pow((delta_x),2) + Math.pow(delta_y,2) + Math.pow(delta_z,2)
        );
    }


    /**
     * Calculates the great central angle between two coordinates using float64 precision
     * For reference see: https://en.wikipedia.org/wiki/Great-circle_distance
     * @return The calculated great central angle
     */
    @Override
    public double getCentralAngle(Coordinate other) {
        if (other == null) {
            throw new IllegalArgumentException("Coordinate was null, provide valid argument");
        }

        var thisSpheric = this.asSphericCoordinate();
        var otherSpheric = other.asSphericCoordinate();

        var thisPhi = thisSpheric.getPhi();
        var otherPhi = otherSpheric.getPhi();
        
        var thisTheta = thisSpheric.getTheta();
        var otherTheta = otherSpheric.getTheta();
        var deltaTheta = Math.abs(thisTheta - otherTheta);

        var leftSummand = Math.sin(thisPhi) * Math.sin(otherPhi);
        var rightSummand = Math.cos(thisPhi) * Math.cos(otherPhi) * Math.cos(deltaTheta);

        return Math.acos(leftSummand + rightSummand);
    }

    /**
     * Compares this coordinate with another instance of Coordinate
     * Returns true when the finite distance between this and other coordinate is less or equal to threshold epsilon
     * For not finite distance between coordinates returns true when all cartesian fields are equal
     * It is valid to compare different types of coordinates, like Cartesian and Spheric
     * @param other  Coordinate for comparison, may be null
     */
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
            return compareCartesianValuesExplicitly(this, other);
        }
    }

    private boolean compareCartesianValuesExplicitly(Coordinate first, Coordinate second) {
        var left = first.asCartesianCoordinate();
        var right = second.asCartesianCoordinate();

        return Double.compare(left.getX(), right.getX()) == 0 &&
                Double.compare(left.getY(), right.getY()) == 0 &&
                Double.compare(left.getZ(), right.getZ()) == 0;
    }

    @Override
    public Coordinate readFrom(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            throw new IllegalArgumentException("ResultSet must not be null");
        }

        var x_coordinate = resultSet.getDouble(ColumnLabels.X_COORDINATE);
        var y_coordinate = resultSet.getDouble(ColumnLabels.Y_COORDINATE);
        var z_coordinate = resultSet.getDouble(ColumnLabels.Z_COORDINATE);
        return new CartesianCoordinate(x_coordinate, y_coordinate, z_coordinate);
    }

    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            throw new IllegalArgumentException("ResultSet must not be null");
        }

        var cartesian = this.asCartesianCoordinate();

        resultSet.updateDouble(ColumnLabels.X_COORDINATE, cartesian.getX());
        resultSet.updateDouble(ColumnLabels.Y_COORDINATE, cartesian.getY());
        resultSet.updateDouble(ColumnLabels.Z_COORDINATE, cartesian.getZ());
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

    @Override
    public int hashCode() {
        var cartesian = this.asCartesianCoordinate();
        return Objects.hash(cartesian.getX(), cartesian.getY(),cartesian.getZ());
    }
}
