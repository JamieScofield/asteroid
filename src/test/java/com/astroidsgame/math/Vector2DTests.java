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

    @Test
    void testSubtract() {
        var vec1 = new Vector2D(5,5);
        var vec2 = new Vector2D(3,2);

        var result = vec1.subtract(vec2);

        assertEquals(new Vector2D(2, 3), result);
    }

    @Test
    void testLength() {
        var vec = new Vector2D(3, 4);

        assertEquals(5.0, vec.length(), 1e-9);
    }

    @Test
    void testLengthOfZeroVector() {
        assertEquals(0.0, Vector2D.ZERO.length(), 1e-9);
    }

    @Test
    void testScale() {
        var vec = new Vector2D(2, 3);

        var result = vec.scale(2);

        assertEquals(new Vector2D(4, 6), result);
    }

    @Test
    void testScaleByZero() {
        var vec = new Vector2D(2, 3);

        var result = vec.scale(0);

        assertEquals(Vector2D.ZERO, result);
    }

    @Test
    void testNormalize() {
        var vec = new Vector2D(3, 4);

        var result = vec.normalize();

        assertEquals(0.6, result.x(), 1e-9);
        assertEquals(0.8, result.y(), 1e-9);
    }

    @Test
    void testNormalizeReturnsUnitLength() {
        var vec = new Vector2D(7, -2);

        var result = vec.normalize();

        assertEquals(1.0, result.length(), 1e-9);
    }

    @Test
    void testNormalizeOfZeroVectorReturnsZero() {
        var result = Vector2D.ZERO.normalize();

        assertEquals(Vector2D.ZERO, result);
    }

    @Test
    void testLerpAtStartReturnsOrigin() {
        var start = new Vector2D(1, 1);
        var end = new Vector2D(9, 9);

        var result = start.lerp(end, 0);

        assertEquals(start, result);
    }

    @Test
    void testLerpAtEndReturnsTarget() {
        var start = new Vector2D(1, 1);
        var end = new Vector2D(9, 9);

        var result = start.lerp(end, 1);

        assertEquals(end, result);
    }

    @Test
    void testLerpAtMidpoint() {
        var start = new Vector2D(0, 0);
        var end = new Vector2D(10, 10);

        var result = start.lerp(end, 0.5);

        assertEquals(new Vector2D(5, 5), result);
    }

    @Test
    void testAngleBetweenPerpendicularVectors() {
        var vec1 = new Vector2D(1, 0);
        var vec2 = new Vector2D(0, 1);

        assertEquals(90.0, vec1.angle(vec2), 1e-9);
    }

    @Test
    void testAngleBetweenParallelVectors() {
        var vec1 = new Vector2D(1, 0);
        var vec2 = new Vector2D(2, 0);

        assertEquals(0.0, vec1.angle(vec2), 1e-9);
    }

    @Test
    void testAngleBetweenOppositeVectors() {
        var vec1 = new Vector2D(1, 0);
        var vec2 = new Vector2D(-1, 0);

        assertEquals(180.0, vec1.angle(vec2), 1e-9);
    }

    @Test
    void testAngleWithZeroVectorIsZero() {
        var vec = new Vector2D(1, 0);

        assertEquals(0.0, vec.angle(Vector2D.ZERO), 1e-9);
    }

}
