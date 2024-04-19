package bomberman.UI;

import bomberman.Game.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;


public class MenuGUI {
    private JFrame frame;
    private GameEngine GE;

    public MenuGUI(){
        frame = new JFrame("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GE = new GameEngine();
        frame.getContentPane().add(GE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Game");
        menuBar.add(fileMenu);
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        JMenuItem restartMenuItem = new JMenuItem("Restart");
        restartMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GE.restartGame();  // Call the restart method on the game engine
            }
        });

        fileMenu.add(restartMenuItem);
        frame.setPreferredSize(new Dimension(920,843));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                //reset dirbool
            }
        });
    }
}
