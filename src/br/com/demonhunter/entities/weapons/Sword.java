package br.com.demonhunter.entities.weapons;

import br.com.demonhunter.entities.Entity;
import br.com.demonhunter.entities.attack.Attack;
import br.com.demonhunter.entities.attack.melee.SwordAttack;
import br.com.demonhunter.main.Game;

public class Sword extends Weapon {

    public Sword(int x, int y) {
        super(x, y, 32, 32, Game.spriteManager.objectSpriteSheet.getSprite(0, 32, 32, 32));
        this.stopOnAttack = true;
        this.attacks.put(0, new SwordAttack(0, 0, 0, 0, 8, 8));
    }

    @Override
    public void attack(int x, int y, int dx, int dy, int attackId, Entity owner) {
        Attack attack = this.attacks.get(attackId).getInstance(x, y, dx, dy, 32, 32);
        attack.owner = owner;
        attack.dx = dy;
        attack.dy = -dx;
        attack.setX(x + 20*dx - 30*dy);
        attack.setY(y + 20*dy + 30*dx);
        Game.entities.add(attack);
    }

    @Override
    public Weapon getInstance(int x, int y) {
        return new Sword(x, y);
    }
}
