package com.astroidsgame.simulation;

import com.astroidsgame.math.Vector2D;

import java.util.Random;

public class GameConstants {

    public static double screenWidth = 800;
    public static double screenHeight = 800;
    public static final long TICK_INTERVAL_NANOS = 16_666_667L; // ~60Hz
    public static final long BULLET_LIFETIME_NANOS = 750_000_000L;
    public static final double BULLET_SPEED_PER_SECOND = 600.0;
    public static final long ASTEROID_WANDER_NANOS = 2_500_000_000L;
    public static final double SHIP_SPEED_PER_TICK = 4.0;
    public static final int ASTEROID_SPAWN_INTERVAL_TICKS = 50;
    public static final Vector2D SHIP_FORWARD = new Vector2D(0, 1);

    public static final Random random = new Random();

    public static double getScreenHeight() {
        return screenHeight;
    }

    public static double getScreenWidth() {
        return screenWidth;
    }

}
