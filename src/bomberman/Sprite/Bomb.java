package bomberman.Sprite;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bomberman.Game.*;

import javax.swing.*;

public class Bomb extends Sprite {

    private int timeLeft = 300;
    private int strength = 2;
    private Level level;

    public Bomb(int x,int y,int height,int width, Image image, Level level){
        super(x-42/2,y-42/2,42,42,image);
        this.level = level;
    }

    public boolean collides_with_sprite(int x1, int y1,int w1,int h1, Sprite sprite) {
        Rectangle rect = new Rectangle(x1, y1, w1 , h1);
        Rectangle otherRect = new Rectangle(sprite.x, sprite.y, sprite.width, sprite.height);
        return rect.intersects(otherRect);
    }
    public int checkCollisionsLevel(int x1,int y1){
        int i = 0;
        while(i<level.grid.size()){
            if(collides_with_sprite(x1,y1, 50, 50, level.grid.get(i))){
                return i;
            }
            i++;
        }
        return -1;
    }
    private boolean collides_with_player(Sprite sprite) {
        Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle otherRect = new Rectangle(sprite.x, sprite.y, sprite.width, sprite.height);
        return rect.intersects(otherRect);
    }



    public void tick(){
        timeLeft = timeLeft-1;
        if(timeLeft == 0){
            blowUp();
        }
    }
    public void blowUp(){
        level.explosions.add(new Explosion(x,y,level));
        int idx;
        idx = 1;
        boolean found;
        found = false;
        while(idx <= this.strength && !found){
            int pos_to_check_x = this.x - (60*idx);
            int pos_to_check_y = this.y;
            int index = checkCollisionsLevel(pos_to_check_x,pos_to_check_y);
            if(index == -1){
                level.explosions.add(new Explosion(pos_to_check_x,pos_to_check_y,level));
            }
            else{
                if(level.grid.get(index) instanceof Box){
                    ((Box) level.grid.get(index)).blowUp();
                    level.explosions.add(new Explosion(pos_to_check_x,pos_to_check_y,level));
                    found = true;
                }
                if(level.grid.get(index) instanceof Wall){
                    found = true;
                }
                if(level.grid.get(index) instanceof Monster){

                }
            }
            idx++;
        }
        idx = 1;
        found = false;
        while(idx <= this.strength && !found){
            int pos_to_check_x = this.x + (60*idx);
            int pos_to_check_y = this.y;
            int index = checkCollisionsLevel(pos_to_check_x,pos_to_check_y);
            if(index == -1){
                level.explosions.add(new Explosion(pos_to_check_x,pos_to_check_y,level));
            }
            else{
                if(level.grid.get(index) instanceof Box){
                    ((Box) level.grid.get(index)).blowUp();
                    level.explosions.add(new Explosion(pos_to_check_x,pos_to_check_y,level));
                    found = true;
                }
                if(level.grid.get(index) instanceof Wall){
                    found = true;
                }
                if(level.grid.get(index) instanceof Monster){

                }
            }
            idx++;
        }
        idx = 1;
        found = false;
        while(idx <= this.strength && !found){
            int pos_to_check_x = this.x;
            int pos_to_check_y = this.y - (60*idx);
            int index = checkCollisionsLevel(pos_to_check_x,pos_to_check_y);
            if(index == -1){
                level.explosions.add(new Explosion(pos_to_check_x,pos_to_check_y,level));
            }
            else{
                if(level.grid.get(index) instanceof Box){
                    ((Box) level.grid.get(index)).blowUp();
                    level.explosions.add(new Explosion(pos_to_check_x,pos_to_check_y,level));
                    found = true;
                }
                if(level.grid.get(index) instanceof Wall){
                    found = true;
                }
                if(level.grid.get(index) instanceof Monster){

                }
            }
            idx++;
        }
        idx = 1;
        found = false;
        while(idx <= this.strength && !found){
            int pos_to_check_x = this.x;
            int pos_to_check_y = this.y + (60*idx);
            int index = checkCollisionsLevel(pos_to_check_x,pos_to_check_y);
            if(index == -1){
                level.explosions.add(new Explosion(pos_to_check_x,pos_to_check_y,level));
            }
            else{
                if(level.grid.get(index) instanceof Box){
                    ((Box) level.grid.get(index)).blowUp();
                    level.explosions.add(new Explosion(pos_to_check_x,pos_to_check_y,level));
                    found = true;
                }
                if(level.grid.get(index) instanceof Wall){
                    found = true;
                }
                if(level.grid.get(index) instanceof Monster){

                }
            }
            idx++;
        }
        level.bombs.remove(this);
    }
}
