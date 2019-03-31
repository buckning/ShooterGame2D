package com.andrewmcglynn.shootergame2d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShooterGame2D extends Canvas implements Stage, KeyListener {

    private BufferStrategy strategy;
    private long usedTime;
    private BufferedImage ocean;
    private int t;
    DisplayMode displayMode = new DisplayMode(Stage.WIDTH, Stage.HEIGHT, 32, 60);
    final GraphicsDevice device;
    private SpriteCache spriteCache;
    private SoundCache soundCache;
    private ArrayList actors;
    JFrame ventana;
    private Player player;
    private boolean gameEnded = false;
    private boolean isPaused = false;
    private boolean gameStarted = false;
    private boolean onBonusLevel = false;
    private int numOfBoss = 0;

    public boolean isOnBonusLevel() {
        return onBonusLevel;
    }

    public void setOnBonusLevel(boolean onBonusLevel) {
        this.onBonusLevel = onBonusLevel;
    }

    private int levelNumber = 1;

    public void gameOver() {
        gameEnded = true;
    }

    public ShooterGame2D() {
        spriteCache = new SpriteCache();
        soundCache = new SoundCache();

        ventana = new JFrame("invaders");
        JPanel panel = (JPanel) ventana.getContentPane();
        setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
        panel.setPreferredSize(new Dimension(Stage.WIDTH, Stage.HEIGHT));
        panel.setLayout(null);
        panel.add(this);
        ventana.setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        requestFocus();

        addKeyListener(this);
//		 get the GraphicsDevice
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
//		device.setFullScreenWindow(ventana);
//		device.setDisplayMode(displayMode);
        ventana.setResizable(false);
    }

    public void initWorld() {


        actors = new ArrayList();

        player = new Player(this);
        player.setX(Stage.WIDTH / 2);
        player.setY(Stage.PLAYER_YPOSITION);

        //soundCache.loopSound("Use Your Head.midi");
        //create the lives icon at the top of the screen
        String[] animationIcon = new String[20];
        for (int i = 0; i < 20; i++) {
            animationIcon[i] = "jimIcon" + i + ".gif";
        }
        NonInteractiveGraphic livesIcon = new NonInteractiveGraphic(this);
        livesIcon.setSpriteNames(animationIcon);
        livesIcon.setX(0);
        livesIcon.setY(0);
        livesIcon.setFrameSpeed(10);
        actors.add(livesIcon);
    }

    public SoundCache getSoundCache() {
        return soundCache;
    }

    public void addActor(Actor a) {
        actors.add(a);
    }

    public void updateWorld() {
        int i = 0;
        while (i < actors.size()) {
            Actor m = (Actor) actors.get(i);
            //remove them from game is selected
            if (m.isMarkedForRemoval()) {
                actors.remove(i);
            } else {
                //let the actors perform their tasks
                m.act();
                i++;
            }
        }
        if (!onBonusLevel) {
            if (actors.size() <= 1) {
                levelNumber++;

                switch (levelNumber) {
                    case 2: {
                        if (numOfBoss >= 4) {
                            gameOver();
                        }

                        paintNextLevelScreen();
                        gameStarted = false;
                        while (!gameStarted) {
                        }
                        if (numOfBoss == 2) {
                            initStage1();
                            soundCache.loopSound("Anything But Tangerines Remix.midi");

                        }

                        initStage2();

                    }
                    break;
                    case 3: {
                        if (numOfBoss == 2) {
                            initStage6();
                        }
                        initStage3();
                    }
                    break;
                    case 4: {
                        if (numOfBoss == 2) {
                            initStage2();
                            initStage1();
                        }
                        initStage4();
                    }
                    break;
                    case 5: {
                        initStage5();
                        if (numOfBoss++ == 2) {
                            initStage2();
                            initStage6();
                        }
                    }
                    break;
                    case 6: {
                        soundCache.loopSound("What the Heck.midi");
                        if (numOfBoss++ == 1) {
                            Wolverine w = new Wolverine(this);
                            actors.add(w);

                        } else {
                            initStage6();
                            Wolverine w = new Wolverine(this);
                            actors.add(w);
                            initStage6();
                        }

                    }
                    break;
                    case 7: {
                        paintBonusScreen();
                        onBonusLevel = true;
                        gameStarted = false;
                        while (!gameStarted) {
                        }
                        soundCache.loopSound("Game Show Inflated Head.midi");

                        for (int j = 0; j < 200; j++) {
                            Asteroid a = new Asteroid(this);
                            a.setX(Stage.WIDTH - 20 + (int) (Math.random() * 6500));
                            a.setY((int) (Math.random() * Stage.HEIGHT));
                            a.setVy(0);
                            actors.add(a);
                        }

                        for (int j = 0; j < 50; j++) {
                            Powerup a = new Powerup(this);
                            a.setX(Stage.WIDTH - 20 + (int) (Math.random() * 6500));
                            a.setY((int) (Math.random() * Stage.HEIGHT));
                            a.setVy(0);
                            a.setVx(-4);
                            actors.add(a);
                        }
                        ExtraLife a = new ExtraLife(this);
                        a.setX(Stage.WIDTH - 20 + (int) (Math.random() * 6500));
                        a.setY((int) (Math.random() * Stage.PLAYER_YPOSITION - 30));
                        a.setVy(0);
                        a.setVx(-4);
                        actors.add(a);

                        player.setCurrentFrame(0);
                        player.setSpriteNames(new String[]{"playerRocket.gif"});


                    }
                    break;
                }
            }
        } else {
            if (actors.size() <= 1) {
                onBonusLevel = false;
                levelNumber = 0;
                player.setCurrentFrame(0);
                player.setSpriteNames(new String[]{"jimIdle0.gif"});
                player.setY(PLAYER_YPOSITION);
                player.setX(Stage.WIDTH / 2);
                player.setVy(0);

            }
        }
        player.act();
    }

    public void displayGameFinish() {
        Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();
        BufferedImage titleScreen = spriteCache.getSprite("titleScreen.gif");
        g2d.setPaint(new TexturePaint(titleScreen, new Rectangle(0, 0, getWidth(), getHeight())));
        g2d.fillRect(0, 0, getWidth(), getHeight() + 200);

        g2d.setColor(Color.CYAN);
        g2d.setFont(new Font("Arial", 100, 100));
        g2d.drawString("Game Cleared", 50, Stage.HEIGHT / 4);
        g2d.setFont(new Font("Arial", 50, 50));
        g2d.drawString("Your Score is: " + (player.getScore() + player.getNoOfLives() * 10000 + player.getShields() * 10000), Stage.WIDTH / 6, Stage.HEIGHT / 2);

        strategy.show();
    }

    public Player getPlayer() {
        return player;
    }

    public void checkCollisions() {
        Rectangle playerBounds = player.getBounds();

        for (int i = 0; i < actors.size(); i++) {
            Actor a1 = (Actor) actors.get(i);
            Rectangle r1 = a1.getBounds();
            //check player collision with monster
            if (r1.intersects(playerBounds)) {
                player.collision(a1);
                a1.collision(player);
            }
            //check actor collision off other actor(not player)
            for (int j = i + 1; j < actors.size(); j++) {
                Actor a2 = (Actor) actors.get(j);
                if (a2 != null) {
                    Rectangle r2 = a2.getBounds();
                    if (r1.intersects(r2)) {
                        a1.collision(a2);
                        a2.collision(a1);
                    }
                }
            }
        }
    }

    public void paintWorld() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        ocean = spriteCache.getSprite("TheStarfield.png");

        if (!onBonusLevel) {
            g.setPaint(new TexturePaint(ocean, new Rectangle(-2 * t, 0, ocean.getWidth(), ocean.getHeight())));
            g.fillRect(0, 0, getWidth(), getHeight());

            ocean = spriteCache.getSprite("base1.gif");
            g.setPaint(new TexturePaint(ocean, new Rectangle(0, 0, ocean.getWidth(), ocean.getHeight() + 50)));
            g.fillRect(0, 0, getWidth(), getHeight() + 200);
        } else {
            g.setPaint(new TexturePaint(ocean, new Rectangle(-10 * t, 0, ocean.getWidth(), ocean.getHeight())));
            g.fillRect(0, 0, getWidth(), getHeight());

        }

        //add all actors to screen
        for (int i = 0; i < actors.size(); i++) {
            Actor m = (Actor) actors.get(i);
            m.paint(g);
        }
        player.paint(g);
        paintStatus(g);
        strategy.show();
    }

    public void paintShields(Graphics2D g) {
        g.setPaint(Color.red);
        g.fillRect(280, Stage.PLAY_HEIGHT, Player.MAX_SHIELDS, 30);
        g.setPaint(Color.blue);
        g.fillRect(280 + Player.MAX_SHIELDS - player.getShields(), Stage.PLAY_HEIGHT, player.getShields(), 30);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setPaint(Color.green);
        g.drawString("Shields", 170, Stage.PLAY_HEIGHT + 20);
    }

    public void paintScore(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setPaint(Color.green);
        g.drawString("Score", 20, Stage.PLAY_HEIGHT + 20);

        g.setPaint(Color.red);
        g.drawString(player.getScore() + "", 100, Stage.PLAY_HEIGHT + 20);
    }

    public void paintfps(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.white);
        if (usedTime > 0)
            g.drawString(String.valueOf(1000 / usedTime) + " fps", Stage.WIDTH - 50, Stage.PLAY_HEIGHT);
        else
            g.drawString("--- fps", Stage.WIDTH - 50, Stage.PLAY_HEIGHT);
    }

    public void paintStatus(Graphics2D g) {
        paintScore(g);
        paintShields(g);
        //	paintfps(g);
        g.drawString("X " + player.getNoOfLives(), 50, 60);
    }


    public void initStage1() {
        TruckShooter ts = new TruckShooter(this);
        ts.setX(-20);
        actors.add(ts);
        ts.setY((int) (PLAY_HEIGHT - ts.getHeight() * 1.5));

        TruckShooter ts3 = new TruckShooter(this);
        ts3.setX(Stage.WIDTH + 197);
        actors.add(ts3);
        ts3.setY((int) (PLAY_HEIGHT - ts.getHeight() * 1.5));
        ts3.setVx(-1);
    }

    public void initStage2() {
        for (int i = 0; i < 5; i++) {
            Bomber h = new Bomber(this);
            actors.add(h);
            h.setY(h.getY() - (i * 200));
            h.setX(h.getX() - i * 10);
            if (i % 2 == 1) {
                h.setX(Stage.WIDTH - h.getWidth() - i * 7);
                h.setVx(-3);
            }
        }

    }

    public void initStage3() {
        MeteorShower m = new MeteorShower(this);
        actors.add(m);

    }

    public void initStage4() {
        for (int i = 0; i < 10; i++) {
            PersonnelCarrier h = new PersonnelCarrier(this);
            actors.add(h);
            h.setY(h.getY() - (i * 200));
            h.setX(h.getX() - i * 10);
        }
    }

    public void initStage5() {
        MeteorShower m = new MeteorShower(this);
        actors.add(m);
        for (int i = 0; i < 5; i++) {
            PersonnelCarrier h = new PersonnelCarrier(this);
            actors.add(h);
            h.setY(h.getY() - (i * 200));
            h.setX(h.getX() - i * 10);
        }
    }

    public void initStage6() {
        Wraith w = new Wraith(this);
        actors.add(w);
        w.setX(Stage.WIDTH / 3);

    }


    public void game() {
        usedTime = 1000;
        t = 0;
        paintTitleScreen();

        initWorld();

        while (!gameStarted) {
            paintTitleScreen();
        }
        onBonusLevel = false;
        soundCache.loopSound("Anything But Tangerines Remix.midi");

        for (int i = 0; i < 15; i++) {
            Monster m = new Monster(this);
            m.setX((int) (Math.random() * (Stage.WIDTH - 100)));
            m.setY(i * 20);
            m.setVx((int) (Math.random() * 20 - 10));

            actors.add(m);
        }
        while (isVisible() && !gameEnded) {
            if (!isPaused) {
                t++;
                long startTime = System.currentTimeMillis();
                updateWorld();
                checkCollisions();
                paintWorld();
                usedTime = System.currentTimeMillis() - startTime;
                try {
                    Thread.sleep(SPEED);
                } catch (Exception e) {
                    System.out.println("sleep failed");//do nothing
                }
            } else {
                Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
                g.setColor(Color.white);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Paused", Stage.WIDTH / 2 - 50, Stage.HEIGHT / 2);
                strategy.show();

            }
        }

        if (player.getNoOfLives() > 0 || player.getShields() > 0) displayGameFinish();
        else while (true) {
            paintGameOver();
        }
    }

    public static void main(String args[]) {
        ShooterGame2D inv = new ShooterGame2D();
        inv.game();
    }

    public SpriteCache getSpriteCache() {
        return spriteCache;
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) device.setFullScreenWindow(null);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) gameStarted = true;
        if (e.getKeyCode() == KeyEvent.VK_F2) {
            device.setFullScreenWindow(ventana);
            device.setDisplayMode(displayMode);
            isPaused = true;

            Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();
            BufferedImage titleScreen = spriteCache.getSprite("titleScreen.gif");
            g2d.setPaint(new TexturePaint(titleScreen, new Rectangle(0, 0, getWidth(), getHeight())));
            g2d.fillRect(0, 0, getWidth(), getHeight() + 200);

            g2d.setColor(Color.CYAN);
            g2d.drawString("Pause: P", 0, Stage.HEIGHT * 2 / 3 + 120);

            strategy.show();

        }
        if (e.getKeyCode() == KeyEvent.VK_P) isPaused = !isPaused;
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void paintGameOver() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Game Over", Stage.WIDTH / 2 - 50, Stage.HEIGHT / 2);
        strategy.show();
    }

    public int getLevel() {
        return levelNumber;
    }

    public void setLevel(int i) {
        levelNumber = i;
    }

    public int getNumOfActors() {
        return actors.size();
    }


    public void paintTitleScreen() {
        Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();
        BufferedImage titleScreen = spriteCache.getSprite("titleScreen.gif");
        g2d.setPaint(new TexturePaint(titleScreen, new Rectangle(0, 0, getWidth(), getHeight())));
        g2d.fillRect(0, 0, getWidth(), getHeight() + 200);
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString("Stage 1: Practise", 250, Stage.HEIGHT / 10);
        g2d.drawString("Shoot Everything!!!", 250, Stage.HEIGHT * 3 / 10);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Controls", 0, Stage.HEIGHT * 2 / 3);
        g2d.setColor(Color.CYAN);
        g2d.drawString("Move: Directional Keys", 0, Stage.HEIGHT * 2 / 3 + 20);
        g2d.drawString("Shoot: SPACE", 0, Stage.HEIGHT * 2 / 3 + 40);
        g2d.drawString("Jump: CTRL", 0, Stage.HEIGHT * 2 / 3 + 60);
        g2d.drawString("Make Full Screen: F2", 0, Stage.HEIGHT * 2 / 3 + 80);
        g2d.drawString("Exit Full Screen: ESC", 0, Stage.HEIGHT * 2 / 3 + 100);
        g2d.drawString("Pause: P", 0, Stage.HEIGHT * 2 / 3 + 120);
        g2d.setColor(Color.RED);
        g2d.drawString("Start: ENTER", Stage.WIDTH / 3, Stage.HEIGHT - 30);


        strategy.show();

    }

    public void paintBonusScreen() {
        Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();
        BufferedImage titleScreen = spriteCache.getSprite("titleScreen.gif");
        g2d.setPaint(new TexturePaint(titleScreen, new Rectangle(0, 0, getWidth(), getHeight())));
        g2d.fillRect(0, 0, getWidth(), getHeight() + 200);
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString("Bonus Stage", 250, Stage.HEIGHT / 10);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Controls", 0, Stage.HEIGHT * 2 / 3);
        g2d.setColor(Color.CYAN);
        g2d.drawString("Move: Directional Keys", 0, Stage.HEIGHT * 2 / 3 + 20);
        g2d.drawString("Shoot: SPACE", 0, Stage.HEIGHT * 2 / 3 + 40);
        g2d.drawString("Jump: CTRL", 0, Stage.HEIGHT * 2 / 3 + 60);
        g2d.drawString("Make Full Screen: F2", 0, Stage.HEIGHT * 2 / 3 + 80);
        g2d.drawString("Exit Full Screen: ESC", 0, Stage.HEIGHT * 2 / 3 + 100);
        g2d.drawString("Pause: P", 0, Stage.HEIGHT * 2 / 3 + 120);
        g2d.setColor(Color.RED);
        g2d.drawString("Start: ENTER", Stage.WIDTH / 3, Stage.HEIGHT - 30);

        strategy.show();

    }

    public void paintNextLevelScreen() {
        Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();
        BufferedImage titleScreen = spriteCache.getSprite("titleScreen.gif");
        g2d.setPaint(new TexturePaint(titleScreen, new Rectangle(0, 0, getWidth(), getHeight())));
        g2d.fillRect(0, 0, getWidth(), getHeight() + 200);

        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString("Level Finished", 250, Stage.HEIGHT / 10);


        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Controls", 0, Stage.HEIGHT * 2 / 3);
        g2d.setColor(Color.CYAN);
        g2d.drawString("Move: Directional Keys", 0, Stage.HEIGHT * 2 / 3 + 20);
        g2d.drawString("Shoot: SPACE", 0, Stage.HEIGHT * 2 / 3 + 40);
        g2d.drawString("Jump: CTRL", 0, Stage.HEIGHT * 2 / 3 + 60);
        g2d.drawString("Make Full Screen: F2", 0, Stage.HEIGHT * 2 / 3 + 80);
        g2d.drawString("Exit Full Screen: ESC", 0, Stage.HEIGHT * 2 / 3 + 100);
        g2d.drawString("Pause: P", 0, Stage.HEIGHT * 2 / 3 + 120);
        g2d.setColor(Color.RED);
        g2d.drawString("Start: ENTER", Stage.WIDTH / 3, Stage.HEIGHT - 30);

        strategy.show();

    }
}