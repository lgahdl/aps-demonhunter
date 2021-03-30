package br.com.demonhunter.world.tiles;

import br.com.demonhunter.graphics.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage sprite;
    private int x, y;

    public boolean show = true;

    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public BufferedImage getSprite() {
        return this.sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void render(Graphics g) {
        if (show) {
            g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
        }
    }

}
