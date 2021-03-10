package br.com.demonhunter.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    private int x, y, width, height;
    private BufferedImage sprite;

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public static boolean isColidding(Entity e1, Entity e2) {
        Rectangle rect1 = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
        Rectangle rect2 = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());
        return rect1.intersects(rect2);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.fillRect(x, y, width, height);
    }

}