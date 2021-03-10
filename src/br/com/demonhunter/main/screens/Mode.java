package br.com.demonhunter.main.screens;

import br.com.demonhunter.main.Game;

import java.awt.*;

public class Mode implements Screen{

    public Mode() {

    }

    public void render(Graphics graphics) {
        graphics.setColor(new Color(15, 15, 15));
        graphics.fillRect(50, 50, 300, 25);
        graphics.setColor(Color.WHITE);
        graphics.drawString("CAMPAIGN", 177, 65);

        graphics.setColor(new Color(15, 15, 15));
        graphics.fillRect(50, 100, 300, 25);
        graphics.setColor(Color.WHITE);
        graphics.drawString("ENDLESS", 185, 115);
    }

    public void onClick(int x, int y) {
        if (x > 50 && x < 350) {
            if (y >= 50 && y <= 75) {
                setGameMode("CAMPAIGN");
                return;
            } else if (y >= 100 && y <= 125) {
                setGameMode("ENDLESS");
                return;
            }
        }

    }

    private void setGameMode(String mode) {
        Game.mode = mode;
        System.out.println("THE GAME MODE IS NOW: " + mode);
        Game.state = "PLAYING";
    }
}
