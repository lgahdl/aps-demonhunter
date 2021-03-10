package br.com.demonhunter.entities;

import br.com.demonhunter.main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean right, up, left, down;

    public boolean haveWeapon;

    public static int speed = 2;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    @Override
    public void tick() {
        if (this.up) {
            this.setY(this.getY() - speed);
        } else if (this.down) {
            this.setY(this.getY() + speed);
        }
        if (this.right) {
            this.setX(this.getX() + speed);
        } else if (this.left) {
            this.setX(this.getX() - speed);
        }
        checkCollisionWeapon();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        if (this.haveWeapon) {
            g.setColor(Color.GREEN);
            g.fillRect(this.getX() + 8, this.getY() + 8, 8, 8);
        }
    }


    public void checkCollisionWeapon() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity atual = Game.entities.get(i);
            if (atual instanceof Weapon) {
                if (Entity.isColidding(this, atual)) {
                    this.haveWeapon = true;
                    Game.entities.remove(atual);
                }
            }
        }
    }

    public void onKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            this.right = true;
            this.left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            this.left = true;
            this.right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            this.up = true;
            this.down = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            this.down = true;
            this.up = false;
        }
    }

    public void onKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            this.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            this.left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            this.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            this.down = false;
        }
    }

}
