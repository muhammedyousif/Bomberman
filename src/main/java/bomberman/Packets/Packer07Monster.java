package bomberman.Packets;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;

public class Packer07Monster extends Packet{
    private String username;
    private int monsterId,x,y;
    private boolean alive;

    public Packer07Monster(byte[] data) {
        super(07);
        String[] dataArray =readData(data).split(",");
        username=dataArray[0];
        monsterId=Integer.parseInt(dataArray[1]);
        x=Integer.parseInt(dataArray[2]);
        y=Integer.parseInt(dataArray[3]);
        alive =Boolean.parseBoolean(dataArray[4]);
    }
    public Packer07Monster(String username,int monsterId,int x,int y,boolean alive){
        super(07);
        this.username=username;
        this.monsterId=monsterId;
        this.x=x;
        this.y=y;
        this.alive=alive;
    }

    @Override
    public byte[] getData() {
        return ("07"+this.username+","+this.monsterId+","+this.x+","+this.y+","+this.alive).getBytes();
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    public String getUsername() {
        return username;
    }

    public int getMonsterId() {
        return monsterId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return alive;
    }
}
