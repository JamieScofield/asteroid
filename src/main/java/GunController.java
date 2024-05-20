
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GunController extends Environment{

    private Group spaceship;
    private Group root;
    private Circle bullet;
    private EventHandler<ActionEvent> endOfAnimation;
    public GunController (Group spaceship, Group root) {
        this.spaceship = spaceship;
        this.root = root;
        this.endOfAnimation = event -> deleteBullet();
    }

    public void fireGun (MouseEvent event) {
        this.bullet = new Circle();

        root.getChildren().add(bullet);
        double mouseclickXPosition = event.getX();
        double mouseclickYPosition = event.getY();
        Node spaceshipTip = spaceship.getChildren().get(1);
        Bounds spaceshipTipBounds = spaceshipTip.localToScene(spaceshipTip.getBoundsInLocal());
        TranslateTransition transition = new TranslateTransition(new Duration(250), bullet);
        transition.setToX(mouseclickXPosition - 400);
        transition.setToY(mouseclickYPosition - 400);
        bullet.setCenterX(spaceshipTipBounds.getCenterX());
        bullet.setCenterY(spaceshipTipBounds.getCenterY());
        bullet.setRadius(1);
        bullet.setFill(Color.YELLOW);
        transition.setOnFinished(endOfAnimation);
        transition.play();
    }

    private void deleteBullet () {
        for (int item=root.getChildren().size() -1; item > 0; item--) {
            if (root.getChildren().get(item) != spaceship) {
                root.getChildren().remove(item);
            }
        }
    }
}
