package org.wahlzeit.model.extension;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is an extension of the Photo class representing a specialized cat photo
 */
public class CatPhoto extends Photo {

    protected Cat cat;

    /**
     *
     */
    public CatPhoto(Cat cat) {
        super();
        this.cat = cat;
    }

    /**
     *
     */
    public CatPhoto(PhotoId myId) {
        super(myId);
    }

    /**
     *
     */
    public CatPhoto(PhotoId myId, Cat cat) {
        super(myId);
        this.cat = null;
    }

    /**
     *
     */
    public CatPhoto(ResultSet resultSet) throws SQLException {
        super(resultSet);
    }

    /**
     * Return the associated cat with this Photo. May be null
     * @return Associated cat instance if present or else null
     */
    public Cat getCat() {
        return this.cat;
    }

    /**
     * Set the associated cat type on this photo
     * @param cat Associated instance of cat, may be null
     */
    public void setCat(Cat cat) {
        this.cat = cat;
    }
}
