package br.com.demonhunter.entities.attack.melee;

import br.com.demonhunter.entities.attack.Attack;
import br.com.demonhunter.entities.attack.magic.Fireball;

import java.awt.image.BufferedImage;

public class SwordAttack extends Attack {

    public static final int cooldown = 2, damage = 5, manaCost = 0;

    public SwordAttack(int x, int y, int dx, int dy, int width, int height) {
        super(x, y, dx, dy, width, height, cooldown, damage, manaCost);
    }

    @Override
    public Attack getInstance(int x, int y, int dx, int dy, int width, int height) {
        return new SwordAttack(x, y, dx, dy, width, height);
    }

}
