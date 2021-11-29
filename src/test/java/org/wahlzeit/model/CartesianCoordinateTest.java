package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    public void testConversionToSpheric() {
        final double ACCEPTED_DELTA = 0.0000001;

        var cartesian = new CartesianCoordinate(1,2,3);
        var spheric = cartesian.asSphericCoordinate();

        assertEquals(spheric.getRadius(), 3.7416573867739413, ACCEPTED_DELTA);
        assertEquals(spheric.getLatitude(), 0.6405223126794245, ACCEPTED_DELTA);
        assertEquals(spheric.getLongitude(), 1.1071487177940904, ACCEPTED_DELTA);
    }
}
