package br.com.demonhunter.components

import java.awt.*;


public class GameTextField {

    public boolean focused = false;
    public boolean activeCaret = false;

    private int frames = 0;

    private final int x, y, width, height;

    private final int maxLength;

    private String text;

    public GameTextField(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = "";
        this.maxLength = 15;
    }

    public String getText() {
        return this.text;
    }

    public void render(Graphics g) {
        activateCaret();
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.width, this.height);
        String textToRender;
        if (this.activeCaret && this.focused) {
            textToRender = this.text + "|";
        } else {
            textToRender = this.text;
        }
        g.setColor(Color.BLACK);
        g.drawString(textToRender, this.x + 5, this.y + 20);
    }

    public void addString(String c) {
        if (c.length() == 1 && this.text.length() < this.maxLength) {
            this.text = this.text + c;
        }
    }

    public void removeChar() {
        if (this.text.length() > 0) {
            this.text = this.text.substring(0, this.text.length() - 1);
        }
    }

    public void addSpace() {
        this.text = this.text + " ";
    }

    public void activateCaret() {
        this.frames++;
        if (this.frames >= 30) {
            this.activeCaret = !this.activeCaret;
            this.frames = 0;
        }
    }

}
