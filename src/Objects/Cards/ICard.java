package Objects.Cards;

abstract class ICard {
    // Diamonds, Clubs, Hearts, Spades
    public String Suit;

    // A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K,
    // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,12,13
    public int Number;

    public int[] GetValue(){
        return new int[]{Number};
    }

    abstract String GetName();

    abstract boolean AreEqual(ICard card);
}
