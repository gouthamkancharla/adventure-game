import java.io.IOException;
import AdventureModel.AdventureGame;
import AdventureModel.AdventureLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class BasicMagicalTest {
    @Test
    void simpleStateTest(){
        AdventureGame game = new AdventureGame("MediumGame");
        game.movePlayer("WEST");// moves player to the 2nd room
        game.interpretAction("take hat");
        assertEquals("magicalstate", game.player.getState());
    }
    @Test
    void simpleBlockedTest(){
        AdventureGame game = new AdventureGame("MediumGame");
        game.movePlayer("WEST"); //moves the player to the 2nd room
        game.interpretAction("take hat");
        game.movePlayer("DOWN");
        // 3rd room is blocked by pant but as player is in magical state he can proceed to room 3
        assertEquals(game.player.getCurrentRoom().getRoomNumber(), 3);
    }
    @Test
    void oneNormalOneMagical(){
        AdventureGame game = new AdventureGame("MediumGame");
        game.interpretAction("take plant");
        assertEquals(1,game.player.getInventory().size());
        game.movePlayer("WEST");
        game.interpretAction("take hat");
        assertEquals(2,game.player.getInventory().size());
        game.movePlayer("DOWN");
        // The hat should be used from the players inventory
        assertEquals(1,game.player.getInventory().size());
        assertEquals("PLANT", game.player.getInventory().get(0));
    }

}
