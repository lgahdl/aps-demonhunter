package br.com.demonhunter.main.screens;


import br.com.demonhunter.main.Game;

import java.awt.*;

public class Difficulty implements Screen {

    public Difficulty() {

    }

    public void render(Graphics graphics) {
        graphics.setColor(new Color(15, 15, 15));
        graphics.fillRect(50, 50, 300, 25);
        graphics.setColor(Color.WHITE);
        graphics.drawString("NORMAL", 177, 65);

        graphics.setColor(new Color(15, 15, 15));
        graphics.fillRect(50, 100, 300, 25);
        graphics.setColor(Color.WHITE);
        graphics.drawString("HARD", 185, 115);

        graphics.setColor(new Color(15, 15, 15));
        graphics.fillRect(50, 150, 300, 25);
        graphics.setColor(Color.WHITE);
        graphics.drawString("DEMON HUNTER", 155, 165);
    }

    public void onClick(int x, int y) {
        if (x > 50 && x < 350) {
            if (y >= 50 && y <= 75) {
                setDifficulty("NORMAL");
                return;
            } else if (y >= 100 && y <= 125) {
                setDifficulty("HARD");
                return;
            } else if (y >= 150 && y <= 175) {
                setDifficulty("DEMONHUNTER");
            }
        }
    }

    private void setDifficulty(String difficulty) {
        Game.difficulty = difficulty;
        Game.state = "MODE";
    }
}
