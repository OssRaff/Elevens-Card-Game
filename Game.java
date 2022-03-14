import java.util.*;

public class Game {
    // deck for the game
    private Deck deck;
    // cards in hand
    private Card[] hand;

    // intital state
    private Deck initialDeck;
    private Card[] initialHand;
    private MovesLinkedList moves; // list of list of integers

    // constructor - to crate new game
    public Game() {
        deck = new Deck(); // new deck
        // shuffle the deck
        deck.shuffle(); // shuffle

        // deal 9 cards for the hand
        hand = new Card[9];
        for (int i = 0; i < 9; i++) {
            hand[i] = deck.deal();
        }

        initialDeck = new Deck(deck);
        initialHand = new Card[9];
        for (int i = 0; i < 9; i++) {
            initialHand[i] = hand[i];
        }
        moves = new MovesLinkedList();
    }

    // print the hand
    private void printHand() {
        for (int i = 0; i < hand.length; i++) {
            if (hand[i] != null) {
                System.out.println(i + " => " + hand[i]);
            }
        }
    }

    private boolean isValidSolution(Card[] solution) {
        /*
         * @param solution is an array of cards
         * 
         * @return true if the solution is valid and false otherwise
         */
        // if two cards were given that sum to 11
        if (solution.length == 2) {
            if (solution[0].getRank() + solution[1].getRank() == 11) {
                return true;
            }
        } else if (solution.length == 3) { // if three cards were given that are all face cards
            for (int i = 0; i < solution.length; i++) {
                if (solution[i].getRank() <= 10) { // if any rank <= 10 return false
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private int[] getSolution() {
        // Card[] faces = new Card[3];
        int[] faces = new int[3];
        int numFaces = 0;

        // check if there is a sum 11 in hand or 3 faces
        for (int i = 0; i < hand.length - 1; i++) {
            if (hand[i].getRank() > 10) {
                faces[numFaces++] = i;
                if (numFaces == 3) { // have 3 faces so game not over
                    return faces; // valid solution returns
                }
            } else {
                for (int j = i + 1; j < hand.length; j++) {
                    if (isValidSolution(new Card[] { hand[i], hand[j] })) { // find sum 11
                        return new int[] { i, j }; //return valid solution
                    }
                }
            }
        }
        // no valid solution found
        return null;
    }

    private int isGameOver() {
        // 1 if win
        // -1 if lose
        // 0 if not over

        // game is over if
        // 1. deck is empty and hand is empty => win
        // 2. there is no solution in hand => lose
        if (deck.isEmpty() && hand.length == 0) {
            return 1;
        }

        // get solution
        int[] solution = getSolution();
        if (solution == null) {
            return -1;
        } else {
            return 0;
        }
    }

    // save move
    private void saveMove(int[] move) {
        System.out.println("saveMove: " + Arrays.toString(move));
        moves.add(move);
    }

    private void getHistory(Scanner scanner) {
        int size = moves.size();
        int i = 0;
        do {
            // confirm user wants next
            System.out.println(">>> enter anything for next move\n>>> or quit to stop...");
            String answer = scanner.nextLine();
            if (answer.equals("quit")) {
                break;
            }

            int[] solutionIndex = moves.getNext();
            if (solutionIndex == null) {
                System.out.println("no more moves");
                break;
            }
            for (int j = 0; j < solutionIndex.length; j++) {
                // print the solution
                System.out.println(solutionIndex[j] + " => " + initialHand[solutionIndex[j]]);
                // deal from deck to hand
                initialHand[solutionIndex[j]] = initialDeck.deal();
            }
            System.out.println("\n");
        } while (i < size);
    }

    private void playDemo() {
        // get the solution
        Scanner scanner = new Scanner(System.in);
        System.out.println(deck.cardsLeft() + " are left in deck!");
        printHand();
        int numCards = 0;
        // get the cards
        Card[] solution = null;
        int[] solutionIndex = null;

        // if demo get solution automatically
        solutionIndex = getSolution();
        if (solutionIndex == null) {
            System.out.println("No solution in hand");
        } else {
            numCards = solutionIndex.length;
            solution = new Card[numCards];
            for (int i = 0; i < numCards; i++) {
                solution[i] = hand[solutionIndex[i]];
            }
        }
        System.out.println(">>> enter anything for next move\n>>> or quit to stop...");
        String answer = scanner.nextLine();
        if (answer.equals("quit")) {
            // exit
            scanner.close();
            return;
        }

        // print solution
        System.out.println("Solution: ");
        for (int i = 0; i < solution.length; i++) {
            System.out.println(solutionIndex[i] + " => " + solution[i]);
        }

        for (int i = 0; i < solution.length; i++) { // remove those cards
            if (deck.cardsLeft() >= numCards) {
                hand[solutionIndex[i]] = deck.deal();
            } else {
                printHand();
                System.out.println("No choice left - You lose!");
                break;
            }
        }
        switch (isGameOver()) {
        case 1:
            System.out.println("You win!");
            getHistory(scanner);
            break;
        case -1:
            printHand();
            System.out.println("No choice left - You lose!");
            getHistory(scanner);
            break;
        default:
            saveMove(solutionIndex);
            playDemo();
        }
        scanner.close();
    }

    private void playTurn() {
        // get the solution
        Scanner scanner = new Scanner(System.in);
        System.out.println(deck.cardsLeft() + " are left in deck!");
        printHand();
        int choice = -1;
        do {
            System.out.println("\n");
            System.out.println("Enter 1 for hint");
            System.out.println("Enter 2 for sum-eleven");
            System.out.println("Enter 3 for face cards");
            System.out.println("Enter 5 to quit");
            System.out.print(">>> ");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 5);

        if (choice == 5) {
            scanner.close();
            return;
        }

        if (choice == 1) {
            // hint
            int[] solutionIndex = getSolution();
            if (solutionIndex == null) {
                System.out.println("No solution in hand");
            } else {
                System.out.println("Solution is: ");
                for (int i = 0; i < solutionIndex.length; i++) {
                    System.out.println(solutionIndex[i] + " => " + hand[solutionIndex[i]]);
                }
            }
            playTurn();
        } else {
            int numCards = choice;
            // get the cards
            Card[] solution = new Card[numCards];
            int[] solutionIndex = new int[numCards];

            for (int i = 0; i < numCards; i++) {
                System.out.print("Enter index of card " + (i + 1) + "\n>>> ");
                int cardIndex = scanner.nextInt();
                if (cardIndex < 0 || cardIndex >= hand.length) {
                    System.out.println("Invalid card index");
                    i--;
                    continue;
                }
                solutionIndex[i] = cardIndex;
                solution[i] = hand[cardIndex];
            }

            // print solution
            System.out.println("Solution: ");
            for (int i = 0; i < solution.length; i++) {
                System.out.println(solutionIndex[i] + " => " + solution[i]);
            }

            // check if solution is valid
            if (isValidSolution(solution)) {
                System.out.println("Solution is valid");
                for (int i = 0; i < solution.length; i++) { // remove those cards
                    if (deck.cardsLeft() >= numCards) {
                        hand[solutionIndex[i]] = deck.deal();
                    } else {
                        printHand();
                        System.out.println("No choice left - You lose!");
                        break;
                    }
                }
                switch (isGameOver()) {
                case 1:
                    System.out.println("You win!");
                    getHistory(scanner);
                    break;
                case -1:
                    printHand();
                    System.out.println("No choice left - You lose!");
                    getHistory(scanner);
                    break;
                default:
                    System.out.println("Nice move!");
                    saveMove(solutionIndex);
                    playTurn();
                }
            } else {
                System.out.println("Solution is invalid, retry.");
                playTurn();
            }
            scanner.close();
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 for demo");
        System.out.println("Enter 2 for play");
        System.out.print(">>> ");
        int choice = scanner.nextInt();
        if (choice == 1) {
            playDemo();
        } else if (choice == 2) {
            playTurn();
        } else {
            System.out.println("Invalid choice");
        }
        scanner.close();
    }

}
