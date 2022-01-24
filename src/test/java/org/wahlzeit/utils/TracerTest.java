package org.wahlzeit.utils;

import org.junit.Test;

public class TracerTest {
    @Test
    public void testTracerNoneInitialization() {
        Tracer.initialize(TraceLevel.NONE);
    }
}
