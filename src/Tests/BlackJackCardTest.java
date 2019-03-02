package Tests;

import Enums.BlackJackEnums;
import Objects.Cards.BlackJackCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BlackJackCardTest {
    @Test
    public void TestCreatingABlackJackCard() {
        BlackJackCard card = new BlackJackCard(BlackJackEnums.Suits.SPADES, 1);
        Assertions.assertTrue(card != null);
    }

    @Test
    public void TestReadingABlackJackCardsCountedValue() throws Exception {
        BlackJackCard lowCard = new BlackJackCard(BlackJackEnums.Suits.SPADES, 2);
        BlackJackCard highCard = new BlackJackCard(BlackJackEnums.Suits.SPADES, 12);
        BlackJackCard neutralCard = new BlackJackCard(BlackJackEnums.Suits.SPADES, 8);

        Assertions.assertTrue(lowCard.GetCardCountedValue() == BlackJackEnums.CardCountingValue.LOWCARD);
        Assertions.assertTrue(highCard.GetCardCountedValue() == BlackJackEnums.CardCountingValue.HIGHCARD);
        Assertions.assertTrue(neutralCard.GetCardCountedValue() == BlackJackEnums.CardCountingValue.NEUTRALCARD);
    }

    @Test
    public void TestGettingADescriptiveNameForBlackJackCards() throws Exception {
        BlackJackCard lowCard = new BlackJackCard(BlackJackEnums.Suits.SPADES, 2);
        BlackJackCard highCard = new BlackJackCard(BlackJackEnums.Suits.SPADES, 12);
        BlackJackCard neutralCard = new BlackJackCard(BlackJackEnums.Suits.SPADES, 8);

        Assertions.assertEquals(lowCard.NumberLabels.get(lowCard.Number) + " of " + BlackJackEnums.Suits.SPADES.toString(), lowCard.GetName());
        Assertions.assertEquals(highCard.NumberLabels.get(highCard.Number) + " of " + BlackJackEnums.Suits.SPADES.toString(), highCard.GetName());
        Assertions.assertEquals(neutralCard.NumberLabels.get(neutralCard.Number) + " of " + BlackJackEnums.Suits.SPADES.toString(), neutralCard.GetName());
    }
}