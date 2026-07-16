package com.astroidsgame.simulation;

import java.util.concurrent.LinkedBlockingQueue;

public class SimulationEngine {

    private final LinkedBlockingQueue<InputEvent> inputQueue = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<SimEvent> outputQueue = new LinkedBlockingQueue<>();
    private final SimulationLoop loop = new SimulationLoop(inputQueue, outputQueue);
    private final Thread thread = new Thread(loop, "sim-loop");

    public void start() {
        thread.start();
    }

    public void shutdown() {
        loop.stop();
        try {
            thread.join(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public LinkedBlockingQueue<InputEvent> getInputQueue() {
        return inputQueue;
    }

    public LinkedBlockingQueue<SimEvent> getOutputQueue() {
        return outputQueue;
    }
}
