package main;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
/*
    This class is for methods that effect everything inside the game
*/
public class Environment {
    public void wrapAround(Node node) {
        Bounds nodeBounds = node.localToScene(node.getBoundsInLocal());
        double centerXPosition = nodeBounds.getCenterX();
        double centerYPosition = nodeBounds.getCenterY();

        if (centerYPosition > Constants.getScreenHeight()) {
            node.setTranslateY((-1 * (Constants.getScreenHeight()/2)) +40);
        }
        if (centerYPosition < 0) {
            node.setTranslateY((Constants.getScreenHeight()/2));
        }
        if (centerXPosition > Constants.getScreenWidth()) {
            node.setTranslateX((-1 * (Constants.getScreenWidth()/2)) +50);
        }
        if (centerXPosition < 0) {
            node.setTranslateX((Constants.getScreenWidth()/2));
        }

    }

    public double calculateAngleFromMousePosition(double mouseClickYPosition, double mouseClickXPosition, Group spaceship) {
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
}
