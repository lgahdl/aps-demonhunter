package br.com.demonhunter.graphics;

import br.com.demonhunter.main.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UI {

    public void render (Graphics g) {
        /*
        g.setColor(Color.red);
        g.fillRect(8,4,70,8);
        g.setColor(Color.green);
        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.PLAIN,8));
        g.drawString((int)Game.player.getLife()+"/"+(int)Game.player.getMaxLife(),30,11);
        */

        //Vida
        g.setColor(Color.red);
        g.fillRect(8,4,(int)((Game.player.getLife()/Game.player.getMaxLife())*70),8);
        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.PLAIN,8));
        g.drawString((int)Game.player.getLife()+"/"+(int)Game.player.getMaxLife(),30,11);

        //Mana

        g.setColor(Color.blue);
        g.fillRect(8,20,(int)((Game.player.getMana()/Game.player.getMaxMana())*70),8);
        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.PLAIN,8));
        g.drawString((int)Game.player.getMana()+"/"+(int)Game.player.getMaxMana(),30,27);

        //Mapa
    }

}
