package bomberman.Game;

import bomberman.Sprite.Bomb;
import bomberman.Sprite.Monster;
import bomberman.Sprite.Sprite;
import bomberman.UI.MenuEngine;
import bomberman.UI.MenuGUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class BombermanTest {
    Level mockLevel;
    Bomb bomb;
    Bomb mockBomb;
    Bomberman bomberman;
    Monster monster;
    GameEngine gameEngine;
    GameLogic gameLogic;
    @BeforeEach
    void setUp() throws IOException {
        mockLevel=mock(Level.class);
        mockBomb = mock(Bomb.class);
        ArrayList<Sprite> grid=new ArrayList<>();
        gameEngine=mock(GameEngine.class);
        mockLevel.gameEngine=gameEngine;
        gameLogic=mock(GameLogic.class);
        gameEngine.gameLogic=gameLogic;
        mockLevel.grid= grid;
        bomb = new Bomb(50, 50, 42, 42, null, mockLevel);
        bomberman = new Bomberman(75, 75, 40, 57, "TestPlayer", mockLevel);
    }
    @Test
    void placeBomb() {
        bomberman.placeBomb();
        verify(mockLevel, times(1)).placeBomb(anyInt(), anyInt());
    }
    @Test
    void placeBombWhenDead() {

        bomberman.setAlive(false);
        bomberman.placeBomb();
        verify(mockLevel, never()).placeBomb(anyInt(), anyInt());
    }
    @Test
    void DiesFromBomb(){
        bomb.tick();
        if (bomb.getTimeLeft()==0) {
            assertFalse(bomberman.isAlive(), "Bomberman should be dead after bomb explosion at his location");
        }
    }
    @Test
    void DiesFromMonster(){
        monster=new Monster(75,75,60,60,1);
        bomberman.x=75;
        bomberman.y=75;
        mockLevel.getMonsters().add(monster);
        monster.setLevel(mockLevel);
        monster.update();
        bomberman.update();
        assertTrue(monster.collides_player(bomberman), "Monster should collide with Bomberman");
    }
    @Test
    void move() {
        bomberman.x+=10;
        bomberman.y+=10;
        assertEquals(85, bomberman.getX(), "Bomberman's X coordinate should be updated after moving");
        assertEquals(85, bomberman.getY(), "Bomberman's Y coordinate should be updated after moving");
    }

    @Test
    void isAliveInitially() {
        assertTrue(bomberman.isAlive(), "Bomberman should be alive initially");
    }

    @Test
    void isDeadAfterSetDead() {
        bomberman.setAlive(false);
        assertFalse(bomberman.isAlive(), "Bomberman should be dead after explicitly setting dead");
    }

    @Test
    void isNotDeadAfterSetAlive() {
        bomberman.setAlive(false);
        bomberman.setAlive(true);
        assertTrue(bomberman.isAlive(), "Bomberman should be alive after explicitly setting alive");
    }
    @Test
    void placeMultipleBombs() {
        // Place multiple bombs
        bomberman.placeBomb();
        bomberman.placeBomb();
        bomberman.placeBomb();
        // Verify that placeBomb method is called three times
        verify(mockLevel, times(3)).placeBomb(anyInt(), anyInt());
    }





}