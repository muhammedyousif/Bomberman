package bomberman.Sprite;

import java.awt.*;

public class Entity {
    protected int x;
    protected int y;
    public int width;
    public int height;
    protected Rectangle hitbox;

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    protected void initHitbox(int x,int y,int width, int height){
        hitbox=new Rectangle(x,y,width,height);
    }
    public void updateHitbox(){
        hitbox.x=x;
        hitbox.y=y;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    protected void drawHitbox(Graphics g){
        g.setColor(Color.red);
        g.drawRect(hitbox.x,hitbox.y,hitbox.width,hitbox.height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
