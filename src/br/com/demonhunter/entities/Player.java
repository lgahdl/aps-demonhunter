package br.com.demonhunter.entities;

import br.com.demonhunter.graphics.Camera;
import br.com.demonhunter.main.Game;
import br.com.demonhunter.world.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean right, up, left, down;

    public String lastPressedMovementKey = "down";

    public boolean haveWeapon;

    public boolean moved = false;

    private int frames = 0, index = 0;

    public static int speed = 2;

    private BufferedImage[] downPlayer;

    private BufferedImage[] leftPlayer;

    private BufferedImage[] rightPlayer;

    private BufferedImage[] upPlayer;


    public Player(int x, int y, int width, int height) {

        super(x, y, width, height, null);

        downPlayer = new BufferedImage[8];
        leftPlayer = new BufferedImage[8];
        rightPlayer = new BufferedImage[8];
        upPlayer = new BufferedImage[8];

        for (int i = 0; i < 8; i++) {
            downPlayer[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 0, 32, 32);
            rightPlayer[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 32, 32, 32);
            leftPlayer[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 64, 32, 32);
            upPlayer[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 96, 32, 32);
        }

    }

    @Override
    public void tick() {
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

        checkCollisionWeapon();
    }

//    public void setCamera() {
//        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, Game.world.WIDTH * 32 - Game.WIDTH);
//        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, Game.world.HEIGHT * 32 - Game.HEIGHT);
//    }

    @Override
    public void render(Graphics g) {
        int positionX = this.getX() - Camera.x;
        int positionY = this.getY() - Camera.y;
        switch (lastPressedMovementKey) {
            case "right":
                if (right) {
                    g.drawImage(rightPlayer[index], positionX, positionY, null);
                } else {
                    g.drawImage(rightPlayer[0], positionX, positionY, null);
                }
                break;
            case "left":
                if (left) {
                    g.drawImage(leftPlayer[index], positionX, positionY, null);
                } else {
                    g.drawImage(leftPlayer[0], positionX, positionY, null);
                }
                break;
            case "up":
                if (up) {
                    g.drawImage(upPlayer[index], positionX, positionY, null);
                } else {
                    g.drawImage(upPlayer[0], positionX, positionY, null);
                }
                break;
            case "down":
                if (down) {
                    g.drawImage(downPlayer[index], positionX, positionY, null);
                } else {
                    g.drawImage(downPlayer[0], positionX, positionY, null);
                }
                break;
            default:
                break;
        }
        setCamera();
    }

    public void setCamera() {
        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 32 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 32 - Game.HEIGHT);
    }

    public void checkCollisionWeapon() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity atual = Game.entities.get(i);
            if (atual instanceof Weapon) {
                if (Entity.isColidding(this, atual)) {
                    this.haveWeapon = true;
                    Game.entities.remove(atual);
                }
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
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            this.left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            this.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            this.down = false;
        }
    }

}
