package br.com.demonhunter.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Weapon extends Entity {


    public Weapon(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

}
