public class Deck {
    /*  Deck of cards behaves similar to stack in term of addition and removal
        It has shuffling property which is not inherent to stack.   */
    // cards array of size 52
    private Card[] cards;
    // index of the top card
    private int topCard;

    // constructor
    public Deck() {
        // initialize cards array
        cards = new Card[52];
        // initialize cards array
        int index = 0;
        // suit 
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        for (int suit = 0; suit < suits.length; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                cards[index] = new Card(rank, suits[suit]);
                index++;
            }
        }
        // initialize topCard
        topCard = 0;
    }

    public Deck(Deck d) {
        // create a deck from d
        cards = new Card[52];

        for (int i = 0; i < 52; i++) {
            cards[i] = d.cards[i];
        }
        
        topCard = d.topCard;
    }

    // shuffle the deck
    public void shuffle() {
        // shuffle the cards
        int len = cards.length;
        for (int i = 0; i < len; i++) {
            int random = (int) (Math.random() * len); // pick a random integer
            // swap card[i] with card[random]
            Card temp = cards[i];
            cards[i] = cards[random];
            cards[random] = temp;
        }
        // reset topCard
        topCard = 0;
    }

    // deal a card
    public Card deal() {
        /* like a pop operation on stack */
        // deal the top card and remove it
        Card top = cards[topCard];
        // increment topCard
        topCard++;
        return top;
    }

    // return the number of cards left in the deck
    public int cardsLeft() {
        return cards.length - topCard;
    }

    // is deck empty
    public boolean isEmpty() {
        return cardsLeft() == 0;
    }
}
