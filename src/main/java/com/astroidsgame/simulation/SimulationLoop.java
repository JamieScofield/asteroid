package com.astroidsgame.simulation;

import com.astroidsgame.entities.EntityType;
import com.astroidsgame.math.GeometryMath;
import com.astroidsgame.math.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

import static com.astroidsgame.simulation.GameConstants.*;

public class SimulationLoop implements Runnable {

    private final LinkedBlockingQueue<InputEvent> inputQueue;
    private final LinkedBlockingQueue<SimEvent> outputQueue;

    private final ShipState ship = new ShipState(EntityId.next(), new Vector2D(400, 370));
    private final Map<EntityId, BulletState> bullets = new HashMap<>();
    private final Map<EntityId, AsteroidState> currentAsteroids = new HashMap<>();

    private volatile boolean running = true;

    private int asteroidSpawnCounter = 0;

    public SimulationLoop(LinkedBlockingQueue<InputEvent> inputQueue, LinkedBlockingQueue<SimEvent> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        outputQueue.offer(new SimEvent.EntitySpawned(
                ship.getId(), EntityType.SHIP, ship.getPosition().x(), ship.getPosition().y(), ship.getRotationDegrees()));
        spawnAsteroid();

        while (running) {
            long tickStart = System.nanoTime();
            tick(tickStart);
            long elapsed = System.nanoTime() - tickStart;
            long sleepNanos = TICK_INTERVAL_NANOS - elapsed;
            if (sleepNanos > 0) {
                LockSupport.parkNanos(sleepNanos);
            }
        }
    }

    private void tick(long nowNanos) {
        boolean rotationChanged = processInput(nowNanos);
        boolean positionChanged = updateShip();
        if (positionChanged || rotationChanged) {
            outputQueue.offer(new SimEvent.EntityMoved(
                    ship.getId(), ship.getPosition().x(), ship.getPosition().y(), ship.getRotationDegrees()));
        }

        updateBullets(nowNanos);
        updateAsteroid(nowNanos);
        spawnAsteroidIfDue();
    }

    private void spawnAsteroidIfDue() {
        if (asteroidSpawnCounter >= ASTEROID_SPAWN_INTERVAL_TICKS) {
            spawnAsteroid();
            asteroidSpawnCounter = 0;
        }
        asteroidSpawnCounter++;
    }

    private void spawnAsteroid() {
        AsteroidState asteroid = AsteroidState.createAsteroid();
        currentAsteroids.put(asteroid.getId(), asteroid);
        outputQueue.offer(new SimEvent.EntitySpawned(
                asteroid.getId(), EntityType.ASTEROID, asteroid.getPosition().x(), asteroid.getPosition().y(), 0));
    }

    private boolean processInput(long nowNanos) {
        boolean rotationChanged = false;
        InputEvent event;
        while ((event = inputQueue.poll()) != null) {
            switch (event) {
                case InputEvent.KeyDown keyDown -> ship.getPressedKeys().add(keyDown.key());
                case InputEvent.KeyUp keyUp -> ship.getPressedKeys().remove(keyUp.key());
                case InputEvent.MouseClicked mouseClicked -> {
                    handleMouseClick(mouseClicked, nowNanos);
                    rotationChanged = true;
                }
            }
        }
        return rotationChanged;
    }

    private void handleMouseClick(InputEvent.MouseClicked click, long nowNanos) {
        double delta = GeometryMath.calculateTurnDelta(
                ship.getPosition(), ship.getRotationDegrees(), click.x(), click.y());
        ship.setRotationDegrees(ship.getRotationDegrees() + delta);

        Vector2D tip = GeometryMath.shipTipPosition(ship.getPosition(), ship.getRotationDegrees());
        Vector2D direction = new Vector2D(click.x(), click.y()).subtract(tip);
        Vector2D unitDirection = direction.length() > 0
                ? direction.normalize()
                // click landed exactly on the tip - fall back to the ship's facing direction
                : GeometryMath.rotate(new Vector2D(0, 1), ship.getRotationDegrees());
        Vector2D velocity = unitDirection.scale(BULLET_SPEED_PER_SECOND);

        EntityId bulletId = EntityId.next();
        BulletState bullet = new BulletState(bulletId, tip, velocity, nowNanos);
        bullets.put(bulletId, bullet);
        outputQueue.offer(new SimEvent.EntitySpawned(bulletId, EntityType.BULLET, tip.x(), tip.y(), 0));
    }

    private boolean updateShip() {
        Vector2D delta = Vector2D.ZERO;
        if (ship.getPressedKeys().contains(GameKey.W)) {
            delta = delta.add(new Vector2D(0, -SHIP_SPEED_PER_TICK));
        }
        if (ship.getPressedKeys().contains(GameKey.S)) {
            delta = delta.add(new Vector2D(0, SHIP_SPEED_PER_TICK));
        }
        if (ship.getPressedKeys().contains(GameKey.A)) {
            delta = delta.add(new Vector2D(-SHIP_SPEED_PER_TICK, 0));
        }
        if (ship.getPressedKeys().contains(GameKey.D)) {
            delta = delta.add(new Vector2D(SHIP_SPEED_PER_TICK, 0));
        }
        if (delta.equals(Vector2D.ZERO)) {
            return false;
        }
        ship.setPosition(GeometryMath.wrapAround(ship.getPosition().add(delta)));
        return true;
    }

    private void updateBullets(long nowNanos) {
        List<EntityId> expired = new ArrayList<>();
        for (BulletState bullet : bullets.values()) {
            long elapsedNanos = nowNanos - bullet.getSpawnNanos();
            double elapsedSeconds = elapsedNanos / 1_000_000_000.0;
            Vector2D position = bullet.getSpawnPos().add(bullet.getVelocity().scale(elapsedSeconds));
            outputQueue.offer(new SimEvent.EntityMoved(bullet.getId(), position.x(), position.y(), 0));
            if (elapsedNanos >= BULLET_LIFETIME_NANOS) {
                expired.add(bullet.getId());
            }
        }
        for (EntityId id : expired) {
            bullets.remove(id);
            outputQueue.offer(new SimEvent.EntityRemoved(id));
        }
    }

    private void updateAsteroid(long nowNanos) {

        for (AsteroidState asteroid : currentAsteroids.values()) {
            double t = Math.min(1.0, (nowNanos - asteroid.getWanderStartNanos()) / (double) ASTEROID_WANDER_NANOS);
            Vector2D pos = asteroid.getWanderFrom().lerp(asteroid.getWanderTo(), t);
            asteroid.setPosition(pos);
            outputQueue.offer(new SimEvent.EntityMoved(asteroid.getId(), pos.x(), pos.y(), 0));
            if (t >= 1.0) {
                asteroid.setWanderFrom(pos);
                asteroid.setWanderTo(GeometryMath.randomWanderTarget(random));
                asteroid.setWanderStartNanos(nowNanos);
            }
        }
    }
}
