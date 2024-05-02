package bomberman.Sprite;
import java.awt.*;
import bomberman.Game.*;

import javax.swing.*;

public class Box extends Sprite{
    private Level level;
    public int id;
    private Image image;
    private float offset=5/4;
    private float offset2=5/4+2.5f;
    public Box(int x,int y,int width, int height, Image image,Level level,int id){
        super(x,y,width,height,image);
        this.image=image;
        this.id=id;
        this.level = level;
        initHitbox((int) (x-offset*2),(int)(y-offset*2),level.getBlock_width(),level.getBlock_width());
    }
    public boolean shouldDropPowerUp() {
        int randomNumber = (int) (Math.random() * 100);
        return randomNumber < 80;
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

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int)(hitbox.x+offset2), (int)(hitbox.y+offset2), width, height, null);
        //drawHitbox(g);
    }
}
