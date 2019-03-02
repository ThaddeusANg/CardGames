package Tests;

import Objects.Cards.BlackJackCard;
import Objects.Decks.BlackJackDeck;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

class BlackJackDeckTest {
    @Test
    public void TestDrawingCardsFromAContinuousBlackJackDeck(){
        var blackJackDeck = new BlackJackDeck(1);
        blackJackDeck.EnableContinuousDrawing(true);

        var myCards = new ArrayList<BlackJackCard>();

        for (int x = 0; x < 100; x++){
            var drawnValue = blackJackDeck.Draw();
            System.out.println(x+ ": "+ drawnValue.GetName() );

            if(myCards.stream().anyMatch(card -> card.AreEqual(drawnValue))){
                System.out.println("=====> DUPLICATE CARD DRAWN <===== DECK RESET: ");
            }
            myCards.add(drawnValue);
            Assertions.assertTrue(drawnValue != null);
        }
    }

    @Test
    public void TestDrawingCardsFromASingleBlackJackDeck(){
        var blackJackDeck = new BlackJackDeck(1);
        blackJackDeck.EnableContinuousDrawing(false);

        var myCards = new ArrayList<BlackJackCard>();

        for (int x = 0; x < 53; x++){
            var drawnValue = blackJackDeck.Draw();

            var output = drawnValue == null
                    ? "OUT OF CARDS"
                    : drawnValue.GetName();

            System.out.println(output);

            myCards.add(drawnValue);

            if(x == 52){
                Assertions.assertTrue(drawnValue == null);
            }else{
                Assertions.assertTrue(drawnValue != null);
            }
        }
    }

    @Test
    public void TestDrawingCardsFromATwoBlackJackDecks(){
        var blackJackDeck = new BlackJackDeck(2);
        blackJackDeck.EnableContinuousDrawing(false);
        var myCards = new ArrayList<BlackJackCard>();

        for (int x = 0; x <= 104; x++){
            var drawnValue = blackJackDeck.Draw();

            if(drawnValue == null){
                System.out.println("Out OF CARDS");
            }
            else
            {
                if(myCards.stream().anyMatch(card -> card.AreEqual(drawnValue))) {
                    System.out.println("Card: " + x + "=====> DUPLICATE CARD DRAWN <===== DECK RESET: ");
                }

                System.out.println(drawnValue.GetName() + ".  Remaining Cards: " + blackJackDeck.GetTotalRemainingCards());
            }

            myCards.add(drawnValue);

            if(x == 104){
                Assertions.assertTrue(drawnValue == null);
            }else{
                Assertions.assertTrue(drawnValue != null);
            }
        }
    }
}