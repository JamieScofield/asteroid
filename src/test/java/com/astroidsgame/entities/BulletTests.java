package com.astroidsgame.entities;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class BulletTests {

    @Test
    void createBulletReturnsCircle() {
        Node bullet = Bullet.createBullet();

        assertInstanceOf(Circle.class, bullet);
    }

    @Test
    void createBulletHasExpectedGeometry() {
        Circle circle = (Circle) Bullet.createBullet();

        assertEquals(0, circle.getCenterX());
        assertEquals(0, circle.getCenterY());
        assertEquals(1, circle.getRadius());
    }

    @Test
    void createBulletIsYellow() {
        Circle circle = (Circle) Bullet.createBullet();

        assertEquals(Color.YELLOW, circle.getFill());
    }

    @Test
    void createBulletReturnsNewInstanceEachCall() {
        Node first = Bullet.createBullet();
        Node second = Bullet.createBullet();

        assertNotSame(first, second);
    }
}
