package bomberman.UI;

import bomberman.Game.GameEngine;
import bomberman.Game.GameState;
import bomberman.Game.StateMethods;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static bomberman.Game.Constants.*;

public class MapSelector implements StateMethods {
    MenuGUI menuGUI;
    private float defaultscale=0.2f;
    private MenuButton srarrow,larrow,green,pink,cont;
    private ArrayList<MenuButton> maps;
    private int currentMapIndex;
    public MapSelector(MenuGUI menuGUI){
        this.menuGUI=menuGUI;
        initButtons();
        arrayinit();
    }

    private void arrayinit() {
        maps=new ArrayList<>();
        maps.add(green);
        maps.add(pink);
        currentMapIndex = 0;

    }

    private void initButtons() {
        srarrow = new MenuButton(GAME_WIDTH-300,GAME_HEIGHT/2,defaultscale, GameState.GAME,"Assets/srarrow.png");
        srarrow.setY(GAME_HEIGHT / 2 - (int)(srarrow.getImage().getHeight() * srarrow.getScale() / 2)+1);
        srarrow.setArrow(true);
        larrow=new MenuButton(250,GAME_HEIGHT/2,defaultscale, GameState.GAME,"Assets/larrow.png");
        larrow.setArrow(true);
        larrow.setY(GAME_HEIGHT / 2 - (int)(larrow.getImage().getHeight() * larrow.getScale() / 2));
        green=new MenuButton(GAME_WIDTH-750,1.2f,GameState.GAME,"Assets/backgroundgreen.png");
        green.setBorder(true);
        cont = new MenuButton(GAME_WIDTH-300,GAME_HEIGHT-150,0.21f,GameState.GAME,"Assets/nolinecon.png","Assets/continue.png");
        pink=new MenuButton(GAME_WIDTH-750,1.2f,GameState.GAME,"Assets/backgroundpink.png");
        pink.setBorder(true);
    }

    @Override
    public void render(Graphics g) {
        srarrow.render(g);
        larrow.render(g);
        maps.get(currentMapIndex).render(g);
        cont.render(g);
    }

    @Override
    public void update() {
        cont.update();
        srarrow.update();
        larrow.update();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, srarrow)) {
            currentMapIndex = (currentMapIndex + 1) % maps.size();
        }

        else if (isIn(e, larrow)) {
            currentMapIndex = (currentMapIndex - 1 + maps.size()) % maps.size();
        }
        if (isIn(e,cont)){
            switch (currentMapIndex){
                case PINK:
                    menuGUI.GE.setBackgroundimg(PINK);
                    if (GameEngine.gameEngine.multiplayer){
                        GameEngine.gameEngine.serverhost=true;
                        GameEngine.gameEngine.multiplayerSetup(0,"dick",PINK);
                        menuGUI.startGame(PINK);
                    }
                    else {
                        menuGUI.startGame(PINK);
                    }
                    break;
                case GREEN:
                    menuGUI.GE.setBackgroundimg(GREEN);
                    if (GameEngine.gameEngine.multiplayer){
                        GameEngine.gameEngine.serverhost=true;
                        GameEngine.gameEngine.multiplayerSetup(0,"dick",GREEN);
                        menuGUI.startGame(GREEN);
                    }
                    else {
                        menuGUI.startGame(GREEN);
                    }
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isIn(e,srarrow)){
            srarrow.setMouseEntered(true);
        } else if (!isIn(e,srarrow)) {
            srarrow.setMouseEntered(false);
        }
        if (isIn(e,larrow)){
            larrow.setMouseEntered(true);

        }
        else if (!isIn(e,larrow)) {
            larrow.setMouseEntered(false);
        }
        if (isIn(e,cont)){
            cont.setMouseEntered(true);
        } else if (!isIn(e,cont)) {
            cont.setMouseEntered(false);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    private boolean isIn(MouseEvent e, MenuButton b) {
        return b.getBounds().contains(e.getX(),e.getY());
    }

}
