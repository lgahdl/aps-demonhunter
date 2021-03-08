package br.com.demonhunter.main;

import br.com.demonhunter.main.screens.Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    private Thread thread;
    private boolean isRunning;
    private static BufferedImage image;

    public final static int WIDTH = 420;
    public final static int HEIGHT = 237;
    public final static int SCALE = 3;

    public String gameState;

    public Register registerScreen;

    public Game() {

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        initFrame();

        //Inicialização de variáveis da classe Game
        gameState = "REGISTER";
        registerScreen = new Register();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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

    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();


        g.setColor(new Color(25, 10, 100));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //RENDERIZAÇÃO DAS ENTIDADES ABAIXO!!!
        if (gameState.equals("REGISTER")) {
            registerScreen.render(g);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == "REGISTER") {
            registerScreen.onKeyPressed(e.getKeyText(e.getKeyCode()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = (e.getX() / 3);
        int my = (e.getY() / 3);
        if (this.gameState.equals("REGISTER")) {
            this.registerScreen.onClick(mx, my);
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
