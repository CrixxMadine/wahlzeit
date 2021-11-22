package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractCoordinateTest {

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

    @Test
    public void testGetDistanceForCartesianCoordinates() {
        final double ACCEPTED_DELTA_DETERMINISTIC = 0;
        final double ACCEPTED_DELTA_NON_DETERMINISTIC = 0.0000000000000001;
        double x1 = 10;
        double y1 = 20;
        double z1 = -5;

        double x2 = 0;
        double y2 = 0;
        double z2 = 0;

        double x3 = Double.MAX_VALUE;
        double y3 = Double.NEGATIVE_INFINITY;
        double z3 = Double.NaN;

        var coordinate1 = new CartesianCoordinate(x1, y1, z1);
        var coordinate2 = new CartesianCoordinate(x2, y2, z2);
        var coordinate3 = new CartesianCoordinate(x3, y3, z3);

        final double EXPECTED_DISTANCE_1_2 = 22.9128784747792;


        var distance_1_2 = coordinate1.getCartesianDistance(coordinate2);
        var distance_2_1 = coordinate2.getCartesianDistance(coordinate1);
        var distance_1_3 = coordinate1.getCartesianDistance(coordinate3);


        assertEquals(EXPECTED_DISTANCE_1_2, distance_1_2, ACCEPTED_DELTA_NON_DETERMINISTIC);

        assertEquals(distance_1_2, distance_2_1, ACCEPTED_DELTA_DETERMINISTIC);
        assertEquals(distance_1_3, Double.NaN, ACCEPTED_DELTA_DETERMINISTIC);

        assertNotEquals(0, distance_1_2);
        assertNotEquals(distance_1_3, distance_1_2);
    }

    @Test
    public void testGetDistanceForSphericAndMixedCoordinates() {
        final var ACCEPTED_DELTA = AbstractCoordinate.EPSILON_DISTANCE;
        final var ZERO_DISTANCE = 0.0;

        var cartesian1 = new CartesianCoordinate(10, 20, 5);
        var cartesian2 = new CartesianCoordinate(-4, 8, 17);

        var spheric1 = cartesian1.asSphericCoordinate();
        var spheric2 = cartesian2.asSphericCoordinate();

        assertEquals(cartesian1.getCartesianDistance(cartesian2), spheric1.getCartesianDistance(spheric2), ACCEPTED_DELTA);
        assertEquals(cartesian1.getCartesianDistance(spheric2), spheric1.getCartesianDistance(cartesian2), ACCEPTED_DELTA);

        assertEquals(ZERO_DISTANCE, cartesian1.getCartesianDistance(spheric1),  ACCEPTED_DELTA);
        assertEquals(ZERO_DISTANCE, spheric2.getCartesianDistance(cartesian2), ACCEPTED_DELTA);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetDistanceWithIllegalArgumentShouldThrowException() {
        var coordinate = new CartesianCoordinate(0,0,0);
        coordinate.getCartesianDistance(null);
    }


    @Test
    public void testIsEqualForCartesianCoordinate() {
        double x1 = 10;
        double y1 = 20;
        double z1 = -5;

        double x2 = 0;
        double y2 = 0;
        double z2 = 0;

        double x3 = Double.MAX_VALUE;
        double y3 = Double.NEGATIVE_INFINITY;
        double z3 = Double.NaN;

        var coordinate1 = new CartesianCoordinate(x1, y1, z1);
        var coordinate2 = new CartesianCoordinate(x2, y2, z2);
        var coordinate3 = new CartesianCoordinate(x3, y3, z3);

        assertTrue(coordinate1.isEqual(coordinate1));

        assertTrue(coordinate1.isEqual(new CartesianCoordinate(x1, y1, z1)));
        assertTrue(coordinate2.isEqual(new CartesianCoordinate(x2, y2, z2)));
        assertTrue(coordinate3.isEqual(new CartesianCoordinate(x3, y3, z3)));

        assertFalse(coordinate1.isEqual(coordinate2));
        assertFalse(coordinate1.isEqual(coordinate3));
        assertFalse(coordinate2.isEqual(coordinate3));

        assertFalse(coordinate1.isEqual(null));
    }

    @Test
    public void testEqualsForCartesianCoordinates() {
        double x1 = 10;
        double y1 = 20;
        double z1 = -5;

        double x2 = 0;
        double y2 = 0;
        double z2 = 0;

        double x3 = Double.MAX_VALUE;
        double y3 = Double.NEGATIVE_INFINITY;
        double z3 = Double.NaN;

        var coordinate1 = new CartesianCoordinate(x1, y1, z1);
        var coordinate2 = new CartesianCoordinate(x2, y2, z2);
        var coordinate3 = new CartesianCoordinate(x3, y3, z3);

        assertEquals(coordinate1, coordinate1);
        assertSame(coordinate1, coordinate1);

        assertEquals(coordinate1, new CartesianCoordinate(x1, y1, z1));
        assertEquals(coordinate2, new CartesianCoordinate(x2, y2, z2));
        assertEquals(coordinate3, new CartesianCoordinate(x3, y3, z3));

        assertNotEquals(coordinate1, coordinate2);
        assertNotEquals(coordinate1, coordinate3);
        assertNotEquals(coordinate2, coordinate3);

        assertNotEquals(coordinate1, null);
        assertNotEquals(null, coordinate2);
        assertNotEquals("Wrong Type", coordinate1);
    }

    @Test
    public void testEqualsForSphericAndConvertedCoordinates() {
        var cartesian = new CartesianCoordinate(1,2,3);
        var spheric = cartesian.asSphericCoordinate();
        var cartesianAsConverted = spheric.asCartesianCoordinate();
        var sphericAsConverted = cartesianAsConverted.asSphericCoordinate();
        var sphericAsAbstract = (AbstractCoordinate) spheric;
        var cartesianAsAbstract = (AbstractCoordinate) cartesian;

        assertEquals(cartesian, cartesianAsConverted);
        assertEquals(cartesian, cartesianAsAbstract);
        assertEquals(spheric, sphericAsConverted);
        assertEquals(spheric, sphericAsAbstract);

        assertNotSame(cartesian, cartesianAsConverted);
        assertNotSame(spheric, sphericAsConverted);

        assertNotEquals(cartesian, spheric);
        assertNotEquals(cartesian, sphericAsAbstract);
        assertNotEquals(sphericAsAbstract, cartesian);
    }

    @Test
    public void testGetHashCodeForConversionCoordinate() {
        var spheric = new SphericCoordinate(000.1, 2, 3);
        var cartesian = spheric.asCartesianCoordinate();
        var sphericConverted = cartesian.asSphericCoordinate();
        var cartesianConverted = sphericConverted.asCartesianCoordinate();

        assertEquals(spheric.hashCode(), sphericConverted.hashCode());
        assertEquals(cartesian.hashCode(), cartesianConverted.hashCode());

        assertNotEquals(spheric.hashCode(), cartesian.hashCode());
    }
}
