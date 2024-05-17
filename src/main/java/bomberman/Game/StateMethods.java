package bomberman.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public interface StateMethods {
    public void render(Graphics g);
    public void update();
    public void keyPressed(KeyEvent e) throws IOException;
    public void keyReleased(KeyEvent e);
    public void mousePressed(MouseEvent e);
    public void mouseMoved(MouseEvent e);
    public void mouseExited(MouseEvent e);
}
