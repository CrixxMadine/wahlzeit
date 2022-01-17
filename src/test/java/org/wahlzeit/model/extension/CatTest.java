package org.wahlzeit.model.extension;

import org.junit.Assert;
import org.junit.Test;

public class CatTest {

    @Test
    public void testConstructor() {
        var type = new CatType("Meow");
        var nickName = "Master of purring";
        var race = "European Shorthair";
        var age = 10;

        var cat = new Cat(type, nickName, race, age);

        Assert.assertSame(type, cat.getCatType());
        Assert.assertSame(nickName, cat.getNickName());
        Assert.assertSame(race, cat.getRace());
        Assert.assertSame(age, cat.getAge());
    }
}
