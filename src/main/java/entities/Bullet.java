package entities;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet {
    private Group bullet;


    public Bullet(double centerX, double centerY) {
        Circle bullet =  new Circle(centerX, centerY, 1);
        bullet.setFill(Color.YELLOW);
        this.bullet = new Group(bullet);
    }
    public Group getBullet() {
        return bullet;
    }
}
