package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.model.testhelper.EvilNullConversionCoordinate;

import static org.junit.Assert.*;

public class SphericCoordinateTest {
    @Test
    public void testConstructorWithValidArguments() {
        final double ACCEPTED_DELTA = 0;

        var normalValue = new SphericCoordinate(1,2,3);

        assertEquals(normalValue.getRadius(), 1, ACCEPTED_DELTA);
    }

    @Test
    public void testConstructorOnValidLowerBoundary() {
        final double ACCEPTED_DELTA = 0;

        var lowerBoundary = new SphericCoordinate(0,0,0);

        assertEquals(lowerBoundary.getLatitude(), 0, ACCEPTED_DELTA);
    }

    @Test
    public void testConstructorWithValidArgumentsCloseToUpperBoundary() {
        final double ACCEPTED_DELTA = 0;

        var closeToUpperBoundary = new SphericCoordinate(Double.MAX_VALUE, 180-0.000001, 360-0.000001);

        assertEquals(closeToUpperBoundary.getLongitude(), 360-0.000001, ACCEPTED_DELTA);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeRadiusShouldThrowIllegalArgumentException() {
        new SphericCoordinate(-1, 2,3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeThetaShouldThrowIllegalArgumentException() {
        new SphericCoordinate(10, -10,3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOnUpperBoundaryLatitudeShouldThrowIllegalArgumentException() {
        new SphericCoordinate(10, 180,3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithOutOfRangeLatitudeShouldThrowIllegalArgumentException() {
        new SphericCoordinate(10, 200,3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeLongitudeShouldThrowIllegalArgumentException() {
        new SphericCoordinate(10, 40,-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOnUpperBoundaryLongitudeShouldThrowIllegalArgumentException() {
        new SphericCoordinate(10, 40,400);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithOutOfRangeLongitudeShouldThrowIllegalArgumentException() {
        new SphericCoordinate(10, 40,400);
    }

    @Test
    public void testGetCentralAngleCalculation() {
        final var ACCEPTED_DELTA = 0.0000001;
        final var EXPECTED_ANGLE = 1.7817115960047594;

        var sphericFirst = new SphericCoordinate(5, 30,10);
        var sphericSecond = new SphericCoordinate(5, 40,20);
        var secondAsCartesian = sphericSecond.asCartesianCoordinate();

        var angleBetweenTwoSpheric = sphericFirst.getCentralAngle(sphericSecond);
        var angleMixedCartesianSpheric = secondAsCartesian.getCentralAngle(sphericFirst);

        assertEquals(EXPECTED_ANGLE, angleBetweenTwoSpheric, ACCEPTED_DELTA);
        assertEquals(EXPECTED_ANGLE, angleMixedCartesianSpheric, ACCEPTED_DELTA);
    }


    @Test
    public void testGetCentralAngleCalculationInvarianceForRadius() {
        final var ACCEPTED_DELTA = 0.0;

        var referencePoint = new CartesianCoordinate(-10, 20, 5);

        var sphericZeroRadius = new SphericCoordinate(0, 10,20);
        var sphericGiantRadius = new SphericCoordinate(Double.MAX_VALUE, 10,20);

        var angleZeroRadius = sphericZeroRadius.getCentralAngle(referencePoint);
        var angleGiantRadius = referencePoint.getCentralAngle(sphericGiantRadius);

        assertEquals(angleZeroRadius, angleGiantRadius, ACCEPTED_DELTA);
    }


    @Test(expected = IllegalStateException.class)
    public void testGetCentralAngleOnNullSphericConversionThrowsIllegalStateException() {
        var sphericCoordinate = new SphericCoordinate(1,2,3);
        var nullConversionCoordinate = new EvilNullConversionCoordinate();
        sphericCoordinate.getCentralAngle(nullConversionCoordinate);
    }


}
