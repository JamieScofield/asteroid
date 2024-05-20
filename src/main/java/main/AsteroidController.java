package main;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import java.util.Random;

public class AsteroidController extends Environment{
    private Group asteroid;
    private EventHandler<ActionEvent> endOfAnimation;

    public AsteroidController(Group asteroid) {
        this.asteroid = asteroid;
        this.endOfAnimation = event -> startAsteroidAnimation();
    }
    public void startAsteroidAnimation() {
            double targetX = findTargetAnimationLocation().getX();
            double targetY = findTargetAnimationLocation().getY();
            TranslateTransition transition = new TranslateTransition(new Duration(2500), asteroid);
            transition.setToX(targetX);
            transition.setToY(targetY);
            transition.setOnFinished(endOfAnimation);
            transition.setInterpolator(Interpolator.LINEAR);
            transition.play();
    }
    private Point2D findTargetAnimationLocation() {
        Random rand = new Random();
        int targetLocationX = rand.nextInt((int)Constants.getScreenWidth()-50);
        int targetLocationY = rand.nextInt((int)Constants.getScreenHeight()-50);
        return new Point2D(targetLocationX, targetLocationY);
    }

}
