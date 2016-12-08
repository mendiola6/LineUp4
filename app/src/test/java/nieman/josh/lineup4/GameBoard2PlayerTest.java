package nieman.josh.lineup4;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mattm on 12/7/2016.
 */
public class GameBoard2PlayerTest {

    String name = "Guest";
    String player1Name = "";
    public void setPlayer1Name(String name) {
        player1Name = name;
    }

    public String getPlayer1Name(){

        return player1Name;
    }


    @Test
    public void testSetPlayer1Name() throws Exception {
        setPlayer1Name(name);
        assertEquals(player1Name,"Guest");
    }

    @Test
    public void testGetPlayer1Name() throws Exception {
        String name2 = getPlayer1Name();
        assertEquals(name2,"Guest");
    }
}