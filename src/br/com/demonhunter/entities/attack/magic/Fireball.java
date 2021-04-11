package br.com.demonhunter.entities.attack.magic;

import br.com.demonhunter.entities.Entity;
import br.com.demonhunter.entities.attack.Attack;
import br.com.demonhunter.graphics.Camera;
import br.com.demonhunter.main.Game;

import java.awt.*;

public class Fireball extends Attack {

    public static final int cooldown = 60, damage = 20, manaCost = 10;

    private int speed = 5;

    public Fireball(int x, int y, int dx, int dy, int width, int height) {
        super(x, y, dx, dy, width, height, cooldown, damage, manaCost, null);
    }

    @Override
    public void tick() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity entity = Game.entities.get(i);
            if (isColidding(this, entity) && !entity.equals(this) && !entity.equals(owner)) {
                boolean received = entity.receiveCollision(this);
                if (received) {
                    Game.entities.remove(this);
                }
            }
        }
        if (Game.world.isFree(this.getX() + this.dx, this.getY() + this.dy)) {
            this.setX(this.getX() + this.dx * speed);
            this.setY(this.getY() + this.dy * speed);
        } else {
            Game.entities.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(this.getX() + 12 - Camera.x, this.getY() + 12 - Camera.y, 8, 8);
    }

    @Override
    public Attack getInstance(int x, int y, int dx, int dy, int width, int height) {
        return new Fireball(x, y, dx, dy, width, height);
    }

}
