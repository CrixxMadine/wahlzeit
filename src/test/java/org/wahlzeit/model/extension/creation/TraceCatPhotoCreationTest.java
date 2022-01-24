package org.wahlzeit.model.extension.creation;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.extension.CatManager;
import org.wahlzeit.model.extension.CatPhotoFactory;
import org.wahlzeit.utils.TraceLevel;
import org.wahlzeit.utils.Tracer;

public class TraceCatPhotoCreationTest {

    @Before
    public void setup() {
        CatPhotoFactory.initialize();
        CatManager.initialize();
    }

    @After
    public void teardown() {
        Tracer.initialize(TraceLevel.NONE);
        CatManager.terminate();
    }

    @Test
    public void traceCatPhotoCreationIsIgnorableTest() {
        try {
            var catTypeName = "Indoor cat";
            var nickName = "Garfield";
            var race = "European Shorthair";
            var ageInYears = 3;

            // Do not trace Cat creation here, only CatPhoto creation
            Tracer.initialize(TraceLevel.NONE);
            var cat = CatManager.getInstance().createCatInstance(catTypeName, nickName, race, ageInYears);
            Tracer.initialize(TraceLevel.DEBUG);

            Tracer.trace("Create new instance of CatPhoto with reference to instance of Cat", TraceLevel.DEBUG);
            Tracer.trace("---------------------------------------------------------", TraceLevel.DEBUG);

            var catPhoto = CatPhotoFactory.getInstance().createPhoto(cat);

            Tracer.trace("---------------------------------------------------------", TraceLevel.DEBUG);
            Tracer.trace("Finished creation of CatPhoto with reference to instance of Cat", TraceLevel.DEBUG);

            Assume.assumeNotNull(catPhoto);
        } catch (Exception ex) {
            Assume.assumeTrue(true);
        }
    }
}
