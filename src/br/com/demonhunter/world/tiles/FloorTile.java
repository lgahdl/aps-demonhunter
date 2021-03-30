package br.com.demonhunter.world.tiles;

import br.com.demonhunter.graphics.Camera;

import java.awt.*;

public class FloorTile extends Tile {
    public FloorTile(int x, int y) {
        super(x, y, null);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, 32, 32);
    }

}
