
import java.io.IOException;

import AdventureModel.AdventureGame;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import views.AdventureGameView;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.StageStyle;

public class BasicAdventureTest {
    @Test
    void getCommandsTest() throws IOException {
        AdventureGame game = new AdventureGame("TinyGame");
        String commands = game.player.getCurrentRoom().getCommands();
        assertEquals("DOWN,NORTH,IN,WEST,UP,SOUTH", commands);
    }

    @Test
    void getObjectString() throws IOException {
        AdventureGame game = new AdventureGame("TinyGame");
        String objects = game.player.getCurrentRoom().getObjectString();
        assertEquals("a magical water bird", objects);
    }
}