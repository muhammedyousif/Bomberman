package bomberman.Packets;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;

import java.io.ByteArrayOutputStream;

public class Packet00Login extends Packet{
    String username;
    private int x,y;
    public Packet00Login(byte[] data) {
        super(00);
        String[] dataArray =readData(data).split(",");
        this.username=dataArray[0];
        this.x=Integer.parseInt( dataArray[1]);
        this.y=Integer.parseInt(dataArray[2]);

    }
    public Packet00Login(String username,int x,int y) {
        super(00);
        this.username=username;
        this.x=x;
        this.y=y;

    }
    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }
    @Override
    public byte[] getData(){
        return ("00"+this.username+","+x+","+y).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
