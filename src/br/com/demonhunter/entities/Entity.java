package br.com.demonhunter.entities;

import br.com.demonhunter.entities.attack.Attack;
import br.com.demonhunter.graphics.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    private int x, y, width, height;
    private BufferedImage sprite;

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

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

    public boolean receiveCollision(Attack attack) {
        return false;
    }

    public void render(Graphics g) {
        if (this.sprite != null) {
            g.drawImage(this.sprite, this.getX() + ((32 - this.getWidth()) / 2) - Camera.x, this.getY() + ((32 - this.getHeight()) / 2) - Camera.y, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x - Camera.x, y - Camera.y, width, height);
        }
    }

}
