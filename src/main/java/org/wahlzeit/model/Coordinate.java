package org.wahlzeit.model;

import java.util.Objects;

/**
 * Simple value object to represent a cartesian coordinate in 3 dimensions
 *
 */
public final class Coordinate {

    /**
     * If two coordinates have a finite distance smaller or equal epsilon they are considered equal
     */
    private static final double EPSILON_DISTANCE = 0.0001;

    private final double x;
    private final double y;
    private final double z;


    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    /**
     * Calculates the direct Cartesian distance between this coordinate and another
     * @param other  Coordinate for distance calculation, not null
     * @return direct Cartesian distance
     * @throws IllegalArgumentException  when provided argument was null
     */
    public double getDistance(Coordinate other) {
        if (other == null) {
            throw new IllegalArgumentException("Coordinate was null, provide valid argument");
        }

        double delta_x = other.x - this.x;
        double delta_y = other.y - this.y;
        double delta_z = other.z - this.z;

        return Math.sqrt(
                Math.pow((delta_x),2) + Math.pow(delta_y,2) + Math.pow(delta_z,2)
        );
    }

    /**
     * Compares this coordinate with another instance
     * Returns true when the finite distance between this and other coordinate is less or equal to threshold epsilon
     * For not finite distance between coordinates returns true when all cartesian fields are equal
     * @param other  Coordinate for comparison, may be null
     */
    public boolean isEqual(Coordinate other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        double distance = this.getDistance(other);

        if (Double.isFinite(distance)) {
            return Math.abs(Double.compare(distance, 0)) <= EPSILON_DISTANCE;
        }
        else {
            return Double.compare(this.x, other.x) == 0 &&
                    Double.compare(this.y, other.y) == 0 &&
                    Double.compare(this.z, other.z) == 0;
        }


    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Coordinate)) {
            return false;
        }

        return isEqual((Coordinate) other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }
}
