/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

/**
 *
 * @author Nikhil singh
 */
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

public abstract class Character {

    private boolean isAlive;
    private Polygon character;
    private Point2D movement;

    public Character(Polygon ship, int x, int y) {

        this.character = ship;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.isAlive  = true;
        this.movement = new Point2D(0, 0);

    }

    public Polygon getCharacter() {

        return this.character;
    }

    public void rotateLeft() {

        this.character.setRotate(this.character.getRotate() - 5);

    }

    public void rotateRight() {

        this.character.setRotate(this.character.getRotate() + 5);

    }

    public void move() {

        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());

        if (this.character.getTranslateX() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + AsteroidsApplication.WIDTH);

        }
        if (this.character.getTranslateX() > AsteroidsApplication.WIDTH) {
            this.character.setTranslateX(this.character.getTranslateX() % AsteroidsApplication.WIDTH);

        }
        if (this.character.getTranslateY() < 0) {
            this.character.setTranslateY(this.character.getTranslateY() + AsteroidsApplication.HEIGHT);

        }
        if (this.character.getTranslateY() > AsteroidsApplication.HEIGHT) {
            this.character.setTranslateY(this.character.getTranslateY() % AsteroidsApplication.HEIGHT);

        }
    }

    public void accrelete() {

        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= .05;
        changeY *= .05;

        this.movement = this.movement.add(new Point2D(changeX, changeY));

    }

    public boolean collide(Character other) {

        Shape crossArea = Shape.intersect(this.character, other.getCharacter());

        return crossArea.getBoundsInLocal().getWidth() != -1;
    }

    public Point2D getMovement() {

        return this.movement;
    }

    public void setMovement(Point2D move) {

        this.movement = this.movement.add(move);
    }

    
    public void setAlive(boolean value){
        
        this.isAlive = value;
        
    }
    
    public boolean isAlive(){
        
        return this.isAlive;
    }
    
}
