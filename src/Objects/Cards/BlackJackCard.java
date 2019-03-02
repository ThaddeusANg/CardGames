package Objects.Cards;

import Enums.BlackJackEnums;

import java.util.HashMap;
import java.util.Map;

public class BlackJackCard extends ICard {
    public Map<Integer, String> NumberLabels = new HashMap<>();
    public boolean IsVisible = true;

    public BlackJackCard(BlackJackEnums.Suits suit, int value) {
        InitializeMap();

        this.Suit = suit.toString();
        this.Number = value;
    }

    public BlackJackEnums.CardCountingValue GetCardCountedValue(){
        if(this.IsVisible){
            switch(this.Number){
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return BlackJackEnums.CardCountingValue.LOWCARD;
                case 7:
                case 8:
                case 9:
                    return BlackJackEnums.CardCountingValue.NEUTRALCARD;
                case 10:
                case 11:
                case 12:
                case 13:
                case 1:
                    return BlackJackEnums.CardCountingValue.HIGHCARD;
                default:
                    return BlackJackEnums.CardCountingValue.UNKNOWN;
            }
        }
        else{
            return BlackJackEnums.CardCountingValue.UNKNOWN;
        }
    }

    // Is an Ace
    public boolean IsSoftCard(){
        return this.Number == 1;
    }

    @Override
    public boolean AreEqual(ICard card){
        return this.Suit == card.Suit && this.Number == card.Number;
    }

    @Override
    public String GetName() {
        return this.NumberLabels.get(this.Number) + " of " + this.Suit;
    }

    @Override
    public int[] GetValue(){
        int[] value;
        switch(this.Number){
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:

            case 10:
                value = new int[]{this.Number};
                break;
            case 11:
            case 12:
            case 13:
                value = new int[]{10};
                break;
            case 1:
                value = new int[]{1,11};
                break;
            default:
                value = new int[]{};
        }

        return value;
    }

    private void InitializeMap(){
        NumberLabels.put(1, "ACE");
        NumberLabels.put(2, "2");
        NumberLabels.put(3, "3");
        NumberLabels.put(4, "4");
        NumberLabels.put(5, "5");
        NumberLabels.put(6, "6");
        NumberLabels.put(7, "7");
        NumberLabels.put(8, "8");
        NumberLabels.put(9, "9");
        NumberLabels.put(10, "10");
        NumberLabels.put(11, "JACK");
        NumberLabels.put(12, "QUEEN");
        NumberLabels.put(13, "KING");
    }
}
