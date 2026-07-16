package com.astroidsgame.simulation;

import com.astroidsgame.math.Vector2D;

public class BulletState {

    private final EntityId id;
    private final Vector2D spawnPos;
    private final Vector2D velocity;
    private final long spawnNanos;

    public BulletState(EntityId id, Vector2D spawnPos, Vector2D velocity, long spawnNanos) {
        this.id = id;
        this.spawnPos = spawnPos;
        this.velocity = velocity;
        this.spawnNanos = spawnNanos;
    }

    public EntityId getId() {
        return id;
    }

    public Vector2D getSpawnPos() {
        return spawnPos;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public long getSpawnNanos() {
        return spawnNanos;
    }
}
