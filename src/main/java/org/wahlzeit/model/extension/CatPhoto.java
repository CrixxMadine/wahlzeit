package org.wahlzeit.model.extension;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.utils.TraceLevel;
import org.wahlzeit.utils.Tracer;

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

        Tracer.trace("CatPhoto: Constructor was called with provided Cat argument", TraceLevel.DEBUG);
        Tracer.trace("CatPhoto: Super() constructor was called without arguments", TraceLevel.DEBUG);

        this.cat = cat;

        Tracer.trace("CatPhoto: Setting provided Cat argument as mutable reference to protected field 'cat'", TraceLevel.DEBUG);
        Tracer.trace("CatPhoto: Successfully created instance", TraceLevel.DEBUG);
    }

    /**
     *
     */
    public CatPhoto(PhotoId myId) {
        super(myId);

        Tracer.trace("CatPhoto: Constructor was called with provided PhotoId argument", TraceLevel.DEBUG);
        Tracer.trace("CatPhoto: Super(PhotoId) constructor was called with PhotoId argument", TraceLevel.DEBUG);
        Tracer.trace("CatPhoto: Successfully created instance", TraceLevel.DEBUG);
    }

    /**
     *
     */
    public CatPhoto(PhotoId myId, Cat cat) {
        super(myId);

        Tracer.trace("CatPhoto: Constructor was called with provided arguments PhotoId and Cat", TraceLevel.DEBUG);
        Tracer.trace("CatPhoto: Super(PhotoId) constructor was called with PhotoId argument", TraceLevel.DEBUG);

        this.cat = cat;

        Tracer.trace("CatPhoto: Setting provided Cat argument as mutable reference to protected field 'cat'", TraceLevel.DEBUG);
        Tracer.trace("CatPhoto: Successfully created instance", TraceLevel.DEBUG);
    }

    /**
     *
     */
    public CatPhoto(ResultSet resultSet) throws SQLException {
        super(resultSet);

        Tracer.trace("CatPhoto: Constructor was called with provided ResultSet argument", TraceLevel.DEBUG);
        Tracer.trace("CatPhoto: Super(ResultSet) constructor was called with ResultSet argument", TraceLevel.DEBUG);
        Tracer.trace("CatPhoto: Successfully created instance", TraceLevel.DEBUG);
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
