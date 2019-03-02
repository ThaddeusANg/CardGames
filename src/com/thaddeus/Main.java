package com.thaddeus;

import Games.BlackJack.BlackJack;
import Games.BlackJack.BlackJackPlayer;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception{

        Scanner scanner = new Scanner(System.in);

        // write your code here
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

        var playerC = new BlackJackPlayer();
        playerA.Name = "playerA";
        playerA.IsHouse = false;
        playerA.BalanceMoney = 50.00;

        var playerD = new BlackJackPlayer();
        playerB.Name = "playerB";
        playerB.IsHouse = false;
        playerB.BalanceMoney = 50.00;

        var playerE = new BlackJackPlayer();
        playerA.Name = "playerA";
        playerA.IsHouse = false;
        playerA.BalanceMoney = 50.00;

        var playerF = new BlackJackPlayer();
        playerB.Name = "playerB";
        playerB.IsHouse = false;
        playerB.BalanceMoney = 50.00;
        List<BlackJackPlayer> players = Arrays.asList(house, playerA, playerB);

        String playAgain;
        do{
            game.StartGame(players);
            System.out.println("?????");
            System.out.println("Play again?");
            playAgain = scanner.nextLine();
        }while(playAgain.equalsIgnoreCase("yes"));
    }
}

public class oneAway{
    public String compress(String text){
        Map<Character, Long> count = text.chars().mapToObj(c -> (char) c).collect(e -> e, Collectors.counting());
    }
}