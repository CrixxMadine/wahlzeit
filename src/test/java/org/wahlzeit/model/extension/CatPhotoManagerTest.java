package org.wahlzeit.model.extension;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.PhotoManager;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class CatPhotoManagerTest {

    @Before
    @After
    public void ensureSingletonDestruction() throws Exception {
        var instanceField = PhotoManager.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        instanceField.setAccessible(false);
    }

    @Test
    public void testCatPhotoManagerCorrectSingletonInitialization() {
        CatPhotoManager.initialize();

        var explicitInstance = CatPhotoManager.getInstance();
        var implicitInstance = PhotoManager.getInstance();

        assertTrue(implicitInstance instanceof CatPhotoManager);
        assertSame(explicitInstance, implicitInstance);
    }

    @Test
    public void testMultipleInitializationIsSafeWhenFirstInitializationIsCatPhotoManager() {
        CatPhotoManager.initialize();
        var firstManagerInstance = CatPhotoManager.getInstance();

        CatPhotoManager.initialize();
        PhotoManager.initialize();
        var secondManagerInstance = CatPhotoManager.getInstance();

        assertSame(firstManagerInstance, secondManagerInstance);
    }

    @Test(expected = IllegalStateException.class)
    public void testTryingToChangeInitializationToCatPhotoManagerThrowsIllegalStateException() {
        PhotoManager.initialize();
        CatPhotoManager.initialize();
    }
}