package br.com.demonhunter.main.screens;

import br.com.demonhunter.components.GameTextField;
import br.com.demonhunter.main.Game;

import java.awt.*;

public class Register {

    public static int unitHeight = Game.HEIGHT / 100, unitWidth = Game.WIDTH / 100;

    private final GameTextField nameTextField;
    private final GameTextField ageTextField;
    private GameTextField focusedTextField;

    public Register() {
        this.nameTextField = new GameTextField(50, 50, 300, 25);
        this.ageTextField = new GameTextField(50, 100, 300, 25);
    }

    public void onClick(int x, int y) {
        this.nameTextField.focused = false;
        this.ageTextField.focused = false;
        if (x > 50 && x < 350) {
            if (y >= 50 && y <= 75) {
                this.nameTextField.focused = true;
                this.focusedTextField = this.nameTextField;
                this.focusedTextField = this.nameTextField;
                return;
            } else if (y >= 100 && y <= 125) {
                this.ageTextField.focused = true;
                this.focusedTextField = this.ageTextField;
                return;
            }
        }
        this.focusedTextField = null;

    }

    public void render(Graphics graphics) {

        //ADDING THE TRANSPARENT SCREEN BEHIND THE BUTTONS
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(new Color(0, 0, 0, 100));
        graphics2D.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

        this.nameTextField.render(graphics);
        this.ageTextField.render(graphics);
    }

    public void onKeyPressed(String key) {

        if (this.focusedTextField == null) {
            return;
        }

        switch (key) {
            case "Backspace" -> this.focusedTextField.removeChar();
            case "Space" -> this.focusedTextField.addSpace();
            default -> this.focusedTextField.addString(key);
        }
    }

}
