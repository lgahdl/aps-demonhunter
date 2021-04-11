package br.com.demonhunter.entities.attack.melee;

import br.com.demonhunter.entities.Entity;
import br.com.demonhunter.entities.attack.Attack;
import br.com.demonhunter.graphics.Camera;
import br.com.demonhunter.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SwordAttack extends Attack {

    public static final int cooldown = 30, damage = 10, manaCost = 0;

    private int speed = 2, countdownToDestroy = 0;

    private BufferedImage[] sprites;

    public SwordAttack(int x, int y, int dx, int dy, int width, int height) {
        super(x, y, dx, dy, width, height, cooldown, damage, manaCost);
        this.sprites = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            sprites[i] = Game.spriteManager.objectSpriteSheet.getSprite(32 + 32 * i, 32, 32, 32);
        }
        ;
    }

    @Override
    public void tick() {

        if (++countdownToDestroy >= cooldown) {
            Game.entities.remove(this);
        }

        for (int i = 0; i < Game.entities.size(); i++) {
            Entity entity = Game.entities.get(i);
            if (isColidding(this, entity) && !entity.equals(this) && !entity.equals(owner)) {
                entity.receiveCollision(this);
            }
        }

        BufferedImage sprite = this.sprites[Math.min(3, countdownToDestroy / (cooldown / 4))];

        this.setSprite(sprite);
        this.setX(this.getX() + this.dx * speed);
        this.setY(this.getY() + this.dy * speed);
    }

    @Override
    public Attack getInstance(int x, int y, int dx, int dy, int width, int height) {
        return new SwordAttack(x, y, dx, dy, width, height);
    }

    @Override
    public void render(Graphics g) {
        int positionX = this.getX() - Camera.x;
        int positionY = this.getY() - Camera.y;
        Graphics2D g2 = (Graphics2D) g;
        int angle = dy * 90 - dx * (90 - 90 * dx);
        g2.rotate(Math.toRadians(angle), positionX + 16, positionY + 16);
        g.drawImage(this.getSprite(), positionX, positionY, null);
        g2.rotate(-Math.toRadians(angle), positionX + 16, positionY + 16);
    }

}
