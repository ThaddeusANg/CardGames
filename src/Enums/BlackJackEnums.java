package Enums;

public class BlackJackEnums {
    public enum CardCountingValue
    {
        UNKNOWN, LOWCARD, NEUTRALCARD, HIGHCARD
    }

    public enum Suits{
        DIAMONDS, HEARTS, CLUBS, SPADES
    }

    public enum PlayerState{
        ACTIVE, BLACKJACK, BUSTED
    }
}
