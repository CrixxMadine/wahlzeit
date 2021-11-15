package org.wahlzeit.model.extension;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.PhotoFactory;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.model.fakes.FakeResultSet;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class CatPhotoFactoryTest {

    @Before
    @After
    public void ensureSingletonDestruction() throws Exception {
        setFinalStaticField(CatPhotoFactory.class.getDeclaredField("isInitialized"), false);
        setFinalStaticField(PhotoFactory.class.getDeclaredField("instance"), null);
    }

    private static void setFinalStaticField(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        if (Modifier.isFinal(field.getModifiers())) {
            var modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            modifiersField.setAccessible(false);
        }

        field.set(null, newValue);
        field.setAccessible(false);
    }

    @Test
    public void testCatPhotoFactoryCorrectSingletonInitialization() {
        CatPhotoFactory.initialize();

        var explicitInstance = CatPhotoFactory.getInstance();
        var implicitInstance = PhotoFactory.getInstance();

        assertTrue(implicitInstance instanceof CatPhotoFactory);
        assertSame(explicitInstance, implicitInstance);
    }

    @Test
    public void testMultipleInitializationIsSafeWhenFirstInitializationIsCatPhotoFactory() {
        CatPhotoFactory.initialize();
        var firstFactoryInstance = CatPhotoFactory.getInstance();

        CatPhotoFactory.initialize();
        PhotoFactory.initialize();
        var secondFactoryInstance = CatPhotoFactory.getInstance();

        assertSame(firstFactoryInstance, secondFactoryInstance);
    }

    @Test(expected = IllegalStateException.class)
    public void testTryingToChangeInitializationToCatPhotoFactoryThrowsIllegalStateException() {
        PhotoFactory.initialize();
        CatPhotoFactory.initialize();
    }

    @Test
    public void testCreatedPhotosAreCatPhotos() throws SQLException {
        CatPhotoFactory.initialize();

        var explicitPhoto = CatPhotoFactory.getInstance().createPhoto();
        var implicitPhoto = PhotoFactory.getInstance().createPhoto();

        var photoFromId = PhotoFactory.getInstance().createPhoto(PhotoId.getNextId());
        var photoFromResultSet = PhotoFactory.getInstance().createPhoto(new FakeResultSet());

        assertTrue(implicitPhoto instanceof CatPhoto);
        assertEquals(explicitPhoto.getClass(), (implicitPhoto.getClass()));

        assertTrue(photoFromId instanceof CatPhoto);
        assertTrue(photoFromResultSet instanceof CatPhoto);
    }

}
