package org.wahlzeit.model.extension;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * This is the type abstraction for cats
 */
public class CatType extends DataObject {

    private final String catTypeName;
    private final Set<CatType> subTypes = new HashSet<>() { };
    private CatType superType;

    /**
     * Construct a new CatType
     * @param catTypeName Unique name identifier for the catType, not null or blank
     */
    public CatType(String catTypeName) {
        this(catTypeName, null);
    }

    /**
     * Construct a new CatType with optional super type
     * @param catTypeName Unique name identifier for the catType, not null or blank
     * @param superType Optional super type for a hierarchy, can be null
     */
    public CatType(String catTypeName, CatType superType) {
        this(catTypeName, superType, null);
    }

    /**
     * Construct a new CatType with optional super type and optional subtypes
     * @param catTypeName Unique name identifier for the catType, not null or blank
     * @param superType Optional super type for a hierarchy, can be null
     * @param subTypes Optional subtypes for hierarchy, can be null or empty
     */
    public CatType(String catTypeName, CatType superType, Set<CatType> subTypes) {
        if (catTypeName == null || catTypeName.isBlank()) {
            throw new IllegalArgumentException("Did not provide valid cat type name");
        }

        this.catTypeName = catTypeName;
        this.superType = superType;

        if (this.superType != null) {
            this.superType.addSubType(this);
        }

        if (subTypes != null) {
            this.subTypes.addAll(subTypes);
        }
    }

    public String getCatTypeName() {
        return this.catTypeName;
    }

    public Optional<CatType> getSuperType() {
        return Optional.ofNullable(this.superType);
    }

    public void setSuperType(CatType superType) {
        this.superType = superType;
    }

    /**
     * Determines whether provided type is a subtype of this type
     * @param subType Desired type that shall be checked as subtype, not null
     * @return Flag whether type is actually a subtype
     */
    public boolean isSubType(CatType subType) {
        if (subType == null) {
            throw new IllegalArgumentException("Provided subtype was null!");
        }

        return isAnySubTypeInHierarchy(subType);
    }

    private boolean isAnySubTypeInHierarchy(CatType target) {
        synchronized (this.subTypes) {
            if (this.equals(target)) {
                return true;
            }

            for (var subType: this.subTypes) {
                if (subType.isAnySubTypeInHierarchy(target)) {
                    return true;
                }
            }
        }

        return false;
    }

    public Iterator<CatType> getSubTypeIterator() {
        return this.subTypes.iterator();
    }

    public void addSubType(CatType subType) {
        if (subType == null) {
            throw new IllegalArgumentException("Provided type was null");
        }

        synchronized (this.subTypes) {
            if (this.subTypes.contains(subType)) {
                return;
            }

            this.subTypes.add(subType);
            subType.setSuperType(this);
            subType.superType = this;
        }
    }

    /**
     * Creates new instance of a cat with this type
     * @param nickName Nickname for the cat, not null or blank
     * @param race Race description for the cat, not null or blank
     * @param age Age of the cat, must not be negative
     * @return No cat instance
     */
    public Cat createInstance(String nickName, String race, int age) {
        return new Cat(this, nickName, race, age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatType)) {
            return false;
        }
        CatType catType = (CatType) o;
        return getCatTypeName().equals(catType.getCatTypeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCatTypeName());
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
