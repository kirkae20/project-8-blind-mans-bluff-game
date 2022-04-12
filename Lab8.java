import java.util.Scanner;
public class Lab8 {

    public static LinkedList initialize_deck() {

        LinkedList deck = new LinkedList();

        // populate linked list with a single deck of cards
        for (Card.suites s : Card.suites.values()) {
            for(Card.ranks r : Card.ranks.values()) {
                if (r != Card.ranks.NULL && s != Card.suites.NULL) {
                    Card newCard = new Card(s, r);
                    //newCard.print_card();
                    deck.add_at_tail(newCard);
                }
            }
        }

        return deck;
    }
    // compare enum ordinal values of p1 Card and computer Card rank
    public static int compare(Card p1, Card computer) {
        if (p1.getRank().ordinal() != computer.getRank().ordinal()) {
            return p1.getRank().ordinal() - computer.getRank().ordinal();
        }
        else {
            // if rank is equal, compare enum ordinal values of p1 Card and computer Card suit
            return p1.getSuit().ordinal() - computer.getSuit().ordinal();
        }
    }

    public static void rage_quit(LinkedList player1, LinkedList computer, LinkedList deck){
        System.out.println("Restarting...\n");

        // while the p1 and computer lists are not empty, remove cards from hand and add them back to the deck
        while(!player1.isEmpty()){
            deck.add_at_tail(player1.remove_from_head());
            deck.add_at_tail(computer.remove_from_head());
        }
        //deck.print();
        deck.sanity_check();

        // shuffle the deck (random order)
        deck.shuffle(512);
        //deck.print();
        deck.sanity_check();

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            // player removes a card from the deck and adds to their hand
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        // restart blindman's bluff
        play_blind_mans_bluff(player1, computer, deck);
    }

    private static void play_blind_mans_bluff(LinkedList player1, LinkedList computer, LinkedList deck) {
        Scanner scnr = new Scanner(System.in);
        System.out.println("\nStarting Blind mans Bluff ");
        System.out.println("\nDealing cards...");
        int counter = 0;
        int losses = 0;
        int wins = 0;
        // play rounds until 5 hands are plaid or until p1 has 3 losses
        while (counter < 5 && losses < 3) {

            System.out.println("\nRound " + (counter + 1) + ":\n");
            Card p1Card = player1.remove_from_head();
            Card computerCard = computer.remove_from_head();
            System.out.print("Your opponent's card is : ");
            computerCard.print_card();
            System.out.println("\nTake a guess: Is your card higher or lower?");
            String hOrL = scnr.nextLine();
            // compare rank
            // if rank is equal, compare suit
            int compareVal = compare(p1Card, computerCard);
            // if p1 guessed higher and p1's Card was lower OR if p1 guessed lower and p1's card was higher, increase
            // losses by 1
            if(compareVal < 0 && hOrL.equalsIgnoreCase("higher") || (compareVal > 0 &&
                    hOrL.equalsIgnoreCase("lower"))) {
                System.out.print("Wrong.\nYour card was: ");
                p1Card.print_card();
                System.out.print("\nTheir card was: ");
                computerCard.print_card();
                losses++;
                System.out.println("\n\nCurrent score: \nYou: " + wins + "\nComputer: " + losses);
            }
            // if p1 correctly guessed higher OR if p1 correctly guessed lower, increase wins by 1
            else {
                System.out.print("Correct!\nYour card was: ");
                p1Card.print_card();
                System.out.print("\nTheir card was: ");
                computerCard.print_card();
                wins++;
                System.out.println("\n\nCurrent score: \nYou: " + wins + "\nComputer: " + losses);
            }
            // add p1's and computer's Cards back to the deck
            // increase counter by 1
            deck.add_at_tail(p1Card);
            deck.add_at_tail(computerCard);
            counter++;
        }
        // if p1 gets 3 losses before the last hand, call rage_quit()
        if(losses == 3 && wins < 2) {
            System.out.println("\nRAGE QUITTING...");
            rage_quit(player1, computer, deck);
        }
        // if 5 hands have been played, show game results
        if(counter == 5){
            if (wins > losses){
                System.out.println("\nYou win!");
                System.out.println("Final score: " + wins + " to " + losses);
            }
            else {
                System.out.println("Better luck next time.");
                System.out.println("Final score: " + losses + " to " + wins);
            }
        }
    }

    public static void main(String[] args) {

        // create a deck (in order)
        LinkedList deck = initialize_deck();
        //deck.print();
        deck.sanity_check(); // because we can all use one

        // shuffle the deck (random order)
        deck.shuffle(512);
        //deck.print();
        deck.sanity_check(); // because we can all use one

        // cards for player 1 (hand)
        LinkedList player1 = new LinkedList();
        // cards for player 2 (hand)
        LinkedList computer = new LinkedList();

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            // player removes a card from the deck and adds to their hand
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        // let the games begin!
        play_blind_mans_bluff(player1, computer, deck);
    }
}
