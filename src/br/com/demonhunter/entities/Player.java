package br.com.demonhunter.entities;

import br.com.demonhunter.entities.attack.Attack;
import br.com.demonhunter.entities.weapons.Weapon;
import br.com.demonhunter.graphics.Camera;
import br.com.demonhunter.main.Game;
import br.com.demonhunter.world.World;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    //ATTACK
    public Weapon weapon = null;
    public boolean attacked = false, isAttacking = false;
    public int attackFrames = 0;
    public int attackId = 0;

    //STATUS
    private int life = 100, maxLife = 100, mana = 100, maxMana = 100;
    public boolean isDead = false;

    //MOVEMENT
    public boolean right, up, left, down;
    public String lastPressedMovementKey = "down";
    public boolean moved = false;
    private int frames = 0, index = 0;
    public static int speed = 1;

    //RENDERING
    private Map<String, BufferedImage[]> sprites;


    public Player(int x, int y, int width, int height) {

        super(x, y, width, height);
        sprites = new HashMap<>();
        sprites.put("up", new BufferedImage[8]);
        sprites.put("down", new BufferedImage[8]);
        sprites.put("left", new BufferedImage[8]);
        sprites.put("right", new BufferedImage[8]);

        for (int i = 0; i < 8; i++) {
            sprites.get("down")[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 0, 32, 32);
            sprites.get("right")[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 32, 32, 32);
            sprites.get("left")[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 64, 32, 32);
            sprites.get("up")[i] = Game.spriteManager.playerSpriteSheet.getSprite(32 * i, 96, 32, 32);
        }

    }

    @Override
    public void tick() {
        if (!moved) {
            index = 0;
        }
        if (up && Game.world.isFree(getX(), getY() - speed)) {
            setY(getY() - speed);
            lastPressedMovementKey = "up";
            moved = true;
        } else if (down && Game.world.isFree(getX(), getY() + speed)) {
            setY(getY() + speed);
            lastPressedMovementKey = "down";
            moved = true;
        }
        if (right && Game.world.isFree(getX() + speed, getY())) {
            setX(getX() + speed);
            lastPressedMovementKey = "right";
            moved = true;
        } else if (left && Game.world.isFree(getX() - speed, getY())) {
            setX(getX() - speed);
            lastPressedMovementKey = "left";
            moved = true;
        }

        if (attacked) {
            isAttacking = true;
            attacked = false;
            int dx = left ? -1 : right ? 1 : 0;
            int dy = up ? -1 : down ? 1 : 0;
            if (dx == 0) {
                dx = this.lastPressedMovementKey.equals("left") ? -1 : this.lastPressedMovementKey.equals("right") ? 1 : 0;
            }
            if (dy == 0) {
                dy = this.lastPressedMovementKey.equals("up") ? -1 : this.lastPressedMovementKey.equals("down") ? 1 : 0;
            }
            if (this.changeMana(weapon.attacks.get(attackId).manaCost)) {
                weapon.attack(getX(), getY(), dx, dy, attackId, this);
            }
        }

        if (isAttacking) {
            attackFrames++;
            Attack attack = weapon.attacks.get(attackId);
            if (attack != null) {
                if (attackFrames >= weapon.attacks.get(attackId).cooldown) {
                    isAttacking = false;
                }
            } else {
                isAttacking = false;
            }
        }

        if (moved) {
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
        setSprite(sprites.get(lastPressedMovementKey)[index]);
        checkCollisionWeapon();
        setCamera();
    }

    public void setCamera() {
        Camera.x = Camera.clamp(getX() - (Game.WIDTH / 2), 0, World.WIDTH * 32 - Game.WIDTH);
        Camera.y = Camera.clamp(getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 32 - Game.HEIGHT);
    }

    public void checkCollisionWeapon() {
        for (int i = 0; i < Game.weapons.size(); i++) {
            Weapon atual = Game.weapons.get(i);
            if (isColidding(this, atual)) {
                if (this.weapon != null) {
                    Game.weapons.add(this.weapon.getInstance(this.getX() + 64, this.getY() + 32));
                }
                weapon = atual;
                Game.weapons.remove(atual);
            }
        }
    }

    public void onKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_J && !isAttacking) {
            attacked = true;
            attackId = 0;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = true;
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            left = true;
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = true;
            down = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            down = true;
            up = false;
        }
    }

    public void onKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = false;
            moved = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            left = false;
            moved = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = false;
            moved = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            down = false;
            moved = false;
        }
    }

    public void stopMovement() {
        up = false;
        right = false;
        down = false;
        left = false;
    }

    public void changeLife(int lifePoints) {
        life += lifePoints;
        if (life > maxLife) {
            life = maxLife;
        } else if (life <= 0) {
            this.isDead = true;
        }
    }

    public boolean changeMana(int manaPoints) {
        int currentMana = mana;
        mana += manaPoints;
        if (mana < 0) {
            mana = currentMana;
            return false;
        } else if (mana > maxMana) {
            mana = maxMana;
        }
        return true;
    }

    @Override
    public void receiveCollision(Attack attack) {
        this.changeLife(-attack.damage);
    }

}
