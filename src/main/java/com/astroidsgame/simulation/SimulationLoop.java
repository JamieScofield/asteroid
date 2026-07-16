package com.astroidsgame.simulation;

import com.astroidsgame.entities.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

public class SimulationLoop implements Runnable {

    private static final long TICK_INTERVAL_NANOS = 16_666_667L; // ~60Hz
    private static final long BULLET_LIFETIME_NANOS = 250_000_000L;
    private static final long ASTEROID_WANDER_NANOS = 2_500_000_000L;
    private static final double SHIP_SPEED_PER_TICK = 4.0;

    private final LinkedBlockingQueue<InputEvent> inputQueue;
    private final LinkedBlockingQueue<SimEvent> outputQueue;
    private final Random random = new Random();

    private final ShipState ship = new ShipState(EntityId.next(), new Vector2D(400, 370));
    private final AsteroidState asteroid;
    private final Map<EntityId, BulletState> bullets = new HashMap<>();

    private volatile boolean running = true;

    public SimulationLoop(LinkedBlockingQueue<InputEvent> inputQueue, LinkedBlockingQueue<SimEvent> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        long now = System.nanoTime();
        this.asteroid = new AsteroidState(EntityId.next(), GeometryMath.randomAsteroidSpawn(random),
                GeometryMath.randomWanderTarget(random), now);
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        outputQueue.offer(new SimEvent.EntitySpawned(
                ship.getId(), EntityType.SHIP, ship.getPosition().x(), ship.getPosition().y(), ship.getRotationDegrees()));
        outputQueue.offer(new SimEvent.EntitySpawned(
                asteroid.getId(), EntityType.ASTEROID, asteroid.getPosition().x(), asteroid.getPosition().y(), 0));

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
        EntityId bulletId = EntityId.next();
        BulletState bullet = new BulletState(bulletId, tip, new Vector2D(click.x(), click.y()), nowNanos);
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
            double t = Math.min(1.0, (nowNanos - bullet.getSpawnNanos()) / (double) BULLET_LIFETIME_NANOS);
            Vector2D pos = bullet.getSpawnPos().lerp(bullet.getTargetPos(), t);
            outputQueue.offer(new SimEvent.EntityMoved(bullet.getId(), pos.x(), pos.y(), 0));
            if (t >= 1.0) {
                expired.add(bullet.getId());
            }
        }
        for (EntityId id : expired) {
            bullets.remove(id);
            outputQueue.offer(new SimEvent.EntityRemoved(id));
        }
    }

    private void updateAsteroid(long nowNanos) {
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
