package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class CoordinateTest {

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

        var coordinate1 = new Coordinate(x1, y1, z1);
        var coordinate2 = new Coordinate(x2, y2, z2);
        var coordinate3 = new Coordinate(x3, y3, z3);



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
    public void testEquals() {
        double x1 = 10;
        double y1 = 20;
        double z1 = -5;

        double x2 = 0;
        double y2 = 0;
        double z2 = 0;

        double x3 = Double.MAX_VALUE;
        double y3 = Double.NEGATIVE_INFINITY;
        double z3 = Double.NaN;

        var coordinate1 = new Coordinate(x1, y1, z1);
        var coordinate2 = new Coordinate(x2, y2, z2);
        var coordinate3 = new Coordinate(x3, y3, z3);

        assertEquals(coordinate1, coordinate1);
        assertSame(coordinate1, coordinate1);

        assertEquals(coordinate1, new Coordinate(x1, y1, z1));
        assertEquals(coordinate2, new Coordinate(x2, y2, z2));
        assertEquals(coordinate3, new Coordinate(x3, y3, z3));

        assertNotEquals(coordinate1, coordinate2);
        assertNotEquals(coordinate1, coordinate3);
        assertNotEquals(coordinate2, coordinate3);

        assertNotEquals(coordinate1, null);
        assertNotEquals(null, coordinate2);
        assertNotEquals("Wrong Type", coordinate1);
    }


    @Test
    public void testIsEqual() {
        double x1 = 10;
        double y1 = 20;
        double z1 = -5;

        double x2 = 0;
        double y2 = 0;
        double z2 = 0;

        double x3 = Double.MAX_VALUE;
        double y3 = Double.NEGATIVE_INFINITY;
        double z3 = Double.NaN;

        var coordinate1 = new Coordinate(x1, y1, z1);
        var coordinate2 = new Coordinate(x2, y2, z2);
        var coordinate3 = new Coordinate(x3, y3, z3);

        assertTrue(coordinate1.isEqual(coordinate1));

        assertTrue(coordinate1.isEqual(new Coordinate(x1, y1, z1)));
        assertTrue(coordinate2.isEqual(new Coordinate(x2, y2, z2)));
        assertTrue(coordinate3.isEqual(new Coordinate(x3, y3, z3)));

        assertFalse(coordinate1.isEqual(coordinate2));
        assertFalse(coordinate1.isEqual(coordinate3));
        assertFalse(coordinate2.isEqual(coordinate3));

        assertFalse(coordinate1.isEqual(null));
    }

    @Test
    public void testGetDistance() {
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

        var coordinate1 = new Coordinate(x1, y1, z1);
        var coordinate2 = new Coordinate(x2, y2, z2);
        var coordinate3 = new Coordinate(x3, y3, z3);

        final double EXPECTED_DISTANCE_1_2 = 22.9128784747792;


        var distance_1_2 = coordinate1.getDistance(coordinate2);
        var distance_2_1 = coordinate2.getDistance(coordinate1);
        var distance_1_3 = coordinate1.getDistance(coordinate3);


        assertEquals(EXPECTED_DISTANCE_1_2, distance_1_2, ACCEPTED_DELTA_NON_DETERMINISTIC);

        assertEquals(distance_1_2, distance_2_1, ACCEPTED_DELTA_DETERMINISTIC);
        assertEquals(distance_1_3, Double.NaN, ACCEPTED_DELTA_DETERMINISTIC);

        assertNotEquals(0, distance_1_2);
        assertNotEquals(distance_1_3, distance_1_2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDistanceWithIllegalArgumentShouldThrowException() {
        var coordinate = new Coordinate(0,0,0);
        coordinate.getDistance(null);
    }

}
