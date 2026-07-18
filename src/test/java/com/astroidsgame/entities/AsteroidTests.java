package com.astroidsgame.entities;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class AsteroidTests {

    @Test
    void createAsteroidReturnsCircle() {
        Node asteroid = Asteroid.createAsteroid();

        assertInstanceOf(Circle.class, asteroid);
    }

    @Test
    void createAsteroidHasExpectedGeometry() {
        Circle circle = (Circle) Asteroid.createAsteroid();

        assertEquals(0, circle.getCenterX());
        assertEquals(0, circle.getCenterY());
        assertEquals(10, circle.getRadius());
    }

    @Test
    void createAsteroidIsWhite() {
        Circle circle = (Circle) Asteroid.createAsteroid();

        assertEquals(Color.WHITE, circle.getFill());
    }

    @Test
    void createAsteroidReturnsNewInstanceEachCall() {
        Node first = Asteroid.createAsteroid();
        Node second = Asteroid.createAsteroid();

        assertNotSame(first, second);
    }
}
