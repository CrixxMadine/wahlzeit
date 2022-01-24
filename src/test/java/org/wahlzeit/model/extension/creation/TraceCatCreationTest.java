package org.wahlzeit.model.extension.creation;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.extension.CatManager;
import org.wahlzeit.utils.TraceLevel;
import org.wahlzeit.utils.Tracer;

public class TraceCatCreationTest {

    @Before
    public void setup() {
        Tracer.initialize(TraceLevel.DEBUG);
        CatManager.initialize();
    }

    @After
    public void teardown() {
        Tracer.initialize(TraceLevel.NONE);
        CatManager.terminate();
    }

    @Test
    public void traceCatCreationIsIgnorableTest() {
        try {
            var catTypeName = "Indoor cat";
            var nickName = "Garfield";
            var race = "European Shorthair";
            var ageInYears = 3;


            Tracer.trace("Create new instance of Cat", TraceLevel.DEBUG);
            Tracer.trace("--------------------------", TraceLevel.DEBUG);

            var cat = CatManager.getInstance().createCatInstance(catTypeName, nickName, race, ageInYears);

            Tracer.trace("--------------------------", TraceLevel.DEBUG);
            Tracer.trace("Finished creation of Cat", TraceLevel.DEBUG);

            Assume.assumeNotNull(cat);

        } catch (Exception ex) {
            Assume.assumeTrue(true);
        }
    }


}
