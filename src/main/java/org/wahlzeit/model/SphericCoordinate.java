package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Implementation of the spheric coordinate in 3 dimensions
 */
public final class SphericCoordinate extends AbstractCoordinate {

    public static final int MIN_RADIUS = 0;

    public static final int MIN_LATITUDE = 0;
    public static final int UPPER_LATITUDE_LIMIT = 180;

    public static final int MIN_LONGITUDE = 0;
    public static final int UPPER_LONGITUDE_LIMIT = 360;

    private final double radius;
    private final double latitude;
    private final double longitude;


    /**
     * Constructor for spherical coordinates. The input ranges are limited.
     * @param radius Must not be negative (range is [0, +Infinity])
     * @param latitude Must be in range [0,180)
     * @param longitude Must be in range [0,360)
     * @throws IllegalArgumentException When any value is out of allowed range
     */
    public SphericCoordinate(double radius, double latitude, double longitude) throws IllegalArgumentException {
        if (radius < MIN_RADIUS) {
            throw new IllegalArgumentException("Radius can not be negative");
        } else if (latitude < MIN_LATITUDE || latitude >= UPPER_LATITUDE_LIMIT) {
            throw new IllegalArgumentException("Theta must be defined in range [" + MIN_LATITUDE + "," + UPPER_LATITUDE_LIMIT +")");
        } else if (longitude < MIN_LONGITUDE || longitude >= UPPER_LONGITUDE_LIMIT) {
            throw new IllegalArgumentException("Phi must be defined in range [" + MIN_LONGITUDE + "," + UPPER_LONGITUDE_LIMIT +")");
        }

        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Package protected accessor solely for testing purposes
     */
    double getRadius() { return radius; }

    /**
     * Package protected accessor solely for testing purposes
     */
    double getLatitude() { return latitude; }

    /**
     * Package protected accessor solely for testing purposes
     */
    double getLongitude() { return longitude; }

    public static boolean isValidSphericValueRepresentation(double radius, double latitude, double longitude) {
        return radius >= SphericCoordinate.MIN_RADIUS &&
                latitude >= SphericCoordinate.MIN_LATITUDE && latitude < SphericCoordinate.UPPER_LATITUDE_LIMIT &&
                longitude >= SphericCoordinate.MIN_LONGITUDE && longitude < SphericCoordinate.UPPER_LONGITUDE_LIMIT;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {

        var cartesianRepresentation = calculateCartesianRepresentation(this.radius, this.latitude, this.longitude);

        var halvedRadius = this.radius / 2.0;
        var mirroredLatitude = -1 * this.latitude + SphericCoordinate.UPPER_LATITUDE_LIMIT;
        var mirroredLongitude = -1 * this.longitude + SphericCoordinate.UPPER_LONGITUDE_LIMIT;
        var plausibilityCheck = calculateCartesianRepresentation(halvedRadius, mirroredLatitude, mirroredLongitude);

        var origin = CartesianCoordinate.getOrigin();

        // This check for plausibility is to detect errors like overflow or arithmetic errors in used libraries
        // The mirrored spheric coordinate with halve the radius is expected to have exactly the halved cartesian distance
        // Mostly for academic purposes included
        assert (Double.compare(
                cartesianRepresentation.getCartesianDistance(origin),
                2 * plausibilityCheck.getCartesianDistance(origin)) == 0);

        return cartesianRepresentation;
    }

    private static CartesianCoordinate calculateCartesianRepresentation(double radius, double latitude, double longitude) {
        var sin_theta = Math.sin(latitude);
        var cos_theta = Math.cos(latitude);
        var sin_phi = Math.sin(longitude);
        var cos_phi = Math.cos(longitude);

        var x = radius * sin_theta * cos_phi;
        var y = radius * sin_theta * sin_phi;
        var z = radius * cos_theta;

        return new CartesianCoordinate(x,y,z);
    }



    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }


    @Override
    public double getCentralAngle(Coordinate other) throws ArithmeticException, IllegalArgumentException {
        if (other == null) {
            throw new IllegalArgumentException("Coordinate was null, provide valid argument");
        }

        var otherSpheric = AbstractCoordinate.safeConvertToSpheric(other);

        var centralAngle = calculateGreatCentralAngle(this, otherSpheric);
        var plausibilityCheck = calculateGreatCentralAngle(otherSpheric, this);

        if (centralAngle < 0 || centralAngle > 360) {
            throw new ArithmeticException("Can not calculate central angle with reasonable value, result would be " + centralAngle);
        };

        // The plausibility check is to detect errors like overflow in used libraries
        // Mostly for academic purposes included
        if (Double.compare(centralAngle, plausibilityCheck) != 0) {
            throw new ArithmeticException("Can not calculate central angle due to arithmetic limitations");
        };

        return centralAngle;
    }

    private static double calculateGreatCentralAngle(SphericCoordinate first, SphericCoordinate second) {
        var thisPhi = first.longitude;
        var otherPhi = second.longitude;

        var deltaTheta = Math.abs(first.latitude - second.latitude);

        var leftSummand = Math.sin(thisPhi) * Math.sin(otherPhi);
        var rightSummand = Math.cos(thisPhi) * Math.cos(otherPhi) * Math.cos(deltaTheta);

        return Math.acos(leftSummand + rightSummand);
    }

    @Override
    public Coordinate readFrom(ResultSet resultSet) throws SQLException {
        var coordinate = super.readFrom(resultSet);
        return coordinate.asSphericCoordinate();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.longitude, this.latitude, this.radius);
    }
}
