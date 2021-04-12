package br.com.demonhunter.entities;

import br.com.demonhunter.entities.attack.Attack;
import br.com.demonhunter.entities.objects.LifePotion;
import br.com.demonhunter.entities.objects.ManaPotion;
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

    //DAMAGE
    private int hitDamage = 0, hitDx = 0, hitDy = 0;
    private boolean invunerable = false, receivedHit = false, throwBack = false;
    private int invunerableFrames = 0, invunerableFramesMax = 60, throwBackFrames = 0, throwBackFramesMax = 10;

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
        if (up && Game.world.isFree(getX(), getY() - speed)
                && !(weapon != null && weapon.stopOnAttack && isAttacking)) {
            setY(getY() - speed);
            lastPressedMovementKey = "up";
            moved = true;
        } else if (down && Game.world.isFree(getX(), getY() + speed)
                && !(weapon != null && weapon.stopOnAttack && isAttacking)) {
            setY(getY() + speed);
            lastPressedMovementKey = "down";
            moved = true;
        }
        if (right && Game.world.isFree(getX() + speed, getY())
                && !(weapon != null && weapon.stopOnAttack && isAttacking)) {
            setX(getX() + speed);
            lastPressedMovementKey = "right";
            moved = true;
        } else if (left && Game.world.isFree(getX() - speed, getY())
                && !(weapon != null && weapon.stopOnAttack && isAttacking)) {
            setX(getX() - speed);
            lastPressedMovementKey = "left";
            moved = true;
        }

        if (attacked) {
            attacked = false;
            if (this.changeMana(-weapon.attacks.get(attackId).manaCost)) {
                int dx = this.lastPressedMovementKey.equals("left") ? -1 : this.lastPressedMovementKey.equals("right") ? 1 : 0;
                int dy = this.lastPressedMovementKey.equals("up") ? -1 : this.lastPressedMovementKey.equals("down") ? 1 : 0;
                isAttacking = true;
                weapon.attack(getX(), getY(), dx, dy, attackId, this);
            }
        }

        if (isAttacking) {
            attackFrames++;
            Attack attack = weapon.attacks.get(attackId);
            if (attack != null) {
                if (attackFrames >= weapon.attacks.get(attackId).cooldown) {
                    isAttacking = false;
                    attackFrames = 0;
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

        if (receivedHit) {
            changeLife(-hitDamage);
            hitDamage = 0;
            receivedHit = false;
            invunerable = true;
            throwBack = true;
        }

        if (throwBack) {
            int movementX = this.hitDx * 4 * speed;
            int movementY = this.hitDy * 4 * speed;
            if (Game.world.isFree(this.getX() + movementX, this.getY() + movementY)) {
                this.setX(this.getX() + movementX);
                this.setY(this.getY() + movementY);
            }
            if (++throwBackFrames >= throwBackFramesMax) {
                throwBack = false;
                throwBackFrames = 0;
            }
        }

        if (invunerable) {
            if (++invunerableFrames >= invunerableFramesMax) {
                invunerable = false;
                invunerableFrames = 0;
            }
        }

        setSprite(sprites.get(lastPressedMovementKey)[index]);
        checkCollisionWeapon();
        if (!invunerable) {
            checkCollisionEnemy();
        }
        checkCollisionPotions();
        setCamera();
    }

    public void setCamera() {
        Camera.x = Camera.clamp(getX() - (Game.WIDTH / 2), 0, World.WIDTH * 32 - Game.WIDTH);
        Camera.y = Camera.clamp(getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 32 - Game.HEIGHT);
    }

    public void checkCollisionWeapon() {
        for (int i = 0; i < Game.weapons.size(); i++) {
            Weapon atual = Game.weapons.get(i);
            if (isColliding(this, atual)) {
                if (this.weapon != null) {
                    Game.weapons.add(this.weapon.getInstance(this.getX() + 64, this.getY() + 32));
                }
                weapon = atual;
                Game.weapons.remove(atual);
            }
        }
    }

    public void checkCollisionEnemy() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity atual = Game.entities.get(i);
            if (isColliding(this, atual)) {
                if (atual instanceof Enemy) {
                    receivedHit = true;
                    hitDamage = 10;
                    int diffX = getX() - atual.getX();
                    int diffY = getY() - atual.getY();
                    hitDx = Integer.compare(diffX, 0);
                    hitDy = Integer.compare(diffY, 0);
                }
            }
        }
    }

    public void checkCollisionPotions() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity atual = Game.entities.get(i);
            if (isColliding(this, atual)) {
                if (atual instanceof ManaPotion) {
                    if (changeMana(20)) {
                        Game.entities.remove(atual);
                    }
                }
                if (atual instanceof LifePotion) {
                    if (changeLife(20)) {
                        Game.entities.remove(atual);
                    }
                }
            }
        }
    }

    public void onKeyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_J && !isAttacking && this.weapon != null) {
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

    public boolean changeLife(int lifePoints) {
        life += lifePoints;
        if (life == maxLife + lifePoints && life > maxLife) {
            life = maxLife;
            return false;
        }
        if (life > maxLife) {
            life = maxLife;
        } else if (life <= 0) {
            life = 0;
            this.isDead = true;
        }
        return true;
    }

    public boolean changeMana(int manaPoints) {
        mana += manaPoints;
        if (mana == maxMana + manaPoints && mana > maxMana) {
            mana = maxMana;
            return false;
        }
        if (mana < 0) {
            mana = 0;
            return false;
        } else if (mana > maxMana) {
            mana = maxMana;
        }
        return true;
    }

    @Override
    public boolean receiveCollision(Attack attack) {
        this.changeLife(-attack.damage);
        return true;
    }

    public int getLife() {
        return life;

    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }
}
