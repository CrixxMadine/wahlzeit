package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.model.testhelper.FakeResultSet;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void testReadCartesianCoordinateFromResultSet() throws Exception {
        final ResultSet resultSet = new FakeResultSet();
        var typeReference = new CartesianCoordinate(0,0,0);

        var cartesianCoordinate = new CartesianCoordinate(1,2,3);
        var locationToBeStored = new Location(cartesianCoordinate);


        locationToBeStored.writeOn(resultSet);

        var locationReadFromDatabase = Location.readFrom(resultSet, typeReference);

        assertNotNull(locationReadFromDatabase);
        assertTrue(locationReadFromDatabase.getCoordinate() instanceof CartesianCoordinate);
        assertEquals(cartesianCoordinate, locationReadFromDatabase.getCoordinate());
        assertEquals(locationToBeStored.getCoordinate(), locationReadFromDatabase.getCoordinate());
    }

    @Test
    public void testReadSphericCoordinateFromResultSet() throws Exception {
        final ResultSet resultSet = new FakeResultSet();
        var typeReference = new SphericCoordinate(0,0,0);

        var sphericCoordinate = new SphericCoordinate(0,20,30);
        var locationToBeStored = new Location(sphericCoordinate);


        locationToBeStored.writeOn(resultSet);

        var locationReadFromDatabase = Location.readFrom(resultSet, typeReference);

        assertNotNull(locationReadFromDatabase);
        assertTrue(locationReadFromDatabase.getCoordinate() instanceof SphericCoordinate);
        assertEquals(sphericCoordinate, locationReadFromDatabase.getCoordinate());
        assertEquals(locationToBeStored.getCoordinate(), locationReadFromDatabase.getCoordinate());
    }
}
