package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.model.testhelper.EvilNullConversionCoordinate;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CartesianCoordinateTest {
    @Test
    public void testConstructor(){
        final double ACCEPTED_DELTA = 0;

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



        assertEquals(x1, coordinate1.getX(), ACCEPTED_DELTA);
        assertEquals(y1, coordinate1.getY(), ACCEPTED_DELTA);
        assertEquals(z1, coordinate1.getZ(), ACCEPTED_DELTA);

        assertEquals(x2, coordinate2.getX(), ACCEPTED_DELTA);
        assertEquals(y2, coordinate2.getY(), ACCEPTED_DELTA);
        assertEquals(z2, coordinate2.getZ(), ACCEPTED_DELTA);

        assertEquals(x3, coordinate3.getX(), ACCEPTED_DELTA);
        assertEquals(y3, coordinate3.getY(), ACCEPTED_DELTA);
        assertEquals(z3, coordinate3.getZ(), ACCEPTED_DELTA);
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

    @Test(expected = ArithmeticException.class)
    public void testGetCartesianDistanceToPointInInfinityThrowsArithmeticException() {
        var validPoint = new CartesianCoordinate(1,2,3);
        var pointInInfinity = new CartesianCoordinate(1,2, Double.POSITIVE_INFINITY);
        validPoint.getCartesianDistance(pointInInfinity);
    }

    @Test(expected = ArithmeticException.class)
    public void testGetCartesianDistanceLargerThanPossibleDoubleValueThrowsArithmeticException() {
        var validPoint = new CartesianCoordinate(1,2,3);
        var pointAtTheLimitOfDouble = new CartesianCoordinate(Double.MAX_VALUE,Double.MAX_VALUE, Double.MAX_VALUE);
        validPoint.getCartesianDistance(pointAtTheLimitOfDouble);
    }

    @Test
    public void testConversionToSpheric() {
        final double ACCEPTED_DELTA = 0.0000001;

        var cartesian = new CartesianCoordinate(1,2,3);
        var spheric = cartesian.asSphericCoordinate();

        assertEquals(spheric.getRadius(), 3.7416573867739413, ACCEPTED_DELTA);
        assertEquals(spheric.getLatitude(), 0.6405223126794245, ACCEPTED_DELTA);
        assertEquals(spheric.getLongitude(), 1.1071487177940904, ACCEPTED_DELTA);
    }

    @Test
    public void testConversionOfCartesianOriginToSphericIsValidAndDoesNotDivideByZero() {
        var cartesianOrigin = new CartesianCoordinate(0,0,0);
        var sphericOrigin = new SphericCoordinate(0,0,0);

        var cartesianAsSpheric = cartesianOrigin.asSphericCoordinate();

        assertEquals(sphericOrigin, cartesianAsSpheric);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArgumentForReadFromThrowsIllegalArgumentException() throws SQLException {
        CartesianCoordinate.getOrigin().readFrom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArgumentForWriteOnThrowsIllegalArgumentException() throws SQLException {
        CartesianCoordinate.getOrigin().writeOn(null);
    }


    @Test(expected = IllegalStateException.class)
    public void testGetCartesianDistanceOnNullSphericConversionThrowsIllegalStateException() {
        var cartesianCoordinate = new CartesianCoordinate(1,2,3);
        var nullConversionCoordinate = new EvilNullConversionCoordinate();
        cartesianCoordinate.getCartesianDistance(nullConversionCoordinate);
    }

    @Test(expected = ArithmeticException.class)
    public void testConversionOfCartesianCoordinateInInfinityToSphericCoordinateThrowsArithmeticException() {
        var pointInInfinity = new CartesianCoordinate(0, 1, Double.POSITIVE_INFINITY);
        pointInInfinity.asSphericCoordinate();
    }

    @Test(expected = ArithmeticException.class)
    public void testConversionOfCartesianCoordinateWithNanValueToSphericCoordinateThrowsArithmeticException() {
        var pointInInfinity = new CartesianCoordinate(Double.NaN, 1, 2);
        pointInInfinity.asSphericCoordinate();
    }
}
