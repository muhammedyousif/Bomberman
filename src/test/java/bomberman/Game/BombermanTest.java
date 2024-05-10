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
        monster=new Monster(75,75,60,60);
        bomberman.x=75;
        bomberman.y=75;
        mockLevel.getMonsters().add(monster);
        monster.setLevel(mockLevel);
        monster.update();
        bomberman.update();
        assertTrue(monster.collides_player(bomberman), "Monster should collide with Bomberman");
    }


}