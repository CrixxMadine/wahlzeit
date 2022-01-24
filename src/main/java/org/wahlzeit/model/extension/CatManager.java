package org.wahlzeit.model.extension;

import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;
import org.wahlzeit.utils.TraceLevel;
import org.wahlzeit.utils.Tracer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CatManager extends ObjectManager {

    private static final Object INITIALIZATION_LOCK = new Object();

    private final Collection<Cat> catInstances = new ArrayList<>();

    private final Map<String, CatType> catTypes = new HashMap<>();

    /**
     * Singleton instance of the CatManager
     */
    protected static CatManager instance;

    /**
     * Protected constructor for use in this and subclasses only
     */
    protected CatManager() { }

    /**
     * Initialize the CatManager if not already initialized
     */
    public static synchronized void initialize() {
        synchronized (INITIALIZATION_LOCK) {
            if (instance == null) {
                initialize(new CatManager());
            }
        }
    }

    /**
     * Terminate initialized CatManager
     */
    public static synchronized void terminate() {
        synchronized (INITIALIZATION_LOCK) {
            instance = null;
        }
    }

    /**
     * Protected initializer using any provided instance of CatManager
     */
    protected static synchronized void initialize(CatManager manger) {
        synchronized (INITIALIZATION_LOCK) {
            if (manger == null) {
                throw new IllegalArgumentException("Did not provide an instance of CatManager");
            }

            if (instance == null) {
                instance = manger;
            }
        }
    }

    /**
     * Return the instance of the CatManager, initializes if necessary
     */
    public static CatManager getInstance() {
        if (instance == null) {
            initialize();
        }
        return instance;
    }


    /**
     * Return an Optional containing the associated CatType when applicable
     * @param catTypeName Desired CatType identifier
     * @return Optional containing the target type when present
     */
    public Optional<CatType> getCatType(String catTypeName) {
        if (catTypeName == null || catTypeName.isBlank()) {
            throw new IllegalArgumentException("Provided cat type must not be null or empty");
        }

        synchronized (this.catTypes) {
            return Optional.ofNullable(this.catTypes.get(catTypeName));
        }
    }

    /**
     * Adds a new CatType created with the desired identifier
     * @param catTypeName Identifier of the desired cat type, not null or empty
     * @throws IllegalStateException When cat type already exists
     */
    public void addNewCatType(String catTypeName) {
        if (catTypeName == null || catTypeName.isBlank()) {
            throw new IllegalArgumentException("CatType identifier must ot be null or empty");
        }

        synchronized (this.catTypes) {
            if (this.catTypes.containsKey(catTypeName)) {
                throw new IllegalStateException("Cat type is already defined in hierarchy!");
            }

            addCatTypeWithNullableParent(catTypeName, null);
        }
    }

    /**
     * Adds a new CatType created with the desired identifier and associates it with the desired parent
     * @param catTypeName Identifier of the desired cat type, not null or empty
     * @param parentTypeName Identifier of the desired parent, not null or empty
     * @throws IllegalStateException When cat type already exists or parent does not exist
     */
    public void addNewCatType(String catTypeName, String parentTypeName) {
        if (catTypeName == null || catTypeName.isBlank()) {
            throw new IllegalArgumentException("CatType identifier must ot be null or empty");
        }

        if (parentTypeName == null || parentTypeName.isBlank()) {
            throw new IllegalArgumentException("ParentType identifier must ot be null or empty");
        }

        synchronized (this.catTypes) {
            if (this.catTypes.containsKey(catTypeName)) {
                throw new IllegalStateException("Desired CatType already exists as known CatType");
            }

            var parent = this.catTypes.get(parentTypeName);

            if (parent == null) {
                throw new IllegalStateException("Desired parent type is not defined!");
            }

            addCatTypeWithNullableParent(catTypeName, parent);
        }
    }

    private void addCatTypeWithNullableParent(String catTypeName, CatType parent) {
        synchronized (this.catTypes) {
            var newCatType = new CatType(catTypeName, parent);
            this.catTypes.put(catTypeName, newCatType);
            if (parent != null) {
                parent.addSubType(newCatType);
            }
        }
    }

    /***
     * Creates a new cat instance with cat specific parameters
     * If desired CatType is unknown, a new cat type gets created
     * @param catTypeName Desired CatType, not null or empty
     */
    public Cat createCatInstance(
            String catTypeName,
            String nickName,
            String race,
            int age) {

        Tracer.trace("CatManager: Method createCatInstance(....) was called with arguments", TraceLevel.DEBUG);

        if (catTypeName == null || catTypeName.isBlank()) {
            Tracer.trace("CatManager: Provided argument CatTypeName was invalid, abort method with exception", TraceLevel.DEBUG);
            throw new IllegalArgumentException("Provided cat type must not be null or empty");
        }

        Tracer.trace("CatManager: Enter synchronized context of private Map 'catTypes' which stores known type", TraceLevel.DEBUG);
        synchronized (this.catTypes) {
            if (! this.catTypes.containsKey(catTypeName)) {
                Tracer.trace("CatManager: Provided CatTypeName '" + catTypeName + "' is not yet known as CatType", TraceLevel.DEBUG);
                Tracer.trace("CatManager: Create new instance of CatType (call constructor) with provided CatTypeName", TraceLevel.DEBUG);
                var catType = new CatType(catTypeName);

                Tracer.trace("CatManager: Add created CatType to private Map 'catTypes", TraceLevel.DEBUG);
                this.catTypes.put(catTypeName, catType);
            }

            Tracer.trace("CatManager: Retrieve reference to known CatType from private Map 'catTypes", TraceLevel.DEBUG);
            var targetType = this.catTypes.get(catTypeName);

            Tracer.trace("CatManager: Call method 'createInstance(...)' on retrieved CatType reference with provided method arguments (except arg CatTypeName)'", TraceLevel.DEBUG);
            var catInstance = targetType.createInstance(nickName, race, age);

            Tracer.trace("CatManager: Add created Cat instance to private Set 'catInstances'", TraceLevel.DEBUG);
            this.catInstances.add(catInstance);

            Tracer.trace("CatManager: Return created instance of Cat as return value of 'createCatInstance(...)' and leave synchronized context", TraceLevel.DEBUG);
            return catInstance;
        }
    }


    @Override
    protected Persistent createObject(ResultSet rset) {
        throw new UnsupportedOperationException("Persistence is not supported");
    }
}
