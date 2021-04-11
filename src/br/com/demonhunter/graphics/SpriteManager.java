package br.com.demonhunter.graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SpriteManager {

    public SpriteSheet enemySpriteSheet;
    public SpriteSheet tileSpriteSheet;
    public SpriteSheet playerSpriteSheet;
    public SpriteSheet objectSpriteSheet;

    public SpriteManager(String enemySpriteSheetPath,
                         String tileSpriteSheetPath,
                         String playerSpriteSheetPath,
                         String objectSpriteSheetPath) {
        this.enemySpriteSheet = new SpriteSheet(enemySpriteSheetPath);
        this.tileSpriteSheet = new SpriteSheet(tileSpriteSheetPath);
        this.playerSpriteSheet = new SpriteSheet(playerSpriteSheetPath);
        this.objectSpriteSheet = new SpriteSheet(objectSpriteSheetPath);
    }

    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();

        return rotated;
    }

}
