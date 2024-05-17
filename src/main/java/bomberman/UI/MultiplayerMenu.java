package bomberman.UI;

import bomberman.Game.GameEngine;
import bomberman.Game.GameState;
import bomberman.Game.StateMethods;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static bomberman.Game.Constants.GAME_WIDTH;
import static bomberman.UI.Page.*;

public class MultiplayerMenu implements StateMethods {
    private MenuGUI menuGUI;
    private MenuButton back,gamemode,start,join;
    public MultiplayerMenu(MenuGUI menuGUI){
        this.menuGUI=menuGUI;
        initButtons();
    }

    private void initButtons() {
        back=new MenuButton(GAME_WIDTH-150,70,0.15f, GameState.state,"Assets/back.png");
        gamemode=new MenuButton(0,50, 0.2f,GameState.state,"Assets/gamemodes.png");
        join=new MenuButton(70,400,0.17f,GameState.state,"Assets/nljoin.png","Assets/join.png");
        start=new MenuButton(GAME_WIDTH-70-300,400,0.17f,GameState.state,"Assets/nlserver.png","Assets/server.png");


    }

    @Override
    public void render(Graphics g) {
        back.render(g);
        gamemode.render(g);
        join.render(g);
        start.render(g);
    }

    @Override
    public void update() {
        join.update();
        start.update();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e,back)){
            page=GAMEMODE;
        }
        if (isIn(e,start)){
            GameEngine.gameEngine.multiplayer=true;
            page=MAP;

/*
            GameEngine.gameEngine.serverhost=true;
            GameEngine.gameEngine.multiplayerSetup(0,"dick");
            menuGUI.startGame();
*/
        }
        if (isIn(e,join)){
            GameEngine.gameEngine.serverhost=false;
            GameEngine.gameEngine.multiplayerSetup(0,"rider",-1);
            menuGUI.joinGame();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isIn(e,join)){
            join.setMouseEntered(true);
        } else if (!isIn(e,join)) {
            join.setMouseEntered(false);
        }
        if (isIn(e,start)){
            start.setMouseEntered(true);
        } else if (!isIn(e,start)) {
            start.setMouseEntered(false);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    private boolean isIn(MouseEvent e, MenuButton b) {
        return b.getBounds().contains(e.getX(),e.getY());
    }

}
