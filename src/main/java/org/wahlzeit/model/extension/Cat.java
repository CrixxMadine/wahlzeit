package org.wahlzeit.model.extension;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.utils.TraceLevel;
import org.wahlzeit.utils.Tracer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents an instance of the cat type
 */
public class Cat extends DataObject {
    private final CatType catType;
    private final String nickName;
    private final String race;
    private final int age;

    /**
     * Create a new instance of a cat type
     * @param catType Type of the cat, not null
     * @param nickName Nickname for the cat, not null or blank
     * @param race Race description for the cat, not null or blank
     * @param age Age of the cat, must not be negative
     */
    public Cat(CatType catType, String nickName, String race, int age) {

        Tracer.trace("Cat: Constructor was called with parameters", TraceLevel.DEBUG);

        if (catType == null) {
            Tracer.trace("Cat: Illegal CatType provided, abort construction", TraceLevel.DEBUG);
            throw new IllegalArgumentException("Did not specify type reference for cat");
        } else {
            Tracer.trace("Cat: Setting argument CatType - value is " + catType.getCatTypeName(), TraceLevel.DEBUG);
            this.catType = catType;
        }

        if (nickName == null || nickName.isBlank()) {
            Tracer.trace("Cat: Illegal nickname provided, abort construction", TraceLevel.DEBUG);
            throw new IllegalArgumentException("No valid nickName provided");
        } else {
            Tracer.trace("Cat: Setting argument Nickname - value is " + nickName, TraceLevel.DEBUG);
            this.nickName = nickName;
        }

        if (race == null || race.isBlank()) {
            Tracer.trace("Cat: Illegal race provided, abort construction", TraceLevel.DEBUG);
            throw new IllegalArgumentException("No valid race provided");
        } else {
            Tracer.trace("Cat: Setting argument Race - value is " + race, TraceLevel.DEBUG);
            this.race = race;
        }

        if (age < 0) {
            Tracer.trace("Cat: Illegal age provided, abort construction", TraceLevel.DEBUG);
            throw new IllegalArgumentException("Age can not be negative");
        } else {
            Tracer.trace("Cat: Setting argument Age - value is " + age, TraceLevel.DEBUG);
            this.age = age;
        }

        Tracer.trace("Cat: Successfully created immutable instance", TraceLevel.DEBUG);
    }

    public CatType getCatType() {
        return this.catType;
    }

    public String getNickName() {
        return nickName;
    }

    public String getRace() {
        return race;
    }

    public int getAge() {
        return age;
    }

    /*
     * Persistence is not implemented
     */
    @Override
    public String getIdAsString() {
        throw new UnsupportedOperationException("Persistence is not supported");
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        throw new UnsupportedOperationException("Persistence is not supported");
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        throw new UnsupportedOperationException("Persistence is not supported");
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) {
        throw new UnsupportedOperationException("Persistence is not supported");
    }
}
