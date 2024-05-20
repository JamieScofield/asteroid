package main;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import main.Environment;


public class SpaceshipController extends Environment {
    private Group spaceship;
    private Boolean aPressed = false;
    private Boolean sPressed = false;
    private Boolean wPressed = false;
    private Boolean dPressed = false;

    public SpaceshipController(Group spaceship){
        this.spaceship = spaceship;
    }

    public void onKeyPressed(KeyEvent event) {
        switch (event.getCode()){
            case A:
                aPressed = true;
                break;
            case S:
                sPressed = true;
                break;
            case D:
                dPressed = true;
                break;
            case W:
                wPressed = true;
                break;
            default:
                break;
        }
        initiateMove();
    }

    public void onKeyRelease(KeyEvent event) {
        switch (event.getCode()){
            case A:
                aPressed = false;
                break;
            case S:
                sPressed = false;
                break;
            case D:
                dPressed = false;
                break;
            case W:
                wPressed = false;
                break;
            default:
                break;
        }
    }

    public void turnSpaceship(MouseEvent event) {
       double mouseClickXPosition = event.getX();
       double mouseClickYPosition = event.getY();
       double angle = calculateAngleFromMousePosition(mouseClickYPosition, mouseClickXPosition, spaceship);
       System.out.println(angle);
       createRotateObject((int)angle);
    }

    public void moveUp() {
        spaceship.setTranslateY(spaceship.getTranslateY() -5);
    }

    public void moveDown() {
        spaceship.setTranslateY(spaceship.getTranslateY() +5);
    }

    public void moveRight() {
        spaceship.setTranslateX(spaceship.getTranslateX() +5);
    }

    public void moveLeft() {
        spaceship.setTranslateX(spaceship.getTranslateX() -5);
    }

    private void createRotateObject(int angle) {
        Rotate rotate = new Rotate(angle, 400, 370);
        System.out.println(angle);
        spaceship.getTransforms().add(rotate);
    }

    private void initiateMove() {

        wrapAround(spaceship);

        if (sPressed) {
            moveDown();
        }

        if (dPressed) {
            moveRight();
        }

        if (aPressed) {
            moveLeft();
        }

        if (wPressed) {
            moveUp();
        }

    }


}
