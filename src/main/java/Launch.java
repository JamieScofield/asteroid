import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Launch extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();
        Polygon spaceship = createSpaceship();
        Controller controller = new Controller(spaceship);
        root.getChildren().add(spaceship);
        primaryStage.setTitle("Asteroids");
        Scene main = new Scene(root, 600, 600, Color.BLACK);

        main.setOnKeyPressed(controller::onKeyPressed);
        main.setOnKeyReleased(controller::onKeyRelease);
        main.setOnMousePressed(controller::onMouseClick);
        primaryStage.setScene(main);
        primaryStage.show();
    }
    private Polygon createSpaceship(){
        Polygon spaceship = new Polygon();
        spaceship.getPoints().addAll(
                300.0, 300.0,
                310.0, 270.0,
                290.0, 270.0);
        spaceship.setFill(Color.WHITE);
        return spaceship;
    }
}
