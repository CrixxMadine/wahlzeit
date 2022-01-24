package org.wahlzeit.model.extension;

import org.wahlzeit.model.*;
import org.wahlzeit.services.SysLog;
import org.wahlzeit.utils.TraceLevel;
import org.wahlzeit.utils.Tracer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is an alternative implementation to the PhotoFactory for creating CatPhotos
 * Since PhotoFactory is a singleton, there can only ever be one PhotoFactory at all
 */
public final class CatPhotoFactory extends PhotoFactory {

    private static boolean isInitialized = false;

    /**
     * Public singleton access method.
     */
    public static synchronized CatPhotoFactory getInstance() {
        if (!isInitialized) {
            SysLog.logSysInfo("setting specialized CatPhotoFactory");
            PhotoFactory.setInstance(new CatPhotoFactory());
            isInitialized = true;
        }

        return (CatPhotoFactory) PhotoFactory.getInstance();
    }

    /**
     * Hidden singleton instance; needs to be initialized from the outside.
     */
    public static void initialize() {
        getInstance(); // drops result due to getInstance() side-effects
    }

    /**
     *
     */
    private CatPhotoFactory() {
        // do nothing
    }

    /**
     * @methodtype factory
     */
    @Override
    public CatPhoto createPhoto() {
        // !!! Type objects are not persisted by now !!!
        Tracer.trace("CatPhotoFactory: Method createPhoto() was called without arguments", TraceLevel.DEBUG);
        Tracer.trace("CatPhotoFactory: Create new CatPhoto (call Constructor) with null reference of Cat argument", TraceLevel.DEBUG);
        var catPhoto = new CatPhoto((Cat) null);

        Tracer.trace("CatPhotoFactory: Return created instance of CatPhoto", TraceLevel.DEBUG);
        return catPhoto;
    }

    public CatPhoto createPhoto(Cat cat) {
        // !!! Type objects are not persisted by now !!!
        Tracer.trace("CatPhotoFactory: Method createPhoto() was called with provided Cat argument", TraceLevel.DEBUG);
        Tracer.trace("CatPhotoFactory: Create new CatPhoto (call Constructor) with unchecked Cat argument", TraceLevel.DEBUG);
        var catPhoto = new CatPhoto(cat);

        Tracer.trace("CatPhotoFactory: Return created instance of CatPhoto", TraceLevel.DEBUG);
        return catPhoto;
    }

    /**
     *
     */
    @Override
    public CatPhoto createPhoto(PhotoId id) {
        Tracer.trace("CatPhotoFactory: Method createPhoto() was called with provided PhotoId argument", TraceLevel.DEBUG);
        Tracer.trace("CatPhotoFactory: Create new CatPhoto (call Constructor) wit unchecked PhotoId argument and null reference of Cat argument", TraceLevel.DEBUG);
        var catPhoto = new CatPhoto(id, null);

        Tracer.trace("CatPhotoFactory: Return created instance of CatPhoto", TraceLevel.DEBUG);
        return catPhoto;
    }

    /**
     *
     */
    @Override
    public CatPhoto createPhoto(ResultSet rs) throws SQLException {
        Tracer.trace("CatPhotoFactory: Method createPhoto() of CatPhotoFactory was called with provided ResultSet argument", TraceLevel.DEBUG);
        Tracer.trace("CatPhotoFactory: Create new CatPhoto (call Constructor) wit unchecked ResultSet argument", TraceLevel.DEBUG);
        var catPhoto = new CatPhoto(rs);

        Tracer.trace("CatPhotoFactory: Return created instance of CatPhoto", TraceLevel.DEBUG);
        return catPhoto;
    }
}
