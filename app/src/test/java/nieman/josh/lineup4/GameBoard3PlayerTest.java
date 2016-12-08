package nieman.josh.lineup4;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mattm on 12/7/2016.
 */
public class GameBoard3PlayerTest {

    int playersTurn = 1;

    public void setPlayersTurn(int playerNumber){
        if(playerNumber == 1){
            playersTurn = 1;
        }
        else if(playerNumber == 2){
            playersTurn = 2;
        }
        else if(playerNumber == 3){
            playersTurn = 3;
        }
    }

    public int getPlayerTurn(){
        return playersTurn;
    }

    public boolean isPlayer1sTurn(){
        return (playersTurn == 1);
    }


    public boolean isPlayer2sTurn(){
        return (playersTurn == 2);
    }


    public boolean isPlayer3sTurn(){
        return (playersTurn == 3);
    }
    @Test
    public void testIsPlayer1sTurn() throws Exception {
        assertEquals(isPlayer1sTurn(), true);
    }

    @Test
    public void testIsPlayer2sTurn() throws Exception {
        assertEquals(isPlayer2sTurn(), false);
    }

    @Test
    public void testIsPlayer3sTurn() throws Exception {
        assertEquals(isPlayer3sTurn(), false);
    }
}