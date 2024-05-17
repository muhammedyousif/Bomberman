package bomberman.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class Mouse implements MouseListener, MouseMotionListener {
    GameEngine gameEngine;
    public Mouse(GameEngine gameEngine){
        this.gameEngine=gameEngine;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state){
            case MENU:
                try {
                    gameEngine.menuGUI.mousePressed(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case GAME:
                if (gameEngine.isPaused()){
                    try {
                        gameEngine.getPauseOverlay().MousePressed(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    gameEngine.mousePressed(e);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        switch (GameState.state){
            case MENU:
                gameEngine.menuGUI.mouseExited(e);
                break;
            case GAME:
                gameEngine.mouseExited(e);
                break;
            default:
                break;
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state){
            case MENU:
                gameEngine.menuGUI.mouseMoved(e);
                break;
            case GAME:
                gameEngine.mouseMoved(e);
                break;
            default:
                break;
        }

    }
}
