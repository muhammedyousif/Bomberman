package bomberman.Packets;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;

public class Packet02Move extends Packet{
    String username;
    private int x,y;
    boolean left,right,down,up;


    public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray =readData(data).split(",");
        this.username=dataArray[0];
        this.x=Integer.parseInt(dataArray[1]);
        this.y=Integer.parseInt(dataArray[2]);
        this.left= Boolean.parseBoolean(dataArray[3]);
        this.right= Boolean.parseBoolean(dataArray[4]);
        this.up= Boolean.parseBoolean(dataArray[5]);
        this.down= Boolean.parseBoolean(dataArray[6]);

    }
    public Packet02Move(String username,int x,int y,boolean left,boolean right,boolean up,boolean down) {
        super(02);
        this.username=username;
        this.x=x;
        this.y=y;
        this.left=left;
        this.up=up;
        this.down=down;
        this.right=right;

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
        return ("02"+this.username+","+this.x+","+this.y+","+left+","+right+","+up+","+down).getBytes();
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

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isUp() {
        return up;
    }
}
