package bomberman.Packets;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;

public class Packet04Bomb extends Packet{
    private int x;
    private int y;
    String username;
    public Packet04Bomb(byte[] data) {
        super(04);
        String[] dataArray =readData(data).split(",");
        username=dataArray[0];
        x= Integer.parseInt(dataArray[1]);
        y= Integer.parseInt(dataArray[2]);
    }
    public Packet04Bomb(String username,int x,int y){
        super(04);
        this.username=username;
        this.x=x;
        this.y=y;
    }
    @Override
    public byte[] getData() {
        return ("04"+this.username+","+this.x+","+this.y).getBytes();
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getUsername() {
        return username;
    }
}
