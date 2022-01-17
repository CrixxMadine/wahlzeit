package org.wahlzeit.model.extension;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CatManagerTest {

    @Before
    @After
    public void ensureSingletonDestruction() throws Exception {
        var instanceField = CatManager.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        instanceField.setAccessible(false);
    }

    @Test
    public void testCatManagerCorrectSingletonInitialization() {
        CatManager.initialize();

        var catManagerInstance = CatManager.getInstance();

        Assert.assertNotNull(catManagerInstance);
    }

    @Test
    public void testCatManagerGetInitializedOnGetInstanceCall() {
        var catManagerInstance = CatManager.getInstance();

        Assert.assertNotNull(catManagerInstance);
    }

    @Test
    public void testMultipleInitializationDoesNotDuplicateManager() {
        CatManager.initialize();
        var firstManagerInstance = CatManager.getInstance();

        CatManager.initialize();
        var secondManagerInstance = CatManager.getInstance();

        assertSame(firstManagerInstance, secondManagerInstance);
    }

    @Test
    public void testAddCatType() {
        var manager = CatManager.getInstance();

        final var firstCatTypeName = "Meow";
        final var secondCatTypeName = "Mauzi";
        final var undefinedType = "Undefined";


        manager.addNewCatType(firstCatTypeName);
        manager.addNewCatType(secondCatTypeName);

        var firstCatType = manager.getCatType(firstCatTypeName);
        var secondCatType = manager.getCatType(secondCatTypeName);
        var undefinedCatType = manager.getCatType(undefinedType);


        Assert.assertTrue(firstCatType.isPresent());
        Assert.assertTrue(secondCatType.isPresent());
        Assert.assertFalse(undefinedCatType.isPresent());

        assertEquals(firstCatTypeName, firstCatType.get().getCatTypeName());
        assertEquals(secondCatTypeName, secondCatType.get().getCatTypeName());
    }


    @Test
    public void testAddCatTypeWithParentCreatesAlsoRelations() {
        var manager = CatManager.getInstance();

        final var parentTypeName = "Meow";
        final var childTypeName = "MeowJunior";


        manager.addNewCatType(parentTypeName);
        manager.addNewCatType(childTypeName, parentTypeName);

        var parentCatType = manager.getCatType(parentTypeName);
        var childCatType = manager.getCatType(childTypeName);

        Assert.assertTrue(parentCatType.isPresent());
        Assert.assertTrue(childCatType.isPresent());


        assertEquals(parentTypeName, parentCatType.get().getCatTypeName());
        assertEquals(childTypeName, childCatType.get().getCatTypeName());

        assertTrue(parentCatType.get().isSubType(childCatType.get()));
    }
}
