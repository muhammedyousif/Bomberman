package bomberman.Game;

import bomberman.UI.PauseOverlay;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
    GameEngine gameEngine;
    PauseOverlay pauseOverlay;
    public Mouse(GameEngine gameEngine){
        this.gameEngine=gameEngine;
    }
    public Mouse(PauseOverlay pauseOverlay){
        this.pauseOverlay=pauseOverlay;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        gameEngine.MousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
