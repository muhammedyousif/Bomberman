package bomberman.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface StateMethods {
    public void render(Graphics g);
    public void update();
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
    public void MousePressed(MouseEvent e);
}
