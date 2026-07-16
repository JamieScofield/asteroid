package com.astroidsgame.simulation;

public class AsteroidState {

    private final EntityId id;
    private Vector2D position;
    private Vector2D wanderFrom;
    private Vector2D wanderTo;
    private long wanderStartNanos;

    public AsteroidState(EntityId id, Vector2D position, Vector2D wanderTo, long wanderStartNanos) {
        this.id = id;
        this.position = position;
        this.wanderFrom = position;
        this.wanderTo = wanderTo;
        this.wanderStartNanos = wanderStartNanos;
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

    public Vector2D getWanderFrom() {
        return wanderFrom;
    }

    public void setWanderFrom(Vector2D wanderFrom) {
        this.wanderFrom = wanderFrom;
    }

    public Vector2D getWanderTo() {
        return wanderTo;
    }

    public void setWanderTo(Vector2D wanderTo) {
        this.wanderTo = wanderTo;
    }

    public long getWanderStartNanos() {
        return wanderStartNanos;
    }

    public void setWanderStartNanos(long wanderStartNanos) {
        this.wanderStartNanos = wanderStartNanos;
    }
}
