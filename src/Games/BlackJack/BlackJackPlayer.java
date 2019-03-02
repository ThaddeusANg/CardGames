package Games.BlackJack;

import Enums.BlackJackEnums;
import Objects.Cards.BlackJackCard;

import java.util.ArrayList;
import java.util.List;

public class BlackJackPlayer{
    public String Name;
    public List<BlackJackCard> PlayerHand = new ArrayList<>();
    public double BetMoney;
    public double BalanceMoney;
    public boolean IsHouse;
    public BlackJackEnums.PlayerState State = BlackJackEnums.PlayerState.ACTIVE;

    private double Id;
    public BlackJackPlayer(){
        this.Id = Math.random();
    }

    // correctly overrides Object.equals(Object)
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            BlackJackPlayer player = (BlackJackPlayer) obj;
            return player.Id == this.Id;
        }

        return false;
    }}
