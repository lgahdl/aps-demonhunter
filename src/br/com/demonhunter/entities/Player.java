package br.com.demonhunter.entities;

import br.com.demonhunter.entities.weapons.Weapon;
import br.com.demonhunter.graphics.Camera;
import br.com.demonhunter.main.Game;
import br.com.demonhunter.world.World;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    public boolean right, up, left, down;

    public String lastPressedMovementKey = "down";

    public Weapon weapon = null;

    public boolean moved = false;

    private int frames = 0, index = 0;

    public static int speed = 2;

    private Map<String, BufferedImage[]> sprites;

    private BufferedImage[] leftPlayer;

    private BufferedImage[] rightPlayer;

    private BufferedImage[] upPlayer;


    public Player(int x, int y, int width, int height) {

        super(x, y, width, height);
        this.sprites = new HashMap<>();
        this.sprites.put("up", new BufferedImage[8]);
        this.sprites.put("down", new BufferedImage[8]);
        this.sprites.put("left", new BufferedImage[8]);
        this.sprites.put("right", new BufferedImage[8]);

        for (int i = 0; i < 8; i++) {
            this.sprites.get("down")[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 0, 32, 32);
            this.sprites.get("right")[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 32, 32, 32);
            this.sprites.get("left")[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 64, 32, 32);
            this.sprites.get("up")[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 96, 32, 32);
        }

    }

    @Override
    public void tick() {
        if (!moved) {
            index = 0;
        }
        if (this.up && Game.world.isFree(this.getX(), this.getY() - speed)) {
            this.setY(this.getY() - speed);
            this.lastPressedMovementKey = "up";
            this.moved = true;
        } else if (this.down && Game.world.isFree(this.getX(), this.getY() + speed)) {
            this.setY(this.getY() + speed);
            this.lastPressedMovementKey = "down";
            this.moved = true;
        }
        if (this.right && Game.world.isFree(this.getX() + speed, this.getY())) {
            this.setX(this.getX() + speed);
            this.lastPressedMovementKey = "right";
            this.moved = true;
        } else if (this.left && Game.world.isFree(this.getX() - speed, this.getY())) {
            this.setX(this.getX() - speed);
            this.lastPressedMovementKey = "left";
            this.moved = true;
        }

        if (this.moved) {
            frames++;
            int maxFrames = 7;
            if (frames >= maxFrames) {
                frames = 0;
                index++;
                int maxIndex = 7;
                if (index == maxIndex) {
                    index = 0;
                }
            }
        }
        this.setSprite(this.sprites.get(lastPressedMovementKey)[index]);
        checkCollisionWeapon();
        setCamera();
    }

    public void setCamera() {
        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 32 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 32 - Game.HEIGHT);
    }

    public void checkCollisionWeapon() {
        for (int i = 0; i < Game.weapons.size(); i++) {
            Weapon atual = Game.weapons.get(i);
            if (isColidding(this, atual)) {
                this.weapon = atual;
                Game.weapons.remove(atual);
            }
        }
    }


    public void onKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            this.right = true;
            this.left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            this.left = true;
            this.right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            this.up = true;
            this.down = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            this.down = true;
            this.up = false;
        }
    }

    public void onKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            this.right = false;
            this.moved = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            this.left = false;
            this.moved = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            this.up = false;
            this.moved = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            this.down = false;
            this.moved = false;
        }
    }

}
