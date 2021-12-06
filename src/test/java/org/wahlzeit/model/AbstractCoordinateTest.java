package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.model.testhelper.EvilNullConversionCoordinate;

import static org.junit.Assert.*;

public class AbstractCoordinateTest {

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

    @Test()
    public void testGetHashCodeForConversionCoordinate() {
        var spheric = new SphericCoordinate(000.1, 2, 3);
        var cartesian = spheric.asCartesianCoordinate();
        var sphericConverted = cartesian.asSphericCoordinate();
        var cartesianConverted = sphericConverted.asCartesianCoordinate();

        assertEquals(spheric.hashCode(), sphericConverted.hashCode());
        assertEquals(cartesian.hashCode(), cartesianConverted.hashCode());

        assertNotEquals(spheric.hashCode(), cartesian.hashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSafelyConvertToCartesianWithNullArgumentThrowsIllegalArgumentException() {
        AbstractCoordinate.safeConvertToCartesian(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSafelyConvertToSphericWithNullArgumentThrowsIllegalArgumentException() {
        AbstractCoordinate.safeConvertToSpheric(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetCartesianDistanceOnNullCartesianConversionThrowsIllegalStateException() {
        var nullConversionCoordinate = new EvilNullConversionCoordinate();
        nullConversionCoordinate.getCartesianDistance(nullConversionCoordinate);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetCentralAngleOnNullSphericConversionThrowsIllegalStateException() {
        var nullConversionCoordinate = new EvilNullConversionCoordinate();
        nullConversionCoordinate.getCentralAngle(nullConversionCoordinate);
    }

    @Test(expected = IllegalStateException.class)
    public void testIsEqualOnNullCartesianConversionThrowsIllegalStateException() {
        var nullConversionCoordinate = new EvilNullConversionCoordinate();
        var otherNullConversionCoordinate = new EvilNullConversionCoordinate();
        nullConversionCoordinate.isEqual(otherNullConversionCoordinate);
    }

    @Test(expected = IllegalStateException.class)
    public void testEqualsOnNullCartesianConversionThrowsIllegalStateException() {
        var nullConversionCoordinate = new EvilNullConversionCoordinate();
        var otherNullConversionCoordinate = new EvilNullConversionCoordinate();
        nullConversionCoordinate.equals(otherNullConversionCoordinate);
    }

    @Test(expected = IllegalStateException.class)
    public void testHashCodeOnNullCartesianConversionThrowsIllegalStateException() {
        var nullConversionCoordinate = new EvilNullConversionCoordinate();
        nullConversionCoordinate.hashCode();
    }
}
