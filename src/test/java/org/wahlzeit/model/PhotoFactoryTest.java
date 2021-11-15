package org.wahlzeit.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.model.extension.CatPhoto;
import org.wahlzeit.model.extension.CatPhotoFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

public class PhotoFactoryTest {

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
    public void testSingletonCanBeInstantiatedByPublicSetter() {
        var initialPhotoFactory = new PhotoFactory();

        PhotoFactory.setInstance(initialPhotoFactory);
        var referencedPhotoFactory = PhotoFactory.getInstance();

        assertSame(initialPhotoFactory, referencedPhotoFactory);
    }

    @Test(expected = IllegalStateException.class)
    public void testSetInstanceOfSecondPhotoFactoryThrowsIllegalStateException() {
        PhotoFactory.initialize();
        PhotoFactory.setInstance(new PhotoFactory());
    }

    @Test(expected = IllegalStateException.class)
    public void testSetAdditionalNormalPhotoFactoryInstanceToCatExtensionThrowsIllegalStateException() {
        CatPhotoFactory.initialize();
        PhotoFactory.setInstance(new PhotoFactory());
    }

    @Test
    public void testNotUsingTheExtensionEnsuresGettingNormalPhotos() {
        PhotoFactory.initialize();
        var normalPhoto = PhotoFactory.getInstance().createPhoto();
        assertFalse(normalPhoto instanceof CatPhoto);
    }
}
