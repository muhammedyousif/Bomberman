package bomberman.Sprite;
import java.awt.*;
import bomberman.Game.*;

import javax.swing.*;

public class Box extends Sprite{
    private Level level;
    public Box(int x,int y,int width, int height, Image image,Level level){
        super(x,y,width,height,image);
        this.level = level;
    }
    public boolean shouldDropPowerUp() {
        int randomNumber = (int) (Math.random() * 100);
        return randomNumber < 0;
    }
    public void blowUp(){
        Image image = new ImageIcon("src/bomberman/Assets/powerup.png").getImage();
        if(shouldDropPowerUp()){
            //level.grid.add(new PowerUp(this.x+5, this.y+5, 50, 50, image));
            PowerUp bomb = new PowerUp(this.x,this.y,50,45,image,level);
            level.gameEngine.gameLogic.bombs.add(bomb);
        }
        else{
            BigBombItem bigBombItem=new BigBombItem(this.x,this.y,40,40,image,level);
            level.gameEngine.gameLogic.bombs.add(bigBombItem);
        }
        level.grid.remove(this);
    }
}
