package br.com.demonhunter.main;

import br.com.demonhunter.entities.Entity;
import br.com.demonhunter.entities.Player;
import br.com.demonhunter.entities.weapons.Weapon;
import br.com.demonhunter.graphics.SpriteManager;
import br.com.demonhunter.main.screens.Difficulty;
import br.com.demonhunter.main.screens.Menu;
import br.com.demonhunter.main.screens.Mode;
import br.com.demonhunter.main.screens.Register;
import br.com.demonhunter.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    private Thread thread;
    private boolean isRunning;
    private static BufferedImage image;

    //Window Params
    public final static int WIDTH = 420;
    public final static int HEIGHT = 237;
    public final static int SCALE = 3;

    //Game params
    public static String state;
    public static String difficulty;
    public static String mode;

    //Graphics
    public static SpriteManager spriteManager;

    //World
    public static World world;
    private int curLevel = 1;

    //Entities
    public static Player player;
    public static List<Entity> entities;
    public static List<Weapon> weapons;

    //Screens
    public Register registerScreen;
    public Menu menuScreen;
    public Difficulty difficultyScreen;
    public Mode modeScreen;


    public Game() {

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        initFrame();


        //Inicialização de variáveis da classe Game
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        spriteManager = new SpriteManager(
                "/enemy.png",
                "/tile.png",
                "/player.png",
                "/object.png");
        state = "REGISTER";
        difficultyScreen = new Difficulty();
        registerScreen = new Register();
        menuScreen = new Menu();
        modeScreen = new Mode();
        entities = new ArrayList<>();
        weapons = new ArrayList<>();
        player = new Player(0, 0, 32, 32);
        entities.add(player);
        weapons.add(new Weapon(128, 128, 16, 16, null));
        world = new World("map" + curLevel + ".png");

    }

    private void initFrame() {
        frame = new JFrame("Game");
        frame.add(this);
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public void tick() {
        player.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();


        g.setColor(new Color(240, 80, 80));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //RENDERIZAÇÃO DAS ENTIDADES ABAIXO!!!

        world.render(g);

        switch (state) {
            case "REGISTER":
                registerScreen.render(g);
                break;
            case "MENU":
                menuScreen.render(g);
                break;
            case "DIFFICULTY":
                difficultyScreen.render(g);
                break;
            case "MODE":
                modeScreen.render(g);
                break;
        }
        if (state.equals("PLAYING")) {
            for (Entity e : entities) {
                e.render(g);
            }
            for (Weapon w : weapons) {
                w.render(g);
            }
        }

        //FIM DA RENDERIZAÇÃO DAS ENTIDADES
        g.dispose();

        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        bs.show();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        @SuppressWarnings("unused")
        int frames = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
//				System.out.println(frames);
                frames = 0;
                timer += 1000;
            }
        }

        stop();
    }

    public synchronized void stop() {

        isRunning = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void exitGame() {
        System.exit(1);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (state == "REGISTER") {
            registerScreen.onKeyPressed(e.getKeyText(e.getKeyCode()));
        } else if (state == "PLAYING") {
            player.onKeyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (state == "PLAYING") {
            player.onKeyReleased(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = (e.getX() / 3);
        int my = (e.getY() / 3);
        switch (state) {
            case "REGISTER":
                this.registerScreen.onClick(mx, my);
                break;
            case "MENU":
                this.menuScreen.onClick(mx, my);
                break;
            case "DIFFICULTY":
                this.difficultyScreen.onClick(mx, my);
                break;
            case "MODE":
                this.modeScreen.onClick(mx, my);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
