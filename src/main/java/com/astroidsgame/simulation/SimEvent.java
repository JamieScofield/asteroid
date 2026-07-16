package com.astroidsgame.simulation;

import com.astroidsgame.entities.EntityType;

public sealed interface SimEvent {
    record EntitySpawned(EntityId id, EntityType type, double x, double y, double rotationDegrees) implements SimEvent {}
    record EntityMoved(EntityId id, double x, double y, double rotationDegrees) implements SimEvent {}
    record EntityRemoved(EntityId id) implements SimEvent {}
}
