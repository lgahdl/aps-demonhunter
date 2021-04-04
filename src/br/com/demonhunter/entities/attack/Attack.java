package br.com.demonhunter.entities.attack;

import br.com.demonhunter.entities.Entity;
import br.com.demonhunter.entities.attack.melee.SwordAttack;

import java.awt.image.BufferedImage;

public abstract class Attack extends Entity {

    public int cooldown, damage, dx, dy, manaCost;

    public Entity owner;

    public Attack(int x, int y, int dx, int dy, int width, int height, int cooldown, int damage, int manaCost, BufferedImage sprite) {
        super(x, y, width, height);
        this.cooldown = cooldown;
        this.damage = damage;
        this.manaCost = manaCost;
        this.setSprite(sprite);
    }

    public Attack(Attack attack) {
        super(attack.getX(), attack.getY(), attack.getWidth(), attack.getHeight());
        this.cooldown = attack.cooldown;
        this.damage = attack.damage;
        this.manaCost = manaCost;
        this.setSprite(attack.getSprite());
    }

    public Attack(int x, int y, int dx, int dy, int width, int height, int cooldown, int damage, int manaCost) {
        super(x, y, width, height);
        this.cooldown = cooldown;
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public Attack getInstance(int x, int y, int dx, int dy, int width, int height) {
        return new SwordAttack(x, y, dx, dy, width, height);
    }

}
