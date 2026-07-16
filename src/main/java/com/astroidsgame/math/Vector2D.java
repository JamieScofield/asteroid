package com.astroidsgame.math;

public record Vector2D(double x, double y) {

    public static final Vector2D ZERO = new Vector2D(0, 0);

    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    // Unsigned angle in degrees between this vector and other, in range [0, 180].
    public double angle(Vector2D other) {
        double lengthProduct = length() * other.length();
        if (lengthProduct == 0) {
            return 0;
        }
        double dot = x * other.x + y * other.y;
        double cos = Math.clamp(dot / lengthProduct, -1.0, 1.0);
        return Math.toDegrees(Math.acos(cos));
    }

    public Vector2D lerp(Vector2D target, double t) {
        return new Vector2D(x + (target.x - x) * t, y + (target.y - y) * t);
    }

    public Vector2D scale(double factor) {
        return new Vector2D(x * factor, y * factor);
    }

    // Unit-length vector in the same direction, or ZERO if this vector has no length.
    public Vector2D normalize() {
        double length = length();
        if (length == 0) {
            return ZERO;
        }
        return new Vector2D(x / length, y / length);
    }
}
