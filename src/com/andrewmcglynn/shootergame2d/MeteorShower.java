package com.andrewmcglynn.shootergame2d;

public class MeteorShower extends Actor {
    private int asteroidCount;
    private int counter = 0;

    public MeteorShower(Stage stage) {
        super(stage);

        for (int i = 0; i < 5; i++) {
            Asteroid a = new Asteroid(stage);
            a.setX((int) (Math.random() * Stage.WIDTH));
            a.setY(i * 20);
            stage.addActor(a);
            asteroidCount++;
            setSpriteNames(new String[]{"atom.gif"});
            setY(-100);
        }

    }

    //this is a non graphical actor
    public void act() {
        super.act();
        counter++;
        if (asteroidCount <= Stage.MAX_METEORS_PER_LEVEL) {
            if (((int) (Math.random()) % 30) == (counter % 30)) {
                Asteroid a = new Asteroid(stage);
                a.setX((int) (Math.random() * Stage.WIDTH + Stage.WIDTH / 2));
                a.setY(0);
                stage.addActor(a);
                asteroidCount++;

            }

        } else {

            ExtraLife e = new ExtraLife(stage);
            e.setX((int) (Math.random() * Stage.WIDTH));
            stage.addActor(e);

            this.remove();
        }
    }
}
