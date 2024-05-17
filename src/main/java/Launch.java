import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Launch extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();
        Group spaceship = createSpaceship();
        Circle bullet = createBullet();
        SpaceshipController spaceshipController = new SpaceshipController(spaceship);
        GunController gunController = new GunController(spaceship, bullet);
        root.getChildren().add(spaceship);
        root.getChildren().add(bullet);
        primaryStage.setTitle("Asteroids");
        Scene main = new Scene(root, Constants.getScreenWidth(), Constants.getScreenHeight(), Color.BLACK);

        main.setOnKeyPressed(spaceshipController::onKeyPressed);
        main.setOnKeyReleased(spaceshipController::onKeyRelease);
        main.addEventHandler(MouseEvent.MOUSE_CLICKED, spaceshipController::turnSpaceship);
        main.addEventHandler(MouseEvent.MOUSE_CLICKED, gunController::fireGun);
        primaryStage.setScene(main);
        primaryStage.show();
    }
    private Group createSpaceship(){
        Polygon polygon = new Polygon();
        Circle tip = new Circle();
        Circle bottom = new Circle();

        tip.setCenterX(400.0);
        tip.setCenterY(400.0);
        tip.setRadius(0.5);
        tip.setFill(Color.RED);

        bottom.setCenterX(400.0);
        bottom.setCenterY(370.0);
        bottom.setRadius(0.5);
        bottom.setFill(Color.RED);

        polygon.getPoints().addAll(
                400.0, 400.0,
                410.0, 370.0,
                390.0, 370.0);
        polygon.setFill(Color.WHITE);
        return new Group(polygon, tip, bottom);

    }

    private Circle createBullet() {
        return new Circle();
    }
}
