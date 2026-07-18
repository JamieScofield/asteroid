package com.astroidsgame.simulation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationLoopTests {
    private SimulationLoop simLoop;
    private LinkedBlockingQueue<InputEvent> testInputQueue;
    private LinkedBlockingQueue<SimEvent> testOutPutQueue;
    private Thread simThread;


    @BeforeEach
    void setUp() {
        testInputQueue = new LinkedBlockingQueue<>();
        testOutPutQueue = new LinkedBlockingQueue<>();
        simLoop = new SimulationLoop(testInputQueue, testOutPutQueue);

        simThread = new Thread(simLoop);
        simThread.start();
    }

    @AfterEach
    void cleanUp() throws InterruptedException {
        simLoop.stop();
        simThread.join();
    }


    @Test
    void spaceshipRotatesWhenAKeyIsPressed() throws InterruptedException {

        var testShip = simLoop.getShipState();
        var keyDownEvent = Optional.of(GameKey.A).map(InputEvent.KeyDown::new);
        var keyUpEvent = Optional.of(GameKey.A).map(InputEvent.KeyUp::new);
        testInputQueue.put(keyDownEvent.get());

        SimEvent event;
        do {
            event = testOutPutQueue.poll(1, TimeUnit.SECONDS);
            assertNotNull(event, "timed out waiting for ship to rotate");
        } while (!(event instanceof SimEvent.EntityMoved moved) || !moved.id().equals(testShip.getId()));

        testInputQueue.put(keyUpEvent.get());

        assertTrue(testShip.getRotationDegrees() > 0);

    }

    @Test
    void spaceshipRotatesWhenDKeyIsPressed() throws InterruptedException {

        var testShip = simLoop.getShipState();
        var keyDownEvent = Optional.of(GameKey.D).map(InputEvent.KeyDown::new);
        var keyUpEvent = Optional.of(GameKey.D).map(InputEvent.KeyUp::new);
        testInputQueue.put(keyDownEvent.get());

        SimEvent event;
        do {
            event = testOutPutQueue.poll(1, TimeUnit.SECONDS);
            assertNotNull(event, "timed out waiting for ship to rotate");
        } while (!(event instanceof SimEvent.EntityMoved moved) || !moved.id().equals(testShip.getId()));

        testInputQueue.put(keyUpEvent.get());

        assertTrue(testShip.getRotationDegrees() < 0);
    }

    @Test
    void spaceShipMovesForwardWhenKeyPressed() throws InterruptedException {

        var testShip = simLoop.getShipState();
        var beforeShipPosition = testShip.getPosition();
        var keyDownEvent = Optional.of(GameKey.W).map(InputEvent.KeyDown::new);
        var keyUpEvent = Optional.of(GameKey.W).map(InputEvent.KeyUp::new);
        testInputQueue.put(keyDownEvent.get());

        SimEvent event;

        do {
            event = testOutPutQueue.poll(1, TimeUnit.SECONDS);
            assertNotNull(event, "timed out waiting for ship to rotate");
        } while (!(event instanceof SimEvent.EntityMoved moved) || !moved.id().equals(testShip.getId()));

        testInputQueue.put(keyUpEvent.get());

        var afterShipPosition = testShip.getPosition();

        assertTrue(afterShipPosition.y() > beforeShipPosition.y());

    }

    @Test
    void spaceShipMovesBackwardWhenKeyPressed() throws InterruptedException {

        var testShip = simLoop.getShipState();
        var beforeShipPosition = testShip.getPosition();
        var keyDownEvent = Optional.of(GameKey.S).map(InputEvent.KeyDown::new);
        var keyUpEvent = Optional.of(GameKey.S).map(InputEvent.KeyUp::new);
        testInputQueue.put(keyDownEvent.get());

        SimEvent event;

        do {
            event = testOutPutQueue.poll(1, TimeUnit.SECONDS);
            assertNotNull(event, "timed out waiting for ship to rotate");
        } while (!(event instanceof SimEvent.EntityMoved moved) || !moved.id().equals(testShip.getId()));

        testInputQueue.put(keyUpEvent.get());

        var afterShipPosition = testShip.getPosition();

        assertTrue(afterShipPosition.y() < beforeShipPosition.y());

    }

}
