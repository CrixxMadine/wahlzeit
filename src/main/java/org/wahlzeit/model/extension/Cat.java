package org.wahlzeit.model.extension;

import org.wahlzeit.services.DataObject;

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

        if (catType == null) {
            throw new IllegalArgumentException("Did not specify type reference for cat");
        }

        if (nickName == null || nickName.isBlank()) {
            throw new IllegalArgumentException("No valid nickName provided");
        }

        if (race == null || race.isBlank()) {
            throw new IllegalArgumentException("No valid race provided");
        }

        if (age < 0) {
            throw new IllegalArgumentException("Age can not be negative");
        }

        this.catType = catType;
        this.nickName = nickName;
        this.race = race;
        this.age = age;
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
