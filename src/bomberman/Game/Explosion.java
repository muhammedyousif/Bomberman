package bomberman.Game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Graphics;

public class Explosion {
    private int timeBuffer = 20;
    private int current_image_index;
    public int x ;
    public int y;
    private Image image;
    public Level level;

    public Explosion(int x,int y, Level level){
        this.x = x;
        this.y = y;
        this.current_image_index = 1;
        String str = "src/bomberman/Assets/explosions/expl";
        str = str.concat(String.valueOf(current_image_index));
        str = str.concat(".png");
        this.image = new ImageIcon(str).getImage();
        this.level = level;

    }
    public void tick(){
        timeBuffer = timeBuffer-1;
        if(timeBuffer == 0){
            nextImg();
            timeBuffer = 20;
        }
    }

    private void nextImg(){

        this.current_image_index++;
        if(current_image_index <7){
            String str = "src/bomberman/Assets/explosions/expl";
            str = str.concat(String.valueOf(current_image_index));
            str = str.concat(".png");
            this.image = new ImageIcon(str).getImage();
        }
        else{
            clean();
        }
    }
    private void clean(){
        level.explosions.remove(this);
    }
    public void draw(Graphics g){
        g.drawImage(image,x,y,50,50,null);
    }


}
