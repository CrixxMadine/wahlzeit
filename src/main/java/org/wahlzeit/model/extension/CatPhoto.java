package org.wahlzeit.model.extension;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is an extension of the Photo class representing a specialized cat photo
 */
public final class CatPhoto extends Photo {

    /**
     *
     */
    public CatPhoto() {
        super();
    }

    /**
     *
     * @methodtype constructor
     */
    public CatPhoto(PhotoId myId) {
        super(myId);
    }

    /**
     *
     * @methodtype constructor
     */
    public CatPhoto(ResultSet resultSet) throws SQLException {
        super(resultSet);
    }
}
