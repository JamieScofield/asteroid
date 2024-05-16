
import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Controller {
    private Polygon spaceship;
    private Boolean aPressed = false;
    private Boolean sPressed = false;
    private Boolean wPressed = false;
    private Boolean dPressed = false;

    public Controller(Polygon spaceship){
        this.spaceship = spaceship;
    }

    private void initiateMove() {

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

    public void onMouseClick(MouseEvent event) {
       double mouseClickXPosition = event.getX();
       double mouseClickYPosition = event.getY();
       double angle = calculateAngleFromMousePosition(mouseClickYPosition, mouseClickXPosition);
       createRotateObject((int)angle);
    }

    private void createRotateObject(int angle) {
        Rotate rotate = new Rotate(angle, 300, 270);
        System.out.println(angle);
        spaceship.getTransforms().add(rotate);
    }

    private double calculateAngleFromMousePosition(double mouseClickYPosition, double mouseClickXPosition) {

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

    public void moveUpRight() {
        moveUp();
        moveRight();
    }

    public void moveUpLeft() {
        moveUp();
        moveLeft();
    }

    public void moveDownRight() {
        moveDown();
        moveRight();
    }

    public void moveDownLeft() {
        moveDown();
        moveLeft();
    }
}
