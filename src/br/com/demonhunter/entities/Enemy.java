package br.com.demonhunter.entities;

import br.com.demonhunter.entities.attack.Attack;
import br.com.demonhunter.main.Game;

public class Enemy extends Entity {

    private int speed = 1;

    //STATUS
    private int life = 50;

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setSprite(Game.spriteManager.enemySpriteSheet.getSprite(0, 0, 32, 32));
    }

    @Override
    public void tick() {

        if (Math.abs(this.getX() - Game.player.getX()) < 100 || Math.abs(this.getY() - Game.player.getY()) < 100) {
            if (Game.player.getX() < this.getX() && Game.world.isFree(this.getX() - speed, this.getY())) {
                this.setX(this.getX() - speed);
            } else if (Game.player.getX() > this.getX() && Game.world.isFree(this.getX() + speed, this.getY())) {
                this.setX(this.getX() + speed);
            }
            if (Game.player.getY() < this.getY() && Game.world.isFree(this.getX(), this.getY() - speed)) {
                this.setY(this.getY() - speed);
            } else if (Game.player.getY() > this.getY() && Game.world.isFree(this.getX(), this.getY() + speed)) {
                this.setY(this.getY() + speed);
            }
        }

    }

    public void changeLife(int lifePoints) {
        this.life += lifePoints;
        if (life < 0) {
            Game.entities.remove(this);
        }
    }

    @Override
    public void receiveCollision(Attack attack) {
        this.changeLife(-attack.damage);
        Game.entities.remove(attack);
    }

}
