package br.com.demonhunter.entities.weapons;

import br.com.demonhunter.entities.magics.Fireball;
import br.com.demonhunter.entities.magics.Magic;
import br.com.demonhunter.main.Game;

import java.awt.image.BufferedImage;
import java.util.List;

public class Staff extends Weapon {

    public List<Magic> magics;
    public Magic selectedMagic;

    public Staff(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.selectedMagic = new Fireball(0, 0, 0, 0, 0, 0);
    }

    @Override
    public void attack(int x, int y, int dx, int dy) {
        this.selectedMagic.dx = dx;
        this.selectedMagic.dy = dy;
        this.selectedMagic.setX(x);
        this.selectedMagic.setY(y);
        Game.entities.add(selectedMagic);
    }

}
