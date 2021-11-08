package org.wahlzeit.model;

import java.util.Objects;

/**
 * Simple value object to represent a cartesian coordinate in 3 dimensions
 *
 */
public final class Coordinate {
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

        return Math.sqrt(
                Math.pow((other.x - this.x),2) + Math.pow(other.y - this.y,2) + Math.pow(other.z - this.z,2)
        );
    }

    /**
     * Compares this coordinate with another instance
     * Returns true, when all cartesian fields are equal
     * @param other  Coordinate for comparison, may be null
     */
    public boolean isEqual(Coordinate other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        return Double.compare(other.x, x) == 0 &&
                Double.compare(other.y, y) == 0 &&
                Double.compare(other.z, z) == 0;
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
        return Objects.hash(x, y, z);
    }
}
