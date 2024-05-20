package main;

import entities.Bullet;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class GunController extends Environment {

    private Group spaceship;
    private Group root;
    private Group bullet;
    private ArrayList<Group> bulletList = new ArrayList<>();
    private EventHandler<ActionEvent> endOfAnimation;
    public GunController (Group spaceship, Group root) {
        this.spaceship = spaceship;
        this.root = root;
        this.endOfAnimation = event -> deleteBullet();
    }

    public void fireGun (MouseEvent event) {

        double mouseclickXPosition = event.getX();
        double mouseclickYPosition = event.getY();
        Node spaceshipTip = spaceship.getChildren().get(1);
        Bounds spaceshipTipBounds = spaceshipTip.localToScene(spaceshipTip.getBoundsInLocal());

        this.bullet = new Bullet(spaceshipTipBounds.getCenterX(), spaceshipTipBounds.getCenterY()).getBullet();
        bulletList.add(bullet);
        root.getChildren().add(bullet);

        TranslateTransition transition = new TranslateTransition(new Duration(250), bullet);
        transition.setToX(mouseclickXPosition - 400);
        transition.setToY(mouseclickYPosition - 400);
        transition.setOnFinished(endOfAnimation);
        transition.play();
    }
    // do not want to pollute the program by storing bullets in a list.
    public void clearBulletList() {
        bulletList.clear();
    }
    // didnt want the bullets to persist on the scene. I needed to reference a list so i can keep track of the created bullets
    // this fixed an issue with the bullets being "forgotten" on rapid clicks
    private void deleteBullet () {
        for (int item=0; item < bulletList.size()-1; item++) {
            root.getChildren().remove(bulletList.get(item));
        }
    }
}
