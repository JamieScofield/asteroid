package com.astroidsgame.simulation;

import java.util.concurrent.atomic.AtomicLong;

public record EntityId(long value) {

    private static final AtomicLong COUNTER = new AtomicLong(0);

    public static EntityId next() {
        return new EntityId(COUNTER.getAndIncrement());
    }
}
