package com.astroidsgame.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector2DTests {

    @Test
    void testAdd() {
        var vec1 = new Vector2D(1,1);
        var vec2 = new Vector2D(1,1);

        var result = vec1.add(vec2);

        assertEquals(new Vector2D(2,2), result);
    }

}
