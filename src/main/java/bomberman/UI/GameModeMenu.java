package bomberman.UI;

import bomberman.Game.GameEngine;
import bomberman.Game.GameState;
import bomberman.Game.StateMethods;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static bomberman.Game.Constants.GAME_WIDTH;
import static bomberman.UI.Page.*;

public class GameModeMenu implements StateMethods {
    private MenuGUI menuGUI;
    private MenuButton back,multiplayer,singleplayer,gamemode;
    public GameModeMenu (MenuGUI menuGUI){
        this.menuGUI=menuGUI;
        initButtons();
    }

    private void initButtons() {
        gamemode=new MenuButton(0,50, 0.2f,GameState.state,"Assets/gamemodes.png");
        singleplayer=new MenuButton(70,400,0.17f,GameState.state,"Assets/singleplayer.png","Assets/singleplayerline.png");
        back=new MenuButton(GAME_WIDTH-150,70,0.15f,GameState.state,"Assets/back.png");
        multiplayer=new MenuButton(GAME_WIDTH-70-300,400,0.17f,GameState.state,"Assets/multiplayer.png","Assets/multiplayerline.png");

    }

    @Override
    public void render(Graphics g) {
        gamemode.render(g);
        singleplayer.render(g);
        back.render(g);
        multiplayer.render(g);
    }

    @Override
    public void update() {
        singleplayer.update();
        multiplayer.update();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e,singleplayer)){
            GameEngine.gameEngine.multiplayerSetup(1,"");
            menuGUI.startGame();
        }
        if (isIn(e,back)){
            page=MAINMENU;
        }
        if (isIn(e,multiplayer)){
            page=MULTIPLAYER;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isIn(e,singleplayer)){
            singleplayer.setMouseEntered(true);
        } else if (!isIn(e,singleplayer)) {
            singleplayer.setMouseEntered(false);
        }
        if (isIn(e,multiplayer)){
            multiplayer.setMouseEntered(true);
        } else if (!isIn(e,multiplayer)) {
            multiplayer.setMouseEntered(false);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private boolean isIn(MouseEvent e, MenuButton b) {
        return b.getBounds().contains(e.getX(),e.getY());
    }

}
