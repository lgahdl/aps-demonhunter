package br.com.demonhunter.world;

import br.com.demonhunter.entities.Enemy;
import br.com.demonhunter.entities.objects.LifePotion;
import br.com.demonhunter.entities.objects.ManaPotion;
import br.com.demonhunter.entities.weapons.Staff;
import br.com.demonhunter.entities.weapons.Sword;
import br.com.demonhunter.graphics.Camera;
import br.com.demonhunter.main.Game;
import br.com.demonhunter.world.tiles.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    public static Tile[] tiles;
    public static int WIDTH;
    public static int HEIGHT;
    public static final int TILE_SIZE = 32;

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource("/maps/" + path));
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            int[] pixels = new int[WIDTH * HEIGHT];
            tiles = new Tile[pixels.length];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            for (int i = 0; i < map.getWidth(); i++) {
                for (int j = 0; j < map.getHeight(); j++) {
                    int pixelToAnalyze = pixels[i + (j * map.getWidth())];
                    tiles[i + (j * WIDTH)] = new CastleFloorTile(i * 32, j * 32);
                    switch (pixelToAnalyze) {
                        case 0xFF808080:
                            tiles[i + (j * WIDTH)] = new CastleFloorTile(i * 32, j * 32);
                            break;
                        case 0xFF000000:
                            tiles[i + (j * WIDTH)] = new CastleFloorTile(i * 32, j * 32);
                            break;
                        case 0xFFFFFFFF:
                            tiles[i + (j * WIDTH)] = new CastleWallTile(i * 32, j * 32);
                            // WALL
                            break;
                        case 0xFF0000FF: // AZUL
                            // PLAYER
                            Game.player.setX(i * 32);
                            Game.player.setY(j * 32);

                            break;
                        case 0xFFFF0000: // VERMELHO
                            // ENEMY
                            Game.entities.add(new Enemy(i * 32, j * 32, 32, 32));

                            break;
                        case 0xFF00FF00: // VERDE
                            //VIDA
                            Game.entities.add(new LifePotion(i * 32, j * 32, 32, 32));
                            break;
                        case 0xFF00FFFF: // CIANO
                            //MANA
                            Game.entities.add(new ManaPotion(i * 32, j * 32, 32, 32));
                            break;
                        case 0xFFFF8000: // LARANJA
                            //STAFF
                            Game.weapons.add(new Staff(i * 32, j * 32));
                            break;
                        case 0xFF666666: // AMARELO
                            //SWORD
                            Game.weapons.add(new Sword(i * 32, j * 32));
                            break;
                        default:
                            tiles[i + (j * WIDTH)] = new FloorTile(i * 32, j * 32);
                            // FLOOR
                            break;
                    }

                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isFree(int xNext, int yNext) {

        int x1 = xNext / TILE_SIZE;
        int y1 = yNext / TILE_SIZE;

        int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = yNext / TILE_SIZE;

        int x3 = xNext / TILE_SIZE;
        int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        return !(tiles[x1 + (y1 * WIDTH)] instanceof WallTile
                || tiles[x2 + (y2 * WIDTH)] instanceof WallTile
                || tiles[x3 + (y3 * WIDTH)] instanceof WallTile
                || tiles[x4 + (y4 * WIDTH)] instanceof WallTile);
    }

    public void render(Graphics g) {
        int xStart = Camera.x >> 5;
        int yStart = Camera.y >> 5;

        int xFinal = xStart + (Game.WIDTH >> 5) + 10;
        int yFinal = yStart + (Game.HEIGHT >> 5) + 10;
        for (int i = xStart; i < xFinal; i++) {
            for (int j = yStart; j <= yFinal; j++) {
                if (i < 0 || j < 0 || i >= WIDTH || j >= HEIGHT) {
                    continue;
                }
                try {
                    Tile tile = tiles[i + (j * WIDTH)];
                    tile.render(g);
                } catch (Exception ignored) {
                }
            }
        }
    }

}
