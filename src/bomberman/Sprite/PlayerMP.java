package bomberman.Sprite;

import bomberman.Game.Bomberman;
import bomberman.Game.Level;

import java.net.InetAddress;

public class PlayerMP extends Bomberman {
    public InetAddress ipAddress;
    public int port;

    public PlayerMP(int x,int y, int width,int height,String username, Level level,InetAddress ipAddress,int port){
        super(x, y, width, height, username, level);
        this.ipAddress=ipAddress;
        this.port=port;
    }

    @Override
    public void update() {
        super.update();
    }
}
