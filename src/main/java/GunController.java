
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GunController extends Environment{

    private Group spaceship;
    private Circle bullet;
    public GunController (Group spaceship, Circle bullet) {
        this.spaceship = spaceship;
        this.bullet = bullet;
    }

    public void fireGun (MouseEvent event) {
        Node spaceshipTip = spaceship.getChildren().get(1);
        Bounds spaceshipTipBounds = spaceshipTip.localToScene(spaceshipTip.getBoundsInLocal());
        bullet.setCenterX(spaceshipTipBounds.getCenterX());
        bullet.setCenterY(spaceshipTipBounds.getCenterY());
        bullet.setRadius(1);
        bullet.setFill(Color.YELLOW);
        bullet.setTranslateX(100);
    }
}
