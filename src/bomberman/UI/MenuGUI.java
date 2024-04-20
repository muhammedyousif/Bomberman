package bomberman.UI;

import bomberman.Game.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;


public class MenuGUI {
    private JFrame frame;
    private GameEngine GE;
    private JLabel statusLabel;


    public MenuGUI(){
        frame = new JFrame("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GE = new GameEngine(this);
        GE.setLayout(new BorderLayout());
        setStatusLabel();
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
        //frame.setPreferredSize(new Dimension(920,843));
        frame.setPreferredSize(new Dimension(920,800));

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
    private void setStatusLabel(){
        frame.getContentPane().add(GE);
        JPanel statusBar = new JPanel();
        statusBar.setBackground(Color.BLACK);
        statusBar.setPreferredSize(new Dimension(920, 40));
        ImageIcon bomb = new ImageIcon("src/bomberman/Assets/bomb.png");
        Image image = bomb.getImage(); // Convert the ImageIcon to an Image
        Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // Scale it to fit your layout, here 20x20 is an example
        bomb = new ImageIcon(newimg);
        statusLabel = new JLabel(": "+GE.gameLogic.getPlayers().get(0).getBombCounter());
        statusLabel.setIcon(bomb); // Set the icon to the label
        statusLabel.setHorizontalTextPosition(SwingConstants.RIGHT); // Text to the right of the icon
        statusLabel.setForeground(Color.WHITE);
        statusBar.add(statusLabel);
        GE.add(statusBar,BorderLayout.SOUTH);

    }
    public void updateBombCounter() {
        // Assuming there is a method in GameEngine or GameLogic that returns the current bomb count.
        int currentBombCount = GE.gameLogic.getPlayers().get(0).getBombCounter();
        statusLabel.setText(": " + currentBombCount);
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }
}
