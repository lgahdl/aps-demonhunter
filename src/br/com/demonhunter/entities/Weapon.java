package br.com.demonhunter.entities;

import br.com.demonhunter.graphics.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Weapon extends Entity {


    public Weapon(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(this.getX() - Camera.x, this.getY()-Camera.y, this.getWidth(), this.getHeight());
    }

}
