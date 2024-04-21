package bomberman.Network;

import bomberman.Game.GameEngine;
import bomberman.UI.MenuGUI;

import java.io.IOException;
import java.net.*;

public class GameClient extends Thread{
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private GameEngine gameEngine;
    public GameClient(GameEngine gameEngine,String ipAddress){
        this.gameEngine=gameEngine;
        try {
            this.socket=new DatagramSocket();
            this.ipAddress=InetAddress.getByName(ipAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        catch (UnknownHostException e){
            e.printStackTrace();
        }
    }
    public void run(){
        while (true){
            byte[] data=new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("server >" + new String( packet.getData()));
        }
    }
    public void sendData(byte[] data){
        DatagramPacket packet=new DatagramPacket(data, data.length,ipAddress,1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
