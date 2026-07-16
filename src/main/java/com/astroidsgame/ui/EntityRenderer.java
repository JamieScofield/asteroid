package com.astroidsgame.ui;

import com.astroidsgame.entities.Asteroid;
import com.astroidsgame.entities.Bullet;
import com.astroidsgame.entities.Ship;
import com.astroidsgame.simulation.SimEvent;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class EntityRenderer {

    private EntityRenderer() {
    }

    public static Node createNode(SimEvent.EntitySpawned spawned) {
        Node node = switch (spawned.type()) {
            case SHIP -> Ship.createShip();
            case ASTEROID -> Asteroid.createAsteroid();
            case BULLET -> Bullet.createBullet();
        };
        node.setTranslateX(spawned.x());
        node.setTranslateY(spawned.y());
        if (node instanceof Rectangle rectangle) {
            ((Rotate) rectangle.getTransforms().get(0)).setAngle(spawned.rotationDegrees());
        }
        return node;
    }

    public static void applyMove(Node node, SimEvent.EntityMoved moved) {
        node.setTranslateX(moved.x());
        node.setTranslateY(moved.y());
        if (node instanceof Rectangle rectangle) {
            ((Rotate) rectangle.getTransforms().get(0)).setAngle(moved.rotationDegrees());
        }
    }
}
