package br.com.demonhunter.entities.magics;

import br.com.demonhunter.entities.Entity;
import br.com.demonhunter.graphics.Camera;
import br.com.demonhunter.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Magic extends Entity {

    public int cooldown, damage, manaCost, dx, dy;

    public Magic(int x, int y, int dx, int dy, int width, int height, int cooldown, int damage, int manaCost, BufferedImage sprite) {
        super(x, y, width, height);
        this.cooldown = cooldown;
        this.damage = damage;
        this.manaCost = manaCost;
        this.setSprite(sprite);
    }

    public Magic(int x, int y, int dx, int dy, int width, int height, int cooldown, int damage, int manaCost) {
        super(x, y, width, height);
        this.cooldown = cooldown;
        this.damage = damage;
        this.manaCost = manaCost;
    }

    @Override
    public void tick() {
        Game.entities.forEach(entity -> {
            if (isColidding(this, entity)) {
                System.out.println("colliding");
            }
        });
        if (Game.world.isFree(this.getX() + this.dx, this.getY() + this.dy)) {
            this.setX(this.getX() + this.dx);
            this.setY(this.getY() + this.dy);
        } else {
            Game.entities.remove(this);
        }
    }

    public void render(Graphics g) {
        if (this.getSprite() != null) {
            g.drawImage(this.getSprite(), this.getX(), this.getY(), null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, 32, 32);
        }
    }

}
