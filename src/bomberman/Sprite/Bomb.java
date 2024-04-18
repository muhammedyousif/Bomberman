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

    public Bomb(int x, int y, int height, int width, Image image, Level level) {
        super(x - 42 / 2, y - 42 / 2, 42, 42, image);
        this.level = level;
    }

    public boolean collides_with_sprite(int x1, int y1, int w1, int h1, Sprite sprite) {
        Rectangle rect = new Rectangle(x1, y1, w1, h1);
        Rectangle otherRect = new Rectangle(sprite.x, sprite.y, sprite.width, sprite.height);
        return rect.intersects(otherRect);
    }

    public int checkCollisionsLevel(int x1, int y1) {
        int i = 0;
        while (i < level.grid.size()) {
            if (collides_with_sprite(x1, y1, 50, 50, level.grid.get(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private boolean collides_with_player(Sprite sprite) {
        Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle otherRect = new Rectangle(sprite.getHitbox().x, sprite.getHitbox().y, sprite.getHitbox().width, sprite.getHitbox().height);
        return rect.intersects(otherRect);
    }


    public void tick() {
        timeLeft = timeLeft - 1;
        if (timeLeft == 0) {
            blowUp();
        }
    }

    public void blowUp() {
        level.explosions.add(new Explosion(x, y, level)); // Center explosion
        explodeInDirection(-1, 0); // explode left
        explodeInDirection(1, 0);  // explode right
        explodeInDirection(0, -1); // explode up
        explodeInDirection(0, 1);  // explode down
        level.bombs.remove(this);
    }

    private void explodeInDirection(int dx, int dy) {
        boolean found = false;
        for (int idx = 1; idx <= this.strength && !found; idx++) {
            int pos_to_check_x = this.x + (60 * idx * dx);
            int pos_to_check_y = this.y + (60 * idx * dy);
            int index = checkCollisionsLevel(pos_to_check_x, pos_to_check_y);
            if (index == -1) {
                level.explosions.add(new Explosion(pos_to_check_x, pos_to_check_y, level));
                checkPlayerHit(pos_to_check_x,pos_to_check_y);

            } else {
                Sprite hitSprite = level.grid.get(index);
                if (hitSprite instanceof Box) {
                    ((Box) hitSprite).blowUp();
                    level.explosions.add(new Explosion(pos_to_check_x, pos_to_check_y, level));
                    found = true;
                } else if (hitSprite instanceof Wall) {
                    found = true;
                } else if (hitSprite instanceof Monster) {
                    // Handle monster logic here if necessary
                } else if (hitSprite instanceof Bomberman) {
                    System.out.println("man");
                }
            }
        }

    }
    private void checkPlayerHit(int x, int y) {
        Bomberman player =level.gameEngine.gameLogic.getPlayers().get(0);
        System.out.println("ENTERED");
        System.out.println("Explosion rect: " + x + ", " + y + ", " + player.width + ", " + player.height);
        System.out.println("Player rect: " + player.x + ", " + player.y + ", " + player.width + ", " + player.height);

        if (collides_with_player(player) ||player.collides_with_sprite(x, y, player.width, player.height, player)) {
                System.out.println("HIT");
                player.setAlive(false);  // Assume `setAlive` is a method to handle player status
                level.explosions.add(new Explosion(x, y, level));  // Optional: Add explosion effect on player hit
            }

    }

}