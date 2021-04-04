package br.com.demonhunter.entities.objects;

import br.com.demonhunter.entities.Entity;
import br.com.demonhunter.main.Game;

public class ManaPotion extends Entity {

    public ManaPotion(int x, int y, int width, int height) {
        super(x, y, width, height, Game.spriteManager.objectSpriteSheet.getSprite(0, 0, 32, 32));
    }

}
