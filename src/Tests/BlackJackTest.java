package Tests;

import Enums.BlackJackEnums;
import Games.BlackJack.BlackJack;
import Games.BlackJack.BlackJackPlayer;
import Objects.Cards.BlackJackCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class BlackJackTest {
    @Test
    public void TestDealingStartingCards() throws Exception {
        BlackJack game = new BlackJack();

        var house = new BlackJackPlayer();
        house.Name = "House";
        house.IsHouse = true;
        house.BalanceMoney = 50.00;

        var playerA = new BlackJackPlayer();
        playerA.Name = "playerA";
        playerA.IsHouse = false;
        playerA.BalanceMoney = 50.00;

        var playerB = new BlackJackPlayer();
        playerB.Name = "playerB";
        playerB.IsHouse = false;
        playerB.BalanceMoney = 50.00;

        List<BlackJackPlayer> players = Arrays.asList(house, playerA, playerB);
        game.StartGame(players);

        Assertions.assertTrue(players.stream().filter(player -> player.IsHouse).count() == 1);
        players.forEach(player -> {
            Assertions.assertTrue(player.PlayerHand.size() == 2);
            if(player.IsHouse){
                Assertions.assertTrue(player.PlayerHand.stream().filter(card -> card.IsVisible).count() == 1);
                Assertions.assertTrue(player.PlayerHand.stream().filter(card -> card.IsVisible == false).count() == 1);
            } else{
                Assertions.assertTrue(player.PlayerHand.stream().filter(card -> card.IsVisible).count() == 2);
            }
        });
    }

    @Test
    public void TestHittingPlayers() throws Exception {
        final int BlackJackValue = 21;
        BlackJack game = new BlackJack();

        var house = new BlackJackPlayer();
        house.Name = "House";
        house.IsHouse = true;
        house.BalanceMoney = 50.00;

        var playerA = new BlackJackPlayer();
        playerA.Name = "playerA";
        playerA.IsHouse = false;
        playerA.BalanceMoney = 50.00;

        List<BlackJackPlayer> players = Arrays.asList(house, playerA);
        game.DealStartingCards(players);


        var cardString = playerA.PlayerHand.stream().map(card -> card.GetName()).reduce((base, added) -> base +", " + added).get();
        System.out.println("cards: "+cardString);
        System.out.println(playerA.State +": " + game.GetTotalValue(playerA.PlayerHand));

        while(game.GetTotalValue(playerA.PlayerHand) < BlackJackValue){

            System.out.println(playerA.State +": " + game.GetTotalValue(playerA.PlayerHand));
            Assertions.assertTrue( playerA.State == BlackJackEnums.PlayerState.ACTIVE);
            game.Hit(playerA);
            cardString = playerA.PlayerHand.stream().map(card -> card.GetName()).reduce((base, added) -> base +", " + added).get();
            System.out.println("cards: "+cardString);
        }

        cardString = playerA.PlayerHand.stream().map(card -> card.GetName()).reduce((base, added) -> base +", " + added).get();
        System.out.println("cards: "+cardString);
        System.out.println(playerA.State +": " + game.GetTotalValue(playerA.PlayerHand));
        if(game.GetTotalValue(playerA.PlayerHand) > BlackJackValue){
            Assertions.assertTrue(playerA.State == BlackJackEnums.PlayerState.BUSTED);
        }

        if(game.GetTotalValue(playerA.PlayerHand) == BlackJackValue){
            Assertions.assertTrue(playerA.State == BlackJackEnums.PlayerState.BLACKJACK);
        }
    }

    @Test
    public void TestGettingTotalValues(){
        BlackJack game = new BlackJack();

        BlackJackCard spadesTwo = new BlackJackCard(BlackJackEnums.Suits.SPADES, 2);
        BlackJackCard spadesAce = new BlackJackCard(BlackJackEnums.Suits.SPADES, 1);
        BlackJackCard spadesSeven = new BlackJackCard(BlackJackEnums.Suits.SPADES, 7);

        var house = new BlackJackPlayer();
        house.Name = "House";
        house.IsHouse = true;
        house.BalanceMoney = 50.00;
        house.PlayerHand = Arrays.asList(spadesSeven, spadesAce, spadesTwo);

        var playerA = new BlackJackPlayer();
        playerA.Name = "playerA";
        playerA.IsHouse = false;
        playerA.BalanceMoney = 50.00;
        playerA.PlayerHand = Arrays.asList(spadesSeven, spadesAce, spadesAce);

        var playerB = new BlackJackPlayer();
        playerB.Name = "playerB";
        playerB.IsHouse = false;
        playerB.BalanceMoney = 50.00;
        playerB.PlayerHand = Arrays.asList(spadesSeven, spadesSeven, spadesSeven);

        List<BlackJackPlayer> players = Arrays.asList(house, playerA, playerB);

        players.forEach(player -> {
            Assertions.assertEquals(20, game.GetTotalValue(house.PlayerHand));
            Assertions.assertEquals(19, game.GetTotalValue(playerA.PlayerHand));
            Assertions.assertEquals(21, game.GetTotalValue(playerB.PlayerHand));
        });
    }

    @Test
    public void TestSearchingForBlackJack(){
        BlackJack game = new BlackJack();

        BlackJackCard spadesTwo = new BlackJackCard(BlackJackEnums.Suits.SPADES, 2);
        BlackJackCard spadesAce = new BlackJackCard(BlackJackEnums.Suits.SPADES, 1);
        BlackJackCard spadesSeven = new BlackJackCard(BlackJackEnums.Suits.SPADES, 7);

        var houseWithoutBlackJack = new BlackJackPlayer();
        houseWithoutBlackJack.Name = "House";
        houseWithoutBlackJack.IsHouse = true;
        houseWithoutBlackJack.BalanceMoney = 50.00;
        houseWithoutBlackJack.PlayerHand = Arrays.asList(spadesSeven, spadesAce, spadesTwo);

        var playerWithoutBlackJack = new BlackJackPlayer();
        playerWithoutBlackJack.Name = "playerWithoutBlackJack";
        playerWithoutBlackJack.IsHouse = false;
        playerWithoutBlackJack.BalanceMoney = 50.00;
        playerWithoutBlackJack.PlayerHand = Arrays.asList(spadesSeven, spadesAce, spadesAce);

        var playerWithBlackJack = new BlackJackPlayer();
        playerWithBlackJack.Name = "playerWithBlackJack";
        playerWithBlackJack.IsHouse = false;
        playerWithBlackJack.BalanceMoney = 50.00;
        playerWithBlackJack.PlayerHand = Arrays.asList(spadesSeven, spadesSeven, spadesSeven);

        List<BlackJackPlayer> players = Arrays.asList(houseWithoutBlackJack, playerWithoutBlackJack, playerWithBlackJack);

        var result = game.GetPlayersWithBlackJack(players);

        Assertions.assertEquals(playerWithBlackJack.Name, result.get(true).get(0).Name);
        Assertions.assertTrue(result.get(false).stream().anyMatch(player -> player.Name == playerWithoutBlackJack.Name));
        Assertions.assertTrue(result.get(false).stream().anyMatch(player -> player.Name == houseWithoutBlackJack.Name));
    }

    @Test
    public void TestBlackJackTurns(){
        BlackJack game = new BlackJack();

        var house = new BlackJackPlayer();
        house.Name = "House";
        house.IsHouse = true;
        house.BalanceMoney = 50.00;

        var playerA = new BlackJackPlayer();
        playerA.Name = "playerA";
        playerA.IsHouse = false;
        playerA.BalanceMoney = 50.00;

        var playerB = new BlackJackPlayer();
        playerB.Name = "playerB";
        playerB.IsHouse = false;
        playerB.BalanceMoney = 50.00;

        List<BlackJackPlayer> players = Arrays.asList(house, playerA, playerB);

        game.BlackJackTurns(players);
    }
}