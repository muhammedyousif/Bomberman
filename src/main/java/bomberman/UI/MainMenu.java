package bomberman.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame {
    private JPanel panel1;
    private JLabel labelmain;
    private JButton PLAYButton;
    private JButton SETTINGSButton;
    private JButton QUITButton;
    private boolean playing = false;

    public MainMenu() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        panel1 = new JPanel();
        this.add(panel1);
        panel1.setFocusable(true);
        labelmain = new JLabel();
        PLAYButton = new JButton();
        QUITButton = new JButton();
        SETTINGSButton = new JButton();
        getmaniac();
    }

    private void getmaniac() {
        try {
            // Create a Font object from the font file
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Assets/MonomaniacOne-Regular.ttf")).deriveFont(60f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            // Register the font
            ge.registerFont(customFont);
            PLAYButton.setFont(customFont.deriveFont(45f));
            SETTINGSButton.setFont(customFont.deriveFont(45f));
            QUITButton.setFont(customFont.deriveFont(45f));

            labelmain.setFont(customFont);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


    }

    public boolean isPlaying() {
        return playing;
    }

    public JButton getPLAYButton() {
        return PLAYButton;
    }

    public JButton getSETTINGSButton() {
        return SETTINGSButton;
    }

    public JButton getQUITButton() {
        return QUITButton;
    }

}
