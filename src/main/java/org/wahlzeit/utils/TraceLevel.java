package org.wahlzeit.utils;

/**
 * Use this enum to determine the trace level
 */
public enum TraceLevel {

    /**
     * Traces nothing, not even error logs
     */
    NONE,

    /**
     * Traces exception, errors and faults
     */
    ERROR,

    /**
     * Trace everything
     */
    DEBUG
}
