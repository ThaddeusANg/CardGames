package Tests;

import Games.BlackJack.BlackJackPlayer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackJackPlayerTest {
    @Test
    public void testCheckingIfPlayersAreTheSameById(){
        BlackJackPlayer playerA = new BlackJackPlayer();
        playerA.Name = "playerA";
        playerA.BalanceMoney = 50.00;

        BlackJackPlayer playerB = new BlackJackPlayer();
        playerB.Name = "playerB";
        playerB.BalanceMoney = 25.00;

        BlackJackPlayer playerASameName = new BlackJackPlayer();
        playerA.Name = "playerA";
        playerA.BalanceMoney = 25.00;

        Assertions.assertEquals(playerA, playerA);
        Assertions.assertNotEquals(playerA, playerB);
        Assertions.assertNotEquals(playerA, playerB);
    }
}