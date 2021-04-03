package br.com.demonhunter.entities.magics;

import java.awt.*;

public class Fireball extends Magic {

    public static final int cooldown=2, damage=5, manaCost=2;

    public Fireball(int x, int y, int dx, int dy, int width, int height) {
        super(x, y, dx, dy, width, height, cooldown, damage, manaCost, null);
    }

}
