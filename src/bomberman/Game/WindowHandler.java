package bomberman.Game;

import bomberman.Packets.Packet01Disconnect;
import bomberman.UI.MenuGUI;

import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowHandler implements WindowListener {
    private MenuGUI menuGUI;
    public WindowHandler(MenuGUI menuGUI){
        this.menuGUI=menuGUI;
        this.menuGUI.frame.addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (GameEngine.gameEngine.multiplayer) {
            Packet01Disconnect packet = new Packet01Disconnect(menuGUI.GE.getUsername());
            packet.writeData(menuGUI.GE.getSocketClient());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
