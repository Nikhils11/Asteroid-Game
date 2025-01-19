package asteroids;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.Point2D;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.scene.text.Text;
import java.util.concurrent.atomic.AtomicInteger;

public class AsteroidsApplication extends Application {

    public static int WIDTH = 300;
    public static int HEIGHT = 200;

    public void start(Stage window) throws Exception {
//        Creatnig the layout for the application;

        Pane layout = new Pane();
        layout.setPrefSize(WIDTH, HEIGHT);
        layout.setBackground(Background.EMPTY);
        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);

        AtomicInteger points = new AtomicInteger();
        Text text = new Text(10, 20, "Points: 0");
        layout.getChildren().add(ship.getCharacter());

        layout.getChildren().add(text);

        Scene view = new Scene(layout);

        ArrayList<Asteroids> list = this.addAsterods();

        list.forEach(value -> {

            layout.getChildren().add(value.getCharacter());
        });

        Map<KeyCode, Boolean> pressedKey = new HashMap<>();

        view.setOnKeyPressed(pressed -> {

            pressedKey.put(pressed.getCode(), true);

        });
        view.setOnKeyReleased(pressed -> {

            pressedKey.put(pressed.getCode(), false);

        });
//        Point2D movement = new Point2D(1, 0);
        ArrayList<Projectiles> proList = new ArrayList<>();
        new AnimationTimer() {

            public void handle(long now) {

                if (pressedKey.getOrDefault(KeyCode.LEFT, false)) {

                    ship.rotateLeft();
                }
                if (pressedKey.getOrDefault(KeyCode.RIGHT, false)) {

                    ship.rotateRight();
                }

                if (pressedKey.getOrDefault(KeyCode.UP, false)) {

                    ship.accrelete();

                }

                if (pressedKey.getOrDefault(KeyCode.SPACE, Boolean.FALSE) && proList.size() <3) {

                    Projectiles projec = new Projectiles((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projec.getCharacter().setRotate(ship.getCharacter().getRotate());
                    proList.add(projec);

                    projec.accrelete();

                    projec.setMovement(projec.getMovement().normalize().multiply(3));
                    layout.getChildren().add(projec.getCharacter());
                }

                ship.move();

                list.forEach(asteroid -> asteroid.move());

                proList.forEach(projectiles -> projectiles.move());
                projectilesToRemove(proList, list, layout, text, points);

                list.forEach(value -> {

                    if (value.collide(ship)) {

                        stop();

                    }
                });

                if (Math.random() < 0.005) {
                    Asteroids asteroid = new Asteroids(WIDTH, HEIGHT);
                    if (!asteroid.collide(ship)) {
                        list.add(asteroid);
                        layout.getChildren().add(asteroid.getCharacter());
                    }
                }

            }

        }.start();

        window.setTitle("Asteroids");
        window.setScene(view);
        window.show();

    }

    public static void main(String[] args) {
        launch(AsteroidsApplication.class);
    }

    public static int partsCompleted() {
        // State how many parts you have completed using the return value of this method
        return 4;
    }

    public ArrayList<Asteroids> addAsterods() {
        Random ran = new Random();
        ArrayList<Asteroids> list = new ArrayList<>();
        int i = 0;
        while (i < 8) {

            list.add(new Asteroids(ran.nextInt(WIDTH / 3), ran.nextInt(HEIGHT)));

            i++;
        }

        return list;
    }

    public void projectilesToRemove(ArrayList<Projectiles> projectileList, ArrayList<Asteroids> asteroidList, Pane layout, Text text, AtomicInteger points) {

        projectileList.forEach(projectiles -> {

            asteroidList.forEach(asteroid -> {

                if (projectiles.collide(asteroid)) {
                    projectiles.setAlive(false);
                    asteroid.setAlive(false);
                    text.setText("Points: " + points.addAndGet(100));

                }

            });

        });

        projectileList.stream().
                filter(projectile -> !projectile.isAlive())
                .forEach(projectiles -> layout.getChildren()
                .remove(projectiles.getCharacter()));

        projectileList.removeAll(projectileList.stream()
                .filter(projectiles -> !projectiles.isAlive()).
                collect(Collectors.toList()));

        asteroidList.stream()
                .filter(asteroid -> !asteroid.isAlive())
                .forEach(asteroids -> layout.getChildren()
                .remove(asteroids.getCharacter()));

        asteroidList.removeAll(asteroidList.stream()
                .filter(asteroid -> !asteroid.isAlive())
                .collect(Collectors.toList()));

    }

}
