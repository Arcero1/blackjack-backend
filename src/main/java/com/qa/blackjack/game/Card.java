package com.qa.blackjack.game;

import javax.naming.InvalidNameException;

public class Card
{
    private String name;
    private String suit = "";

    public Card(String name) throws InvalidNameException {
        setName(name);
    }

    public Card(String name, String suit) throws InvalidNameException {
        setName(name);
        setSuit(suit);
    }

    public String getId() {
        String id;

        try {
            Integer.parseInt(name);
            id = name;
        } catch (NumberFormatException e) {
            id = name.substring(0, 1);
        }
        return id + (suit.isEmpty() ? "" : suit.substring(0, 1));
    }

    private int cardValue() {

        switch (this.name) {
            case "King":
            case "Queen":
            case "Jack":
                return 10;
            case "Ace":
                return 11;
            default:
                return Integer.parseInt(this.name);
        }
    }

    public int getValue() {
        return cardValue();
    }

    public void setSuit(String suit) throws InvalidNameException {
        switch(suit) {
            case "Clubs":
            case "Diamonds":
            case "Hearts":
            case "Spades":
                break;
            default:
                throw new InvalidNameException();
        }

        this.suit = suit;
    }

    public String getSuit() {
        return this.suit;
    }

    public void setName(String name) throws InvalidNameException {
        switch(name) {
            case "King":
            case "Queen":
            case "Jack":
            case "Ace":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
                break;
            default:
                throw new InvalidNameException();
        }

        this.name = name;
    }

    public String getName() {
        return this.name;
    }



    public String toString() {
        return this.name + (!this.suit.equals("") ? " of " + this.suit : "");
    }
}
