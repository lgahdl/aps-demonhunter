package br.com.demonhunter.entities.weapons;

import br.com.demonhunter.entities.Entity;
import br.com.demonhunter.entities.attack.Attack;
import br.com.demonhunter.entities.attack.magic.Fireball;
import br.com.demonhunter.main.Game;

public class Staff extends Weapon {

    public Staff(int x, int y) {
        super(x, y, 32, 32, Game.spriteManager.objectSpriteSheet.getSprite(0, 64, 32, 32));
        this.attacks.put(0, new Fireball(0, 0, 0, 0, 8, 8));
    }

    @Override
    public void attack(int x, int y, int dx, int dy, int attackId, Entity owner) {
        Attack attack = this.attacks.get(attackId).getInstance(x, y, dx, dy, 32, 32);
        attack.owner = owner;
        attack.dx = dx;
        attack.dy = dy;
        attack.setX(x);
        attack.setY(y);
        Game.entities.add(attack);
    }

    @Override
    public Weapon getInstance(int x, int y) {
        return new Staff(x, y);
    }

}
