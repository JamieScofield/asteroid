package com.astroidsgame.ui;

import com.astroidsgame.simulation.GameConstants;
import com.astroidsgame.simulation.InputEvent;
import com.astroidsgame.simulation.SimulationEngine;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.LinkedBlockingQueue;

public class GameApplication extends Application {

    private SimulationEngine engine;

    @Override
    public void start(Stage primaryStage) {
        engine = new SimulationEngine();
        engine.start();

        Group root = new Group();
        Scene scene = new Scene(root, GameConstants.getScreenWidth(), GameConstants.getScreenHeight(), Color.BLACK);

        RenderLoop renderLoop = new RenderLoop(engine.getOutputQueue(), root);
        renderLoop.start();

        LinkedBlockingQueue<InputEvent> inputQueue = engine.getInputQueue();
        scene.setOnKeyPressed(event -> InputTranslator.keyDown(event).ifPresent(inputQueue::offer));
        scene.setOnKeyReleased(event -> InputTranslator.keyUp(event).ifPresent(inputQueue::offer));
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> inputQueue.offer(InputTranslator.mouseClicked(event)));

        primaryStage.setTitle("Asteroids");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        if (engine != null) {
            engine.shutdown();
        }
    }
}
