package bomberman.Game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameEngine extends JPanel{
    public GameEngine(){
        int FPS = 60;
        Image background = new ImageIcon("src/bomberman/Assets/background.jpg").getImage();
        Timer newFrameTimer = new Timer(1000/FPS, new NewFrameListener());
        newFrameTimer.start();
    }

    class NewFrameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            repaint();
        }
    }

}
