package com.astroidsgame.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.Node;

public class Ship {

    private Ship(){}

    public static Node createShip() {
        // Local origin (0,0) is the ship's top-center pivot; the rectangle spans
        // x -15..15, y 0..30 around it, matching the simulation's geometry model.
        Rectangle rectangle = new Rectangle(-15, 0, 30, 30);
        rectangle.setFill(Color.WHITE);
        rectangle.getTransforms().add(new Rotate(0, 0, 0));
        return rectangle;
    }
}
