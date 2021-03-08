package br.com.demonhunter.main.screens;

import java.awt.Color;
import java.awt.Graphics;

import br.com.demonhunter.main.Game;

public class Difficulty {
	
	public static int unitHeight = Game.getHEIGHT() / 100, unitWidth = Game.getWIDTH() / 100;
	
	public Difficulty() {
		
	}

    public void render(Graphics g) {
		g.setColor(new Color(135, 135, 100));
		g.fillRect(25 * unitWidth, 35 * unitHeight, 50 * unitWidth, 15 * unitHeight);
		g.setColor(Color.BLACK);
		g.drawString("NORMAL", 45 * unitWidth, 47 * unitHeight);

		g.setColor(new Color(135, 135, 100));
		g.fillRect(25 * unitWidth, 55 * unitHeight, 50 * unitWidth, 15 * unitHeight);
		g.setColor(Color.BLACK);
		g.drawString("HARD", 45 * unitWidth, 67 * unitHeight);

		g.setColor(new Color(135, 135, 100));
		g.fillRect(25 * unitWidth, 75 * unitHeight, 50 * unitWidth, 15 * unitHeight);
		g.setColor(Color.BLACK);
		g.drawString("DEMON HUNTER", 37 * unitWidth, 87 * unitHeight);
    }
    
    public void onClick(int x, int y){
        x = x / Game.SCALE;
        y = y / Game.SCALE;
        System.out.println(x + "  " + y);
    }
}
