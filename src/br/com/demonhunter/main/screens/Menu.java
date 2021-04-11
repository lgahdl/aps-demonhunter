package br.com.demonhunter.main.screens;

import br.com.demonhunter.main.Game;

import java.awt.*;

public class Menu implements Screen {

    public Menu() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(15, 15, 15));
        g.fillRect(50, 50, 300, 25);
        g.setColor(Color.WHITE);
        g.drawString("PLAY", 185, 65);

        g.setColor(new Color(15, 15, 15));
        g.fillRect(50, 100, 300, 25);
        g.setColor(Color.WHITE);
        g.drawString("LOAD", 185, 115);

        g.setColor(new Color(15, 15, 15));
        g.fillRect(50, 150, 300, 25);
        g.setColor(Color.WHITE);
        g.drawString("QUIT", 185, 165);
    }

    @Override
    public void onClick(int x, int y) {
        if (x > 50 && x < 350) {
            if (y >= 50 && y <= 75) {
                play();
                return;
            } else if (y >= 100 && y <= 125) {
                load();
                return;
            } else if (y >= 150 && y <= 175) {
                Game.exitGame();
            }
        }

    }

    private void load() {
        System.out.println("loading game");
    }

    private void play() {
        if (Game.state == "MENU") {
            Game.state = "DIFFICULTY";
        } else {
            Game.state = "PLAYING";
        }
    }



}
