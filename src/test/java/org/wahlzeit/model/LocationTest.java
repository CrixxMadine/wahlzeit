package org.wahlzeit.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LocationTest {

    @Test
    public void testConstructorWithValidArgument() {
        var coordinate1 = new CartesianCoordinate(0,0,0);
        var coordinate2 = new SphericCoordinate(10, 6, 20);

        var location1 = new Location(coordinate1);
        var location2 = new Location(coordinate2);

        assertEquals(coordinate1, location1.getCoordinate());
        assertEquals(coordinate2, location2.getCoordinate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithIllegalArgumentShouldThrowException() {
        var location = new Location(null);
    }
}
