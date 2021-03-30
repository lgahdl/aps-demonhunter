package br.com.demonhunter.graphics;

public class SpriteManager {

    public SpriteSheet enemySpriteSheet;
    public SpriteSheet tileSpriteSheet;
    public SpriteSheet playerSpriteSheet;
    public SpriteSheet objectSpriteSheet;

    public SpriteManager(String enemySpriteSheetPath,
                         String tileSpriteSheetPath,
                         String playerSpriteSheetPath,
                         String objectSpriteSheetPath) {
        this.enemySpriteSheet = new SpriteSheet(enemySpriteSheetPath);
        this.tileSpriteSheet = new SpriteSheet(tileSpriteSheetPath);
        this.playerSpriteSheet = new SpriteSheet(playerSpriteSheetPath);
        this.objectSpriteSheet = new SpriteSheet(objectSpriteSheetPath);
    }

}
