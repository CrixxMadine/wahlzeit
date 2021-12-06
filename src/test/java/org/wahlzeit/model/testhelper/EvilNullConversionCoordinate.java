package org.wahlzeit.model.testhelper;

import org.wahlzeit.model.AbstractCoordinate;
import org.wahlzeit.model.CartesianCoordinate;
import org.wahlzeit.model.SphericCoordinate;

public class EvilNullConversionCoordinate extends AbstractCoordinate {
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return null;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return null;
    }
}
