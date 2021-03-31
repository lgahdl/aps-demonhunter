package br.com.demonhunter.world.tiles;

import br.com.demonhunter.graphics.Camera;
import br.com.demonhunter.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CastleFloorTile extends FloorTile {

    public CastleFloorTile(int x, int y) {
        super(x, y);
        this.setSprite(Game.spriteManager.tileSpriteSheet.getSprite(128,192,32,32));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.getSprite(), this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}
