package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SphericCoordinateTest {
    @Test
    public void testConstructorWithValidArguments() {
        final double ACCEPTED_DELTA = 0;

        var normalValue = new SphericCoordinate(1,2,3);
        var lowerBoundary = new SphericCoordinate(0,0,0);
        var upperBoundary = new SphericCoordinate(Double.MAX_VALUE, 180-0.000001, 360-0.000001);

        assertEquals(normalValue.getRadius(), 1, ACCEPTED_DELTA);
        assertEquals(lowerBoundary.getTheta(), 0, ACCEPTED_DELTA);
        assertEquals(upperBoundary.getPhi(), 360-0.000001, ACCEPTED_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeRadiusShouldThrowException() {
        new SphericCoordinate(-1, 2,3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithOutOfRangeThetaShouldThrowException() {
        new SphericCoordinate(10, 200,3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithOutOfRangePhiShouldThrowException() {
        new SphericCoordinate(10, 40,380);
    }
}
