import javafx.geometry.Bounds;
import javafx.scene.Node;


/*
    This class is for methods that effect everything inside the game
*/
public class Environment {
    public static void wrapAround(Node node) {
        Bounds spaceshipBounds = node.localToScene(node.getBoundsInLocal());
        double centerXPosition = spaceshipBounds.getCenterX();
        double centerYPosition = spaceshipBounds.getCenterY();

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
}
