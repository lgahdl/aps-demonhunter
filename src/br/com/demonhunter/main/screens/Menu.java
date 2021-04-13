package br.com.demonhunter.main.screens;

import br.com.demonhunter.main.Game;

import java.awt.*;

public class Menu implements Screen {

    public Menu() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(15, 15, 15));
        g.fillRect(50, 10, 300, 25);
        g.setColor(Color.WHITE);
        g.drawString("PLAY", 185, 25);

        g.setColor(new Color(15, 15, 15));
        g.fillRect(50, 60, 300, 25);
        g.setColor(Color.WHITE);
        g.drawString("LOAD", 185, 75);
        if (Game.state != "PAUSE") {
            g.setColor(new Color(15, 15, 15));
            g.fillRect(50, 110, 300, 25);
            g.setColor(Color.WHITE);
            g.drawString("SELECT DIFFICULTY: " + Game.difficulty, 85, 125);

            g.setColor(new Color(15, 15, 15));
            g.fillRect(50, 160, 300, 25);
            g.setColor(Color.WHITE);
            g.drawString("SELECT MODE: " + Game.mode, 95, 175);
        }

        g.setColor(new Color(15, 15, 15));
        g.fillRect(50, Game.state != "PAUSE" ? 210 : 110, 300, 25);
        g.setColor(Color.WHITE);
        g.drawString("QUIT", 185, Game.state != "PAUSE" ? 225 : 125);

    }

    @Override
    public void onClick(int x, int y) {
        if (x > 50 && x < 350) {
            if (y >= 10 && y <= 35) {
                play();
                return;
            } else if (y >= 60 && y <= 85) {
                load();
                return;
            } else if (y >= 110 && y <= 135 && Game.state != "PAUSE") {
                Game.state = "DIFFICULTY";
            } else if (y >= 160 && y <= 185 && Game.state != "PAUSE") {
                Game.state = "MODE";
            } else if (y >= 210 && y <= 235) {
                Game.exitGame();
            } else if (y >= 110 && y <= 135) {
                Game.exitGame();
            }
        }

    }

    private void load() {
        System.out.println("loading game");
    }

    private void play() {
        Game.startWorld();
        Game.state = "PLAYING";
    }


}
