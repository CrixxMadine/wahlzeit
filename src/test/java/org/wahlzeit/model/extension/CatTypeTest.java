package org.wahlzeit.model.extension;

import org.junit.Assert;
import org.junit.Test;

public class CatTypeTest {

    @Test
    public void testIsSubTypeDetectsIsActuallySubType() {
        var parent = new CatType("Parent");
        var child = new CatType("Child", parent);

        parent.isSubType(child);
        Assert.assertTrue(parent.isSubType(child));
    }

    @Test
    public void testIsSubTypeDetectsAdoptedSubTyeAsActuallySubType() {
        var parent = new CatType("Parent");
        var child = new CatType("Child");
        parent.addSubType(child);

        Assert.assertTrue(parent.isSubType(child));
    }

    @Test
    public void testIsSubTypeDetectsInNotSubType() {
        var parent = new CatType("Parent");
        var foreign = new CatType("NoMyChild");

        Assert.assertFalse(parent.isSubType(foreign));
    }

    @Test
    public void testIsSubTypeDetectsDeepHierarchySubType() {
        var top = new CatType("Top");
        var medium = new CatType("Middle", top);
        var bottom = new CatType("Bottom", medium);

        top.isSubType(bottom);
        Assert.assertTrue(top.isSubType(bottom));
    }


    @Test
    public void testEqualsContractIsFulfilled() {
        var typeOne = new CatType("1");
        var typeOneCopy = new CatType("1");
        var typeTwo = new CatType("2");

        Assert.assertEquals(typeOne, typeOneCopy);
        Assert.assertNotEquals(typeOne, typeTwo);

        Assert.assertSame(typeOne.hashCode(), typeOneCopy.hashCode());
        Assert.assertNotSame(typeOne.hashCode(), typeTwo.hashCode());
    }
}
