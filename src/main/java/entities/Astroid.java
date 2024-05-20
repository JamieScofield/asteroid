package entities;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Constants;

import javafx.geometry.Point2D;
import java.util.Random;

public class Astroid {

    private Group astroid;

    public Astroid() {
        Circle circleBig = new Circle();

        circleBig.setFill(Color.WHITE);
        circleBig.setRadius(5);
        Point2D asteroidSpawnPosition = setAstroidSpawn();
        circleBig.setCenterX(asteroidSpawnPosition.getX());
        circleBig.setCenterY(asteroidSpawnPosition.getY());
        this.astroid = new Group(circleBig);

    }

    public Group getAstroid() {
        return astroid;
    }

    private Point2D setAstroidSpawn() {
        Random rand1st = new Random();
        Random rand = new Random();
        int randIntCheck = rand1st.nextInt(4);
        int lowXPosition;
        int highXPosition;
        int lowYPosition;
        int highYPosition;
        int randomXPosition;
        int randomYPosition;
        switch (randIntCheck){
            case 0:
                lowXPosition = ((int)Constants.getScreenWidth()) - 50;
                highXPosition = (int)Constants.getScreenWidth();
                randomXPosition = rand.nextInt(highXPosition - lowXPosition + 1) + lowXPosition;
                randomYPosition = rand.nextInt((int)Constants.getScreenHeight());
                return new Point2D(randomXPosition, randomYPosition);
            case 1:
                lowXPosition = 0;
                highXPosition = 50;
                randomXPosition = rand.nextInt(highXPosition - lowXPosition + 1) + lowXPosition;
                randomYPosition = rand.nextInt((int)Constants.getScreenHeight());
                return new Point2D(randomXPosition, randomYPosition);
            case 2:
                lowYPosition = 0;
                highYPosition = 50;
                randomXPosition = rand.nextInt((int)Constants.getScreenWidth());
                randomYPosition = rand.nextInt(highYPosition - lowYPosition + 1) + lowYPosition;
                return new Point2D(randomXPosition, randomYPosition);
            case 3:
                lowYPosition = ((int)Constants.getScreenHeight()) - 50;
                highYPosition = (int)Constants.getScreenHeight();
                randomXPosition = rand.nextInt((int)Constants.getScreenWidth());
                randomYPosition = rand.nextInt(highYPosition - lowYPosition + 1) + lowYPosition;
                return new Point2D(randomXPosition, randomYPosition);
        }
        return null;
    }
}
