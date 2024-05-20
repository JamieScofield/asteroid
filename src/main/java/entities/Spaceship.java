package entities;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Spaceship {

    private Group spaceship;

    public Spaceship() {
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
        this.spaceship = new Group(polygon, tip, bottom);
    }

    public Group getSpaceship() {
        return spaceship;
    }
}
