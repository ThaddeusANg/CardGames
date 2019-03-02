package Games.BlackJack;

import Enums.BlackJackEnums;
import Objects.Cards.BlackJackCard;
import Objects.Decks.BlackJackDeck;

import java.util.*;
import java.util.stream.Collectors;

public class BlackJack {
    private final int TotalStartingDecks = 4;

    protected List<BlackJackPlayer> players;
    BlackJackDeck deck = new BlackJackDeck(TotalStartingDecks);
    final int BlackJackValue = 21;

    protected int HighLowCardCountedTotal = 0;

    public void StartGame(List<BlackJackPlayer> players) throws Exception {
        players.forEach(player -> {
            player.PlayerHand = new ArrayList<>();
            player.State = BlackJackEnums.PlayerState.ACTIVE;
        } );

        DealStartingCards(players);

        // Check for natural Black Jack
        GetPlayersWithBlackJack(this.players).forEach((hasBlackJack, playerList) -> {
            var playerNameString = playerList
                    .stream()
                    .map(player -> player.Name)
                    .reduce((base, added) -> {
                        return base + ", " + added;
                    })
                    .get();
            var hasOrHasntBlackJack = hasBlackJack ? " has" : " doesn't have";
            System.out.println(playerNameString + hasOrHasntBlackJack + " BlackJack");
        });

        BlackJackTurns(this.players);

        PrintFinalStanding(this.players);
    }

    public void DealStartingCards(List<BlackJackPlayer> players) {
        if (players.stream().filter(player -> player.IsHouse).count() > 1) {
            throw new IllegalArgumentException("Cannot play with more than one dealer");
        }

        this.players = players;
        // sort players into players then dealer order;
        this.players.sort((p1, p2) -> Boolean.compare(p1.IsHouse, p2.IsHouse));

        // Burn a card
        deck.Draw();

        // pre-deal bet

        // deal face up card to each player
        this.Deal(true);
        this.Deal();
    }

    public void BlackJackTurns(List<BlackJackPlayer> players) {
        final String HIT = "hit";
        final String STAY = "stay";

        Scanner scanner = new Scanner(System.in);

        players.forEach(player -> {
            System.out.println("<===>");
            System.out.println("");
            System.out.println("");
            System.out.println(player.Name + "'s Turn");
            String request = "";
            //if (player.IsHouse) {
            if(true){
                while (this.GetTotalValue(player.PlayerHand) < 17) {
                    var cardString = player.PlayerHand.size() > 0
                            ? player.PlayerHand.stream().map(currentCard -> currentCard.GetName()).reduce((base, added) -> base + ", " + added).get()
                            : "NO CARDS";
                    System.out.println("cards: " + cardString);
                    System.out.println("card value: " + this.GetTotalValue(player.PlayerHand));

                    this.Hit(player);
                }
            } else {
                if(player.State != BlackJackEnums.PlayerState.BLACKJACK) {
                    do{
                        System.out.println("===");
                        var cardString = player.PlayerHand.size() > 0
                                ? player.PlayerHand.stream().map(currentCard -> currentCard.GetName()).reduce((base, added) -> base + ", " + added).get()
                                : "NO CARDS";
                        System.out.println("cards: " + cardString);
                        System.out.println("card value: " + this.GetTotalValue(player.PlayerHand));
                        System.out.println("player State: " + player.State);

                        System.out.println("hit to get a new card, stay to pass");
                        request = scanner.nextLine();

                        if (request.equalsIgnoreCase(HIT)) {
                            this.Hit(player);
                        }
                    }while (player.State == BlackJackEnums.PlayerState.ACTIVE && request.equalsIgnoreCase("hit"));
                }
            }

            System.out.println("<===>");
            System.out.println("Final State");
            System.out.println(player.Name + ": " + player.State);
            System.out.println("card value: " + this.GetTotalValue(player.PlayerHand));

            var cardString = player.PlayerHand.size() > 0
                    ? player.PlayerHand.stream().map(currentCard -> currentCard.GetName()).reduce((base, added) -> base + ", " + added).get()
                    : "NO CARDS";
            System.out.println("cards: " + cardString);
        });
    }

    public void Hit(BlackJackPlayer player) {
        var currentPlayer = players.stream().filter(p -> p == player).findFirst().get();

        if (currentPlayer != null) {

            CardCountedDeal(player, false);
        }

    }

    public void Deal(boolean isStartingHand) {
        // deal face up card to all players except the house
        // deal face down card to the house
        players.forEach(player -> {
            // all cards dealt are face up except for the first card dealt to the dealer
            CardCountedDeal(player, isStartingHand);
        });
    }

    public void Deal() {
        // deal face up card to all players except the house
        // deal face down card to the house
        players.forEach(player -> {
            CardCountedDeal(player, false);
        });
    }

    public Map<Boolean, List<BlackJackPlayer>> GetPlayersWithBlackJack(List<BlackJackPlayer> players) {
        return players.stream().collect(Collectors.groupingBy(player -> GetTotalValue(player.PlayerHand) == BlackJackValue));
    }

    public int GetTotalValue(List<BlackJackCard> hand) {
        int total = 0;

        // group cards into is aces and is not aces
        var groupedCards = hand.stream().collect(Collectors.groupingBy(card -> card.IsSoftCard()));

        if (groupedCards.get(false) == null) {
            total = 0;
        } else {
            total = groupedCards
                    .get(false)
                    .stream()
                    .filter(card -> card.IsSoftCard() == false)
                    .map(card -> card.GetValue()[0])
                    .reduce((base, added) -> base + added)
                    .get();
        }

        var aces = groupedCards.get(true);
        if (groupedCards.get(true) != null) {
            for (int x = 0; x < aces.size(); x++) {
                total += total + aces.get(x).GetValue()[1] <= 21
                        ? aces.get(x).GetValue()[1]
                        : aces.get(x).GetValue()[0];
            }
        }

        return total;
    }

    public void PrintFinalStanding(List<BlackJackPlayer> players) {
        players.sort((BlackJackPlayer a, BlackJackPlayer b) -> this.GetTotalValue(b.PlayerHand) - this.GetTotalValue(a.PlayerHand));

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        players
                .stream()
                .filter(player -> player.State != BlackJackEnums.PlayerState.BUSTED)
                .forEach(player -> {
                    System.out.println(player.Name + "'s hand");

                    var cardString = player.PlayerHand.size() > 0
                            ? player.PlayerHand.stream().map(currentCard -> currentCard.GetName()).reduce((base, added) -> base + ", " + added).get()
                            : "NO CARDS";

                    System.out.println("cards: " + cardString);
                    System.out.println("Total Value: " + this.GetTotalValue(player.PlayerHand));
                    System.out.println("Final State for " + player.Name + ": " + player.State);
                });

        System.out.println("");
        System.out.println("");
        players
                .stream()
                .filter(player -> player.State == BlackJackEnums.PlayerState.BUSTED)
                .forEach(player -> {
                    System.out.println(player.Name + " busted");

                    var cardString = player.PlayerHand.size() > 0
                            ? player.PlayerHand.stream().map(currentCard -> currentCard.GetName()).reduce((base, added) -> base + ", " + added).get()
                            : "NO CARDS";

                    System.out.println("cards: " + cardString);
                    System.out.println("Total Value: " + this.GetTotalValue(player.PlayerHand));
                });
    }

    private void CardCountedDeal(BlackJackPlayer player, Boolean isStartingHand){
        var card = deck.Draw();

        // all cards dealt are face up except for the first card dealt to the dealer
        if(player.IsHouse && isStartingHand)
        {
            card.IsVisible = false;
        }

        player.PlayerHand.add(card);

        if (this.GetTotalValue(player.PlayerHand) == BlackJackValue) {
            player.State = BlackJackEnums.PlayerState.BLACKJACK;
            System.out.println(player.Name + " has BlackJack");
        } else if (this.GetTotalValue(player.PlayerHand) < BlackJackValue) {
            System.out.println(player.Name + " has: " + this.GetTotalValue(player.PlayerHand));
        } else {
            player.State = BlackJackEnums.PlayerState.BUSTED;
            System.out.println(player.Name + " has busted at: " + this.GetTotalValue(player.PlayerHand));
        }

        AdjustHighLowCardCount(card);
    }

    private void AdjustHighLowCardCount(BlackJackCard card){
        // only count cards we can see
        if(card.IsVisible) {
            switch (card.GetCardCountedValue()) {
                case LOWCARD:
                    HighLowCardCountedTotal += 1;
                    break;
                case NEUTRALCARD:
                    break;
                case HIGHCARD:
                    HighLowCardCountedTotal += -1;
            }
        }

        double decksRemaing = deck.GetTotalRemainingCards() / 52;
        var trueCount = HighLowCardCountedTotal/ decksRemaing;
        var bettingCount = trueCount - 1;

        System.out.println("");
        System.out.println("");
        System.out.println("CARD COUNTED VALUES");
        System.out.println("Running Count: " + HighLowCardCountedTotal);
        System.out.println("Total decks used: " + decksRemaing);
        System.out.println("True Count: " + trueCount);
        System.out.println("Betting Count: " + bettingCount);
        System.out.println("");
    }
}
