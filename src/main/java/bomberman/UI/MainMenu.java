package bomberman.UI;

import bomberman.Game.GameState;
import bomberman.Game.StateMethods;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static bomberman.UI.Page.GAMEMODE;
import static bomberman.UI.Page.page;

public class MainMenu implements StateMethods {
    MenuGUI menuGUI;
    MenuButton play,settings,quit,logo;
    public MainMenu(MenuGUI menuGUI){
        this.menuGUI=menuGUI;
        initButtons();
    }

    private void initButtons() {
        logo=new MenuButton(10,0.3f, GameState.state,"Assets/logo.png");
        play=new MenuButton(400,0.3f,GameState.state,"Assets/play.png");
    }

    @Override
    public void render(Graphics g) {
        logo.render(g);
        play.render(g);
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
    public void mousePressed(MouseEvent e) {
        if (isIn(e,play)){
            page=GAMEMODE;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private boolean isIn(MouseEvent e, MenuButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
