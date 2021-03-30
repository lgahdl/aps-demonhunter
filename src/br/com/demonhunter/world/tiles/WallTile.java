package br.com.demonhunter.world.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WallTile extends Tile{
    public WallTile(int x, int y) {
        super(x, y, null);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(this.getX(), this.getY(), 32, 32);
    }

}
