package bomberman.UI;

import bomberman.Game.GameState;
import bomberman.Game.StateMethods;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MainMenu implements StateMethods {
    MenuButton play,settings,quit,logo;
    public MainMenu(){
        initButtons();
    }

    private void initButtons() {
        logo=new MenuButton(10,0.3f, GameState.state,"Assets/logo.png");
    }

    @Override
    public void render(Graphics g) {
        logo.render(g);
    }

    @Override
    public void update() {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void MousePressed(MouseEvent e) {

    }
}
