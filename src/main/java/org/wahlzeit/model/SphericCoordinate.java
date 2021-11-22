package org.wahlzeit.model;

import java.util.Objects;

/**
 * Implementation of the spheric coordinate in 3 dimensions
 */
public final class SphericCoordinate extends AbstractCoordinate {
    private final double radius;
    private final double theta;
    private final double phi;


    /**
     * Constructor for spherical coordinates. The input ranges are limited.
     * @param radius Must not be negative (range is [0, +Infinity])
     * @param theta  Must be in range [0,180)
     * @param phi    Must be in range [0,360)
     */
    public SphericCoordinate(double radius, double theta, double phi) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius can not be negative");
        } else if (theta < 0 || theta >= 180) {
            throw new IllegalArgumentException("Theta must be defined in range [0,180)");
        } else if (phi < 0 || phi >= 360) {
            throw new IllegalArgumentException("Phi must be defined in range [0,360)");
        }

        this.radius = radius;
        this.theta = theta;
        this.phi = phi;
    }

    public double getRadius() {
        return this.radius;
    }

    public double getTheta() {
        return this.theta;
    }

    public double getPhi() { return this.phi; }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {

        var sin_theta = Math.sin(theta);
        var cos_theta = Math.cos(theta);
        var sin_phi = Math.sin(phi);
        var cos_phi = Math.cos(phi);

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
        return Objects.hash(super.hashCode(), getPhi(), getTheta(), getRadius());
    }
}
