package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Implementation of the cartesian coordinate in 3 dimensions
 *
 */
public final class CartesianCoordinate extends AbstractCoordinate {

    private static class ColumnLabels {
        public static final String X_COORDINATE = "x_coordinate";
        public static final String Y_COORDINATE = "y_coordinate";
        public static final String Z_COORDINATE = "z_coordinate";
    }

    private final double x;
    private final double y;
    private final double z;


    public CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Package protected accessor solely for testing purposes
     */
    double getX() { return x; }

    /**
     * Package protected accessor solely for testing purposes
     */
    double getY() { return y; }

    /**
     * Package protected accessor solely for testing purposes
     */
    double getZ() { return z; }

    public static CartesianCoordinate getOrigin() {
        return new CartesianCoordinate(0,0,0);
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }


    @Override
    public SphericCoordinate asSphericCoordinate() {

        var radius = Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));

        if (radius == 0) {
            return new SphericCoordinate(0,0,0);
        }

        var theta = Math.acos(z / radius);
        var phi = Math.atan2(y,x);

        if (! SphericCoordinate.isValidSphericValueRepresentation(radius, theta, phi)) {
            throw new ArithmeticException("The coordinate can not get converted to spheric representation." +
                    "Invalid result would be: \n" +
                    "radius: " + radius + ", latitude: " + theta + ", longitude: " + phi);
        }

        var sphericRepresentation = new SphericCoordinate(radius,theta,phi);

        // This check for plausibility is to detect errors like overflow in used libraries
        // Mostly for academic purposes included
        assert this.isEqual(sphericRepresentation);

        return sphericRepresentation;
    }


    @Override
    public double getCartesianDistance(Coordinate other) {
        if (other == null) {
            throw new IllegalArgumentException("Coordinate was null, provide valid argument");
        }

        var otherCartesian = AbstractCoordinate.safeConvertToCartesian(other);

        var distance = calculateCartesianDistance(this, otherCartesian);
        var plausibilityCheck = calculateCartesianDistance(otherCartesian, this);

        if (distance < 0 || Double.isInfinite(distance)) {
            throw new ArithmeticException("Can not calculate distance with reasonable value, result would be " + distance);
        }

        // The plausibility check is to detect errors like overflow in used libraries
        // Mostly for academic purposes included
        assert Double.compare(distance, plausibilityCheck) == 0;

        return distance;
    }

    private static double calculateCartesianDistance(CartesianCoordinate first, CartesianCoordinate second) {
        double delta_x = second.x - first.x;
        double delta_y = second.y - first.y;
        double delta_z = second.z - first.z;

        return Math.sqrt(
                Math.pow((delta_x),2) + Math.pow(delta_y,2) + Math.pow(delta_z,2)
        );
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

        resultSet.updateDouble(ColumnLabels.X_COORDINATE, cartesian.x);
        resultSet.updateDouble(ColumnLabels.Y_COORDINATE, cartesian.y);
        resultSet.updateDouble(ColumnLabels.Z_COORDINATE, cartesian.z);
    }

    @Override
    public int hashCode() {
        var cartesian = this.asCartesianCoordinate();
        return Objects.hash(cartesian.x, cartesian.y,cartesian.z);
    }

    /**
     * Utility method explicitly cartesian value of this coordinate with another
     * @param other Coordinate for comparison (can be null)
     * @return Result floating number comparison
     */
    public boolean compareCartesianValuesExplicitly(Coordinate other) {

        if (other == null) {
            return false;
        }

        var otherCartesian = other.asCartesianCoordinate();

        return Double.compare(this.x, otherCartesian.x) == 0 &&
                Double.compare(this.y, otherCartesian.y) == 0 &&
                Double.compare(this.z, otherCartesian.z) == 0;
    }
}
