package org.wahlzeit.model;

import java.util.Objects;

/**
 * Implementation of the spheric coordinate in 3 dimensions
 */
public final class SphericCoordinate extends AbstractCoordinate {
    private final double radius;
    private final double latitude;
    private final double longitude;


    /**
     * Constructor for spherical coordinates. The input ranges are limited.
     * @param radius Must not be negative (range is [0, +Infinity])
     * @param latitude Must be in range [0,180)
     * @param longitude Must be in range [0,360)
     */
    public SphericCoordinate(double radius, double latitude, double longitude) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius can not be negative");
        } else if (latitude < 0 || latitude >= 180) {
            throw new IllegalArgumentException("Theta must be defined in range [0,180)");
        } else if (longitude < 0 || longitude >= 360) {
            throw new IllegalArgumentException("Phi must be defined in range [0,360)");
        }

        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() { return this.longitude; }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {

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
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLongitude(), getLatitude(), getRadius());
    }
}
