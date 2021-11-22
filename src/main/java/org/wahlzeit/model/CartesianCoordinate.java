package org.wahlzeit.model;

/**
 * Implementation of the cartesian coordinate in 3 dimensions
 *
 */
public final class CartesianCoordinate extends AbstractCoordinate {
    private final double x;
    private final double y;
    private final double z;


    public CartesianCoordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {

        var radius = Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));

        if (radius <= 0) {
            return new SphericCoordinate(0,0,0);
        }

        var theta = Math.acos(z / radius);
        var phi = Math.atan2(y,x);

        return new SphericCoordinate(radius,theta,phi);
    }
}
