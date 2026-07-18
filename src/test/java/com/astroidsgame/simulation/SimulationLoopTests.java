package com.astroidsgame.simulation;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationLoopTests {
    @Test
    void spaceshipRotatesWhenAKeyIsPressed() throws InterruptedException {
        LinkedBlockingQueue<InputEvent> testInputQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<SimEvent> testOutPutQueue = new LinkedBlockingQueue<>();
        SimulationLoop simLoop = new SimulationLoop(testInputQueue, testOutPutQueue);

        Thread simThread = new Thread(simLoop);
        simThread.start();

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

        simLoop.stop();
        simThread.join();
    }
}
