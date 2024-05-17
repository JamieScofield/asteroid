
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class SpaceshipController {
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
       double angle = calculateAngleFromMousePosition(mouseClickYPosition, mouseClickXPosition);
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

    private double calculateAngleFromMousePosition(double mouseClickYPosition, double mouseClickXPosition) {
        Node spaceshipTip = spaceship.getChildren().get(1);
        Node spaceshipBottom = spaceship.getChildren().get(2);

        Bounds spaceshipTipBounds = spaceshipTip.localToScene(spaceshipTip.getBoundsInLocal());
        Bounds spaceshipBottomBounds = spaceshipBottom.localToScene(spaceshipBottom.getBoundsInLocal());

        Point2D mouseClickCoordinates = new Point2D(mouseClickXPosition, mouseClickYPosition);
        System.out.println("mouse " + mouseClickCoordinates);
        Point2D spaceshipTipCoordinates = new Point2D(spaceshipTipBounds.getCenterX(), spaceshipTipBounds.getCenterY());
        System.out.println("tip " + spaceshipTipCoordinates);
        Point2D spaceshipBottomCoordinates = new Point2D(spaceshipBottomBounds.getCenterX(), spaceshipBottomBounds.getCenterY());
        System.out.println("bottom " + spaceshipBottomCoordinates);

        Point2D vectorA = spaceshipTipCoordinates.subtract(spaceshipBottomCoordinates);
        Point2D vectorB = mouseClickCoordinates.subtract(spaceshipBottomCoordinates);

        // calculating the axis through the spaceship to determine whenever to turn clockwise or anti clockwise
        // if the mouse click is above the line and the spaceship is pointing to the left then it turns clockwise
        // if the mouse click is above the line and the spaceship is pointing to the right then it turns anti clockwise


        double gradient = (spaceshipTipCoordinates.getY() - spaceshipBottomCoordinates.getY()) / (spaceshipTipCoordinates.getX() - spaceshipTipCoordinates.getY());
        double constant = spaceshipTipCoordinates.getY() - (gradient * spaceshipTipCoordinates.getX());
        double angle = vectorA.angle(vectorB);
        double checkYValue = mouseClickXPosition * gradient + constant;
        System.out.println("check y value " + checkYValue);

        if (spaceshipBottomCoordinates.getX() > spaceshipTipCoordinates.getX()) {

            if (checkYValue > mouseClickYPosition) {
                return angle;
            }
            return -1 * angle;
        }

        if (checkYValue > mouseClickYPosition) {
            return -1 * angle;
        }
        return angle;

    }

    private void initiateMove() {

        Environment.wrapAround(spaceship);

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
