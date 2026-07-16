package com.astroidsgame.simulation;

import com.astroidsgame.math.Vector2D;

public class BulletState {

    private final EntityId id;
    private final Vector2D spawnPos;
    private final Vector2D targetPos;
    private final long spawnNanos;

    public BulletState(EntityId id, Vector2D spawnPos, Vector2D targetPos, long spawnNanos) {
        this.id = id;
        this.spawnPos = spawnPos;
        this.targetPos = targetPos;
        this.spawnNanos = spawnNanos;
    }

    public EntityId getId() {
        return id;
    }

    public Vector2D getSpawnPos() {
        return spawnPos;
    }

    public Vector2D getTargetPos() {
        return targetPos;
    }

    public long getSpawnNanos() {
        return spawnNanos;
    }
}
