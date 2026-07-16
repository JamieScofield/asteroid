package com.astroidsgame.entities;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Asteroid {
    private Asteroid(){}

    public static Node createAsteroid() {
        Circle circle = new Circle(0, 0, 10);
        circle.setFill(Color.WHITE);
        return circle;
    }
}
