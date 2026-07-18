package com.astroidsgame.entities;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class ShipTests {

    @Test
    void createShipReturnsRectangle() {
        Node ship = Ship.createShip();

        assertInstanceOf(Rectangle.class, ship);
    }

    @Test
    void createShipHasExpectedBounds() {
        Rectangle rectangle = (Rectangle) Ship.createShip();

        assertEquals(-15, rectangle.getX());
        assertEquals(0, rectangle.getY());
        assertEquals(30, rectangle.getWidth());
        assertEquals(30, rectangle.getHeight());
    }

    @Test
    void createShipIsWhite() {
        Rectangle rectangle = (Rectangle) Ship.createShip();

        assertEquals(Color.WHITE, rectangle.getFill());
    }

    @Test
    void createShipHasRotationPivotAtOrigin() {
        Rectangle rectangle = (Rectangle) Ship.createShip();

        assertEquals(1, rectangle.getTransforms().size());
        Rotate rotate = (Rotate) rectangle.getTransforms().get(0);
        assertEquals(0, rotate.getAngle());
        assertEquals(0, rotate.getPivotX());
        assertEquals(0, rotate.getPivotY());
    }

    @Test
    void createShipReturnsNewInstanceEachCall() {
        Node first = Ship.createShip();
        Node second = Ship.createShip();

        assertInstanceOf(Rectangle.class, first);
        assertInstanceOf(Rectangle.class, second);
        assertNotSame(first, second);
    }
}