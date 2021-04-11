package br.com.demonhunter.entities;

import br.com.demonhunter.entities.attack.Attack;
import br.com.demonhunter.graphics.Camera;
import br.com.demonhunter.main.Game;

import java.awt.*;

public class Enemy extends Entity {

    private int speed = 1;
    //STATUS
    private int life = 50;

    private int hitDamage = 0, hitDx = 0, hitDy = 0;

    private boolean invunerable = false, receivedHit = false;

    private int invunerableFrames = 0, invunerableFramesMax = 10;

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setSprite(Game.spriteManager.enemySpriteSheet.getSprite(0, 0, 32, 32));
        switch (Game.difficulty) {
            case "NORMAL":
                break;
            case "HARD":
                life = 100;
                break;
            case "DEMONHUNTER":
                life = 200;
                break;
        }
    }

    @Override
    public void tick() {
        if (life <= 0) {
            Game.entities.remove(this);
        }
        if (Math.abs(this.getX() - Game.player.getX()) < 100 || Math.abs(this.getY() - Game.player.getY()) < 100) {
            if (Game.player.getX() < this.getX()
                    && Game.world.isFree(this.getX() - speed, this.getY())) {
                this.setX(this.getX() - speed);
            } else if (Game.player.getX() > this.getX()
                    && Game.world.isFree(this.getX() + speed, this.getY())
                    || (invunerable && hitDx < 0)) {
                this.setX(this.getX() + speed);
            }
            if (Game.player.getY() < this.getY()
                    && Game.world.isFree(this.getX(), this.getY() - speed)) {
                this.setY(this.getY() - speed);
            } else if (Game.player.getY() > this.getY()
                    && Game.world.isFree(this.getX(), this.getY() + speed)) {
                this.setY(this.getY() + speed);
            }
        }
        if (receivedHit) {
            System.out.println("receivedHit");
            changeLife(-hitDamage);
            hitDamage = 0;
            receivedHit = false;
            invunerable = true;
        }

        if (invunerable) {
            System.out.println("invunerable");
            int movementX = this.hitDx * 8 * speed;
            int movementY = this.hitDy * 8 * speed;
            if (Game.world.isFree(this.getX() + movementX, this.getY() + movementY)) {
                this.setX(this.getX() + movementX);
                this.setY(this.getY() + movementY);
            }

            if (++invunerableFrames >= invunerableFramesMax) {
                invunerable = false;
                invunerableFrames = 0;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.drawString("" + life + "", this.getX() - Camera.x, this.getY() - Camera.y);
    }

    public void changeLife(int lifePoints) {
        life += lifePoints;
    }

    @Override
    public boolean receiveCollision(Attack attack) {
        if (!invunerable) {
            receivedHit = true;
            hitDamage = attack.damage;
            hitDx = attack.dx;
            hitDy = attack.dy;
        }
        return true;
    }

}
