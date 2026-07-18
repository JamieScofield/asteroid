package com.astroidsgame;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Boots the real JavaFX toolkit and drives GameApplication end-to-end, so a
// real window is expected to appear while this runs. Kept out of the normal
// `test` task on purpose - run explicitly via `./gradlew integrationTest`.
class GameApplicationIntegrationTest {

    @BeforeAll
    static void startJavaFxToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX toolkit failed to start");
    }

    @AfterAll
    static void stopJavaFxToolkit() {
        Platform.exit();
    }

    @Test
    void startWiresShipIntoSceneAndRespondsToRotationKey() throws InterruptedException {
        GameApplication app = new GameApplication();
        AtomicReference<Stage> stageRef = new AtomicReference<>();
        CountDownLatch started = new CountDownLatch(1);

        Platform.runLater(() -> {
            Stage stage = new Stage();
            app.start(stage);
            stageRef.set(stage);
            started.countDown();
        });
        assertTrue(started.await(5, TimeUnit.SECONDS), "GameApplication.start() did not complete");

        Rectangle shipNode = waitForShipNode(stageRef.get());
        double rotationBefore = currentAngle(shipNode);

        Platform.runLater(() -> stageRef.get().getScene().getOnKeyPressed()
                .handle(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false)));

        double rotationAfter = waitForRotationChange(shipNode, rotationBefore);
        assertTrue(rotationAfter > rotationBefore, "expected ship to rotate after the A key was pressed");

        Platform.runLater(app::stop);
    }

    private static Rectangle waitForShipNode(Stage stage) throws InterruptedException {
        long deadlineNanos = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
        while (System.nanoTime() < deadlineNanos) {
            Rectangle shipNode = findShipNode(stage);
            if (shipNode != null) {
                return shipNode;
            }
            Thread.sleep(50);
        }
        throw new AssertionError("ship node never appeared in the scene graph");
    }

    // The ship is the only Rectangle spawned at startup - asteroids and bullets render as circles.
    private static Rectangle findShipNode(Stage stage) throws InterruptedException {
        AtomicReference<Rectangle> found = new AtomicReference<>();
        runOnFxThreadAndWait(() -> {
            Scene scene = stage.getScene();
            for (Node node : scene.getRoot().getChildrenUnmodifiable()) {
                if (node instanceof Rectangle rectangle) {
                    found.set(rectangle);
                    return;
                }
            }
        });
        return found.get();
    }

    private static double currentAngle(Rectangle shipNode) throws InterruptedException {
        AtomicReference<Double> angle = new AtomicReference<>();
        runOnFxThreadAndWait(() -> angle.set(((Rotate) shipNode.getTransforms().get(0)).getAngle()));
        return angle.get();
    }

    private static double waitForRotationChange(Rectangle shipNode, double previousAngle) throws InterruptedException {
        long deadlineNanos = System.nanoTime() + TimeUnit.SECONDS.toNanos(3);
        while (System.nanoTime() < deadlineNanos) {
            double angle = currentAngle(shipNode);
            if (angle != previousAngle) {
                return angle;
            }
            Thread.sleep(50);
        }
        throw new AssertionError("ship rotation never changed after the key press");
    }

    private static void runOnFxThreadAndWait(Runnable action) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            action.run();
            latch.countDown();
        });
        latch.await(1, TimeUnit.SECONDS);
    }
}
