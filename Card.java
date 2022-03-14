public class Card {
    private int rank;
    private String suit;

    // card constructor
    public Card(int rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // get card number
    public int getRank() {
        return rank;
    }

    // get card suit
    public String getsuit() {
        return suit;
    }

    // toString
    public String toString() {
        return rank + " of " + suit;
    }

    // is sum 11
    public static boolean isSum11(Card card1, Card card2) {
        return (card1.getRank() + card2.getRank() == 11);
    }
}