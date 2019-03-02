package Objects.Decks;

import Enums.BlackJackEnums;
import Objects.Cards.BlackJackCard;

import java.util.*;

public class BlackJackDeck {
    // A collection of cards and an associated flag if it is drawn or not
    private ArrayList<BlackJackCard> AvailableCards = new ArrayList<>();
    private ArrayList<BlackJackCard> DrawnCards = new ArrayList<>();

    private int totalDecksAvailable;
    private boolean AllowContinuousDrawing = true;


    public BlackJackDeck(int totalDecksAvailable){
        this.totalDecksAvailable = 1;
        this.totalDecksAvailable = totalDecksAvailable;

        CreateDeck();
    }

    public BlackJackCard Draw(){
        if(AvailableCards.size() == 0)
        {
            if(this.AllowContinuousDrawing == true){
                CreateDeck();
            }
            else
                {
                return null;
            }
        }

        var card = AvailableCards.get(0);
        AvailableCards.remove(0);
        DrawnCards.add(card);
        return card;
    }

    public int GetTotalRemainingCards(){
        return AvailableCards.size();
    }

    public int GetTotalDrawnCards(){
        return DrawnCards.size();
    }

    public void ResetDeck(){
        CreateDeck();
    }

    public void EnableContinuousDrawing(boolean enableContinuousDrawing){
        this.AllowContinuousDrawing = enableContinuousDrawing;
    }

    private void CreateDeck(){
        ArrayList<BlackJackEnums.Suits> Suits = new ArrayList<BlackJackEnums.Suits>();

        Suits.add(BlackJackEnums.Suits.SPADES);
        Suits.add(BlackJackEnums.Suits.HEARTS);
        Suits.add(BlackJackEnums.Suits.DIAMONDS);
        Suits.add(BlackJackEnums.Suits.CLUBS);

        for(int x = 0; x<totalDecksAvailable;x++) {
            Suits.forEach(suit -> {
                for (int val = 1; val < 14; val++) {
                    AvailableCards.add(new BlackJackCard(suit, val));
                }
            });
        }

        DrawnCards.clear();

        Collections.shuffle(AvailableCards);
    }
}
