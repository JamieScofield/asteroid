package com.astroidsgame.simulation;

import java.util.Random;

public class GeometryMath {

    // The ship's position tracks its top-center pivot point (matching the original
    // rectangle's top-center anchor); the "tip" (front of the ship) is 30 units
    // further along the ship's local y-axis before rotation is applied.
    private static final Vector2D SHIP_LOCAL_TIP_OFFSET = new Vector2D(0, 30);

    private GeometryMath() {
    }

    public static Vector2D rotate(Vector2D vector, double degrees) {
        double radians = Math.toRadians(degrees);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        return new Vector2D(
                vector.x() * cos - vector.y() * sin,
                vector.x() * sin + vector.y() * cos);
    }

    public static Vector2D shipTipPosition(Vector2D shipPosition, double shipRotationDegrees) {
        return shipPosition.add(rotate(SHIP_LOCAL_TIP_OFFSET, shipRotationDegrees));
    }

    // Returns the signed angle delta (degrees) to add to the ship's current rotation
    // so that it turns to face the given mouse click point.
    public static double calculateTurnDelta(Vector2D shipPosition, double shipRotationDegrees,
                                             double mouseX, double mouseY) {
        Vector2D tip = shipTipPosition(shipPosition, shipRotationDegrees);
        Vector2D bottom = shipPosition;
        Vector2D mouse = new Vector2D(mouseX, mouseY);

        Vector2D vectorA = tip.subtract(bottom);
        Vector2D vectorB = mouse.subtract(bottom);
        double angle = vectorA.angle(vectorB);

        // Line through the ship's axis (tip/bottom), used to tell which side of the
        // ship's facing direction the mouse click landed on. Note: when the ship
        // faces exactly up or down, tip.x() == bottom.x() and this line is vertical
        // (gradient is infinite) — the resulting NaN comparisons below simply fall
        // through to the "else" branch rather than throwing, so this is a known
        // residual edge case rather than a crash.
        double gradient = (tip.y() - bottom.y()) / (tip.x() - bottom.x());
        double constant = tip.y() - (gradient * tip.x());
        double checkYValue = mouseX * gradient + constant;

        if (bottom.x() > tip.x()) {
            return checkYValue > mouseY ? angle : -angle;
        }
        return checkYValue > mouseY ? -angle : angle;
    }

    // Ported from Environment.wrapAround: reflects the ship back onto screen using
    // the same absolute-reset offsets as the original, expressed relative to the
    // ship's initial spawn point (400, 370) and its ~15px half-size.
    public static Vector2D wrapAround(Vector2D position) {
        double screenWidth = GameConstants.getScreenWidth();
        double screenHeight = GameConstants.getScreenHeight();
        double centerX = position.x();
        double centerY = position.y() + 15;

        double x = position.x();
        double y = position.y();

        if (centerY > screenHeight) {
            y = 370 + (-1 * (screenHeight / 2)) + 40;
        }
        if (centerY < 0) {
            y = 370 + (screenHeight / 2);
        }
        if (centerX > screenWidth) {
            x = 400 + (-1 * (screenWidth / 2)) + 50;
        }
        if (centerX < 0) {
            x = 400 + (screenWidth / 2);
        }

        return new Vector2D(x, y);
    }

    public static Vector2D randomAsteroidSpawn(Random random) {
        int side = random.nextInt(4);
        int lowX;
        int highX;
        int lowY;
        int highY;
        switch (side) {
            case 0:
                lowX = ((int) GameConstants.getScreenWidth()) - 50;
                highX = (int) GameConstants.getScreenWidth();
                return new Vector2D(
                        random.nextInt(highX - lowX + 1) + lowX,
                        random.nextInt((int) GameConstants.getScreenHeight()));
            case 1:
                lowX = 0;
                highX = 50;
                return new Vector2D(
                        random.nextInt(highX - lowX + 1) + lowX,
                        random.nextInt((int) GameConstants.getScreenHeight()));
            case 2:
                lowY = 0;
                highY = 50;
                return new Vector2D(
                        random.nextInt((int) GameConstants.getScreenWidth()),
                        random.nextInt(highY - lowY + 1) + lowY);
            default:
                lowY = ((int) GameConstants.getScreenHeight()) - 50;
                highY = (int) GameConstants.getScreenHeight();
                return new Vector2D(
                        random.nextInt((int) GameConstants.getScreenWidth()),
                        random.nextInt(highY - lowY + 1) + lowY);
        }
    }

    public static Vector2D randomWanderTarget(Random random) {
        return new Vector2D(
                random.nextInt((int) GameConstants.getScreenWidth() - 50),
                random.nextInt((int) GameConstants.getScreenHeight() - 50));
    }
}
