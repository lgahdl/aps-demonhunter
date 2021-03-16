package br.com.demonhunter.main.screens;

import br.com.demonhunter.components.GameTextField;
import br.com.demonhunter.main.Game;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Register implements Screen {

    public static int unitHeight = Game.HEIGHT / 100, unitWidth = Game.WIDTH / 100;

    private final GameTextField nameTextField;
    private final GameTextField ageTextField;
    private GameTextField focusedTextField;

    private boolean isUnderAge = false, isEmptyString = false;

    public Register() {
        this.nameTextField = new GameTextField(50, 50, 300, 25);
        this.ageTextField = new GameTextField(50, 100, 300, 25, "", 3);
        this.ageTextField.numberOnly = true;
    }

    public void onClick(int x, int y) {
        this.nameTextField.focused = false;
        this.ageTextField.focused = false;
        if (x > 50 && x < 350) {
            if (y >= 50 && y <= 75) {
                this.nameTextField.focused = true;
                this.focusedTextField = this.nameTextField;
                return;
            } else if (y >= 100 && y <= 125) {
                this.ageTextField.focused = true;
                this.focusedTextField = this.ageTextField;
                return;
            } else if (y >= 150 && y <= 175) {
                submit();
            }
        }
        this.focusedTextField = null;

    }

    private void submit() {
        String age = this.ageTextField.getText();
        String name = this.nameTextField.getText();
        isEmptyString = false;
        isUnderAge = false;

        if (age == "" || name == "") {
            isEmptyString = true;
            return;
        }

        if (Integer.parseInt(age) < 12) {
            isUnderAge = true;
            return;
        }

        createAccount(name, age);

        Game.state = "MENU";

    }

    private void createAccount(String name, String age) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File("accounts", name.replace(" ", "-") + ".txt")));
            writer.write("name: " + name);
            writer.newLine();
            writer.write("age: " + age);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics graphics) {

        //ADDING THE TRANSPARENT SCREEN BEHIND THE BUTTONS
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(new Color(0, 0, 0, 100));
        graphics2D.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);


        graphics.setColor(Color.BLACK);
        graphics.drawString("NAME", 50, 48);
        this.nameTextField.render(graphics);

        graphics.setColor(Color.BLACK);
        graphics.drawString("AGE", 50, 98);
        this.ageTextField.render(graphics);

        graphics.setColor(new Color(60, 130, 50));
        graphics.fillRect(50, 150, 300, 25);
        graphics.setColor(Color.BLACK);
        graphics.drawString("SUBMIT", 175, 165);

        if (isUnderAge) {
            graphics.drawString("YOU NEED TO BE OLDER TO PLAY THIS GAME", 50, 195);
        }

        if (isEmptyString) {
            graphics.drawString("YOU HAVE TO TYPE YOUR NAME AND AGE TO CONTINUE", 50, 195);
        }

    }

    public void onKeyPressed(String key) {

        if (this.focusedTextField == null) {
            return;
        }

        switch (key) {
            case "Backspace":
                this.focusedTextField.removeChar();
                break;
            case "Space":
                this.focusedTextField.addSpace();
                break;
            default:
                this.focusedTextField.addString(key);
                break;
        }
    }

}
