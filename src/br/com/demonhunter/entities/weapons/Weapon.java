package br.com.demonhunter.entities.weapons;

import br.com.demonhunter.entities.Entity;
import br.com.demonhunter.entities.attack.Attack;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Weapon extends Entity {

    public Map<Integer, Attack> attacks;

    public Weapon(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.attacks = new HashMap<>();
    }

    public void attack(int x, int y, int dx, int dy, int attackId, Entity owner) {

    }

    public Weapon getInstance(int x, int y) {
        return null;
    }

}
