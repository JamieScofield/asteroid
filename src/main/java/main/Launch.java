package main;

import entities.Astroid;
import entities.Spaceship;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Launch extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();
        Scene main = new Scene(root, Constants.getScreenWidth(), Constants.getScreenHeight(), Color.BLACK);
        Group spaceship = new Spaceship().getSpaceship();
        Group asteroid = new Astroid().getAstroid();
        SpaceshipController spaceshipController = new SpaceshipController(spaceship);
        GunController gunController = new GunController(spaceship, root);
        root.getChildren().add(spaceship);
        root.getChildren().add(asteroid);
        primaryStage.setTitle("Asteroids");

        main.setOnKeyPressed(spaceshipController::onKeyPressed);
        main.setOnKeyReleased(spaceshipController::onKeyRelease);
        main.addEventHandler(MouseEvent.MOUSE_CLICKED, spaceshipController::turnSpaceship);
        main.addEventHandler(MouseEvent.MOUSE_CLICKED, gunController::fireGun);
        gunController.clearBulletList();
        primaryStage.setScene(main);
        primaryStage.show();
    }

}
