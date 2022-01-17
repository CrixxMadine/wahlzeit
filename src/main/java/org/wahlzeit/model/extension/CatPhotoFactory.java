package org.wahlzeit.model.extension;

import org.wahlzeit.model.*;
import org.wahlzeit.services.SysLog;

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
        return new CatPhoto((Cat) null);
    }

    /**
     *
     */
    @Override
    public CatPhoto createPhoto(PhotoId id) {
        // !!! Type objects are not persisted by now !!!
        return new CatPhoto(id, null);
    }

    /**
     *
     */
    @Override
    public CatPhoto createPhoto(ResultSet rs) throws SQLException {
        return new CatPhoto(rs);
    }
}
