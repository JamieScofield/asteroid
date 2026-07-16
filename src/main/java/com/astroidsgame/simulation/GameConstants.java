package com.astroidsgame.simulation;

import java.util.Random;

public class GameConstants {

    public static double screenWidth = 800;
    public static double screenHeight = 800;
    public static final long TICK_INTERVAL_NANOS = 16_666_667L; // ~60Hz
    public static final long BULLET_LIFETIME_NANOS = 250_000_000L;
    public static final long ASTEROID_WANDER_NANOS = 2_500_000_000L;
    public static final double SHIP_SPEED_PER_TICK = 4.0;
    public static final Random random = new Random();

    public static double getScreenHeight() {
        return screenHeight;
    }

    public static double getScreenWidth() {
        return screenWidth;
    }

}
