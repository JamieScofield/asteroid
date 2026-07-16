package com.astroidsgame.simulation;

import com.astroidsgame.math.Vector2D;

import java.util.EnumSet;

public class ShipState {

    private final EntityId id;
    private Vector2D position;
    private double rotationDegrees;
    private final EnumSet<GameKey> pressedKeys = EnumSet.noneOf(GameKey.class);

    public ShipState(EntityId id, Vector2D position) {
        this.id = id;
        this.position = position;
    }

    public EntityId getId() {
        return id;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public double getRotationDegrees() {
        return rotationDegrees;
    }

    public void setRotationDegrees(double rotationDegrees) {
        this.rotationDegrees = rotationDegrees;
    }

    public EnumSet<GameKey> getPressedKeys() {
        return pressedKeys;
    }
}
