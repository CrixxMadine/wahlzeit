package org.wahlzeit.model.extension;

import org.wahlzeit.model.*;
import java.io.File;

public class CatPhotoManager extends PhotoManager {

    /**
     * Initialize the CatPhotoManager as used PhotoManager
     */
    public static synchronized void initialize() {
        if (instance == null) {
            initialize(new CatPhotoManager());
        } else if (!(instance instanceof CatPhotoManager)) {
            throw new IllegalStateException("Another PhotoManager was already configured!");
        }
    }

    /**
     *
     */
    private CatPhotoManager() {
        super();
    }

    /**
     *
     */
    public static CatPhotoManager getInstance() {
        if (instance == null) {
            initialize();
        }
        return (CatPhotoManager) instance;
    }

    /**
     *
     */
    @Override
    public CatPhoto getPhotoFromId(PhotoId id) {
        var photo = super.getPhotoFromId(id);

        if (photo != null) {
            return (CatPhoto) photo;
        }

        return null;
    }


    /**
     *
     */
    @Override
    public CatPhoto getVisiblePhoto(PhotoFilter filter) {
        var photo = super.getVisiblePhoto(filter);

        if (photo != null) {
            return (CatPhoto) photo;
        }
        return null;
    }



    /**
     *
     */
    @Override
    public CatPhoto createPhoto(File file) throws Exception {
        var photo = super.createPhoto(file);
        return (CatPhoto) photo;
    }


}
