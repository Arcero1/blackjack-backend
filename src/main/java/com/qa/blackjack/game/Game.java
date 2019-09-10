package com.qa.blackjack.game;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    Deck deck;
    Dealer dealer = new Dealer("easy");
    List<Player> players = new ArrayList<Player>();
    Scanner scanner;

    public Game() throws InvalidNameException {
        deck = new Deck();
        System.out.println("ENTER THE NUMBER OF PLAYERS");
        scanner = new Scanner(System.in);
        int num = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < num; i++) {
            System.out.println("ENTER NAME OF PLAYER " + (i + 1));
            players.add(new Player(scanner.nextLine()));
        }

        dealerTurn();
        while (!everyoneFinished()) {
            for(Player player : players) {
                playerTurn(player);
            }
        }

        for(Player player : players) {
            if (hasWon(player)) {
                
            }
        }

    }

    public boolean hasWon(Player player) {
        if (player.getScore() > dealer.getScore()) {
            return true;
        }
        return false;
    }

    public boolean everyoneFinished() {
        for(Player player : players) {
            if (!player.isFinished()) {
                return false;
            }
        }
        return true;
    }

    private void dealerTurn() {
        dealer.play(deck);
        for(Player player : players) {
            player.hit(deck);
            player.hit(deck);
        }
    }

    private void playerTurn(Player player) {
        if (player.finished) {
            return;
        }
        System.out.println("PLAYER: " + player.getName());
        System.out.println("YOUR CARDS: " + player.getCards());
        String nextMove;

        do {
            System.out.println("choose: hit / stand");
            nextMove = scanner.nextLine();
        } while (!nextMove.equals("hit") && !nextMove.equals("stand"));

        if (nextMove.equals("hit")) {
            System.out.println("NEW CARD: [" + player.hit(deck).getName() + "] ");
            scanner.nextLine();
            return;
        }

        player.stand();
        System.out.println(player.name + " STANDS");
    }
}
