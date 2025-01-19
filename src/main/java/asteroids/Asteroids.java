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
import java.util.Random;

public class Asteroids extends Character {
    
    
    

    private double raotationMovemnet;

    public Asteroids(int x, int y) {

        super(new PolygonFactory().createPolygon(), x, y);

        Random ran = new Random();

        super.getCharacter().setRotate(ran.nextInt(360));
        int accrelation = 1 + ran.nextInt(10);
        int i = 0;
        while (i < accrelation) {

            accrelete();

            i++;
        }

        this.raotationMovemnet = 0.5 + ran.nextDouble();

    }

    @Override
    public void move() {

        super.move();

        super.getCharacter().setRotate(super.getCharacter().getRotate() + this.raotationMovemnet);
    }

}
