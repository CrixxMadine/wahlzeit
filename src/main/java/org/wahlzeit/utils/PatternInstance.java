package org.wahlzeit.utils;

/**
 * Annotation to document instances of design patterns
 */
public @interface PatternInstance {
    /**
     * Name of the design pattern
     */
    String name();

    /**
     * Participants of the design pattern that are represented by this class
     */
    String[] participants();

    /**
     * (Optional): Additional comments from the author
     */
    String comment() default "";
}
