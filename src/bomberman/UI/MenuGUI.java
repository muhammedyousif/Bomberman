package bomberman.UI;

import bomberman.Game.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;



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
        fileMenu.add(restartMenuItem);
        frame.setPreferredSize(new Dimension(920,843));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
