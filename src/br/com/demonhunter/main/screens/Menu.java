package br.com.demonhunter.main.screens;

import br.com.demonhunter.main.Game;

import java.awt.*;

public class Menu implements Screen{

    public Menu() {

    }

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
        Game.state = "DIFFICULTY";
    }

    public void render(Graphics graphics) {
        graphics.setColor(new Color(15, 15, 15));
        graphics.fillRect(50, 50, 300, 25);
        graphics.setColor(Color.WHITE);
        graphics.drawString("PLAY", 185, 65);

        graphics.setColor(new Color(15, 15, 15));
        graphics.fillRect(50, 100, 300, 25);
        graphics.setColor(Color.WHITE);
        graphics.drawString("LOAD", 185, 115);

        graphics.setColor(new Color(15, 15, 15));
        graphics.fillRect(50, 150, 300, 25);
        graphics.setColor(Color.WHITE);
        graphics.drawString("QUIT", 185, 165);

    }


}
