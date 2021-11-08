package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.model.fakes.FakeResultSet;

import java.net.URL;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class PhotoTest {

    @Test
    public void testLocationPersistenceInResultSet() throws Exception {

        final ResultSet resultSet = new FakeResultSet();

        var coordinate = new Coordinate(1,2,3);
        var location = new Location(coordinate);

        var photoToBeStored = new Photo();
        photoToBeStored.ownerHomePage = new URL("https://www.this-is-ignored.de");
        photoToBeStored.location = location;

        var photoToBeRead = new Photo();
        photoToBeRead.location = null;

        assertNotNull(photoToBeStored.location);
        assertNull(photoToBeRead.location);


        photoToBeStored.writeOn(resultSet);

        photoToBeRead.readFrom(resultSet);


        assertNotNull(photoToBeRead.location);
        assertEquals(coordinate, photoToBeRead.location.getCoordinate());
        assertEquals(photoToBeStored.location.getCoordinate(), photoToBeRead.location.getCoordinate());
    }
}
