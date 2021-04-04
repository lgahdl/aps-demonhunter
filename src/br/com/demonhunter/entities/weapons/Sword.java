package br.com.demonhunter.entities.weapons;

import br.com.demonhunter.entities.attack.melee.SwordAttack;
import br.com.demonhunter.main.Game;

public class Sword extends Weapon {

    public Sword(int x, int y) {
        super(x, y, 32, 32, Game.spriteManager.objectSpriteSheet.getSprite(0, 32, 32, 32));
        this.attacks.put(0, new SwordAttack(0, 0, 0, 0, 8, 8));
    }

    @Override
    public Weapon getInstance(int x, int y) {
        return new Sword(x, y);
    }
}
