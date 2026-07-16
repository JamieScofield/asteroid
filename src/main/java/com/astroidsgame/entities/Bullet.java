package com.astroidsgame.entities;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet {
    private Bullet(){}


    public static Node createBullet() {
        Circle circle = new Circle(0, 0, 1);
        circle.setFill(Color.YELLOW);
        return circle;
    }
}
