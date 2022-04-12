import java.util.Random;

// linked list class for a deck of cards
public class LinkedList {

    public Node head;
    public Node tail;
    public int size = 0;

    LinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    public void shuffle(int shuffle_count) {

        Random rand = new Random();
        for(int i = 0; i < shuffle_count; i++) {
            // pick two random integers
            int r1 = rand.nextInt(52);
            int r2 = rand.nextInt(52);

            if(r1 != r2) {
                swap(r1,r2); // swap nodes at these indices
            }
        }
    }

    // remove a card from a specific index
    public Card remove_from_index(int index) {
        Card returnCard;
        if (head == null){ // if list is empty, return null
            return null;
        }

        if (index == 0){ // removes from head when index is 0
            Node currNode = head;
            returnCard = currNode.data;
            head = currNode.next;
            size--;
            return returnCard;
        }
        Node currNode = head;
        Node succNode = currNode.next;


        int counter = 0;
        while (succNode != null && counter < index) { // finds index to remove using counter
            currNode = currNode.next;
            succNode = currNode.next;
            counter++;
        }
        returnCard = currNode.data;
        if (succNode != null) { // removes from index if currNode is not last index

            currNode.prev.next = succNode;
            succNode.prev = currNode.prev;

            size--;
            return returnCard;
        }
        else {  // removes from index if currNode is at the tail
            tail = tail.prev;
            currNode.prev.next = null;

            size--;
            return returnCard;
        }
    }

    // insert a card at a specific index
    public void insert_at_index(Card x, int index) {
        Node newNode = new Node();
        newNode.data = x;

        if (head == null) { // if list is empty, add Card to list
            head = newNode;
            size++;
        }
        if (index == 0){ // if index is zero, add Card to head of list
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node currNode = head;
        Node prevNode = null;

        int counter = 0;
        while(currNode.next != null && counter < index) { // finds index to remove using counter
            prevNode = currNode;
            currNode = currNode.next;

            counter++;
        }

        if (currNode != null) { // if the index to add to is not the last index, insert Card to index

            prevNode.next = newNode;
            newNode.next = currNode;
            currNode.prev = newNode;
            newNode.prev = prevNode;

            size++;
        }
        else { // if index is the last index, insert Card at tail of list
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            tail.next = null;
            size++;
        }
    }

    // swap two cards in the deck at the specific indices
    public void swap(int index1, int index2) {
        Card card1;
        Card card2;

        if(index1 > index2) { // swaps indices when index 1 has a higher index than index 2
            card1 = remove_from_index(index1);
            insert_at_index(card1, index2);
            card2 = remove_from_index(index2 + 1);
            insert_at_index(card2, index1);
        }
        else{ // swaps indices when index 2 has a higher index than index 1
            card1 = remove_from_index(index1);
            insert_at_index(card1, index2);
            card2 = remove_from_index(index2 - 1);
            insert_at_index(card2, index1);
        }

    }

    // add card at the end of the list
    public void add_at_tail(Card data) {
        Node newNode = new Node();
        newNode.data = data;
        newNode.next = null;

        Node currNode = head;

        if (head == null){ // if list is empty, add Card
            head = newNode;
            tail = newNode;
            head.prev = null;
            tail.next = null;
            size++;
            return;
        }
        tail.next = newNode; // if list is not empty, add Card to tail
        newNode.prev = tail;
        tail = newNode;
        tail.next = null;
        size++;
    }

    // remove a card from the beginning of the list
    public Card remove_from_head() {
        Node currNode = head;
        Card removeCard = currNode.data;
        if (head == null){ // if list is empty, return null
            return null;
        }
        else if (head == tail) { // if list has one element, remove and return element
            head = null;
            tail = null;
            return removeCard;
        }
        head = head.next; // if list has more than one element, remove and return element
        head.prev = null;
        size--;
        return removeCard;
    }

    public boolean isEmpty() { // checks if list is empty
        if(head == null){
            return true;
        }
        return false;
    }

    // check to make sure the linked list is implemented correctly by iterating forwards and backwards
    // and verifying that the size of the list is the same when counted both ways.
    // 1) if a node is incorrectly removed
    // 2) and head and tail are correctly updated
    // 3) each node's prev and next elements are correctly updated
    public void sanity_check() {
        // count nodes, counting forward
        Node curr = head;
        int count_forward = 0;
        while (curr != null) {
            curr = curr.next;
            count_forward++;
        }


        // count nodes, counting backward
        curr = tail;
        int count_backward = 0;
        while (curr != null) {
            curr = curr.prev;
            count_backward++;
        }


        // check that forward count, backward count, and internal size of the list match
        if (count_backward == count_forward && count_backward == size) {
            System.out.println("Basic sanity Checks passed");
        }
        else {
            // there was an error, here are the stats
            System.out.println("Count forward:  " + count_forward);
            System.out.println("Count backward: " + count_backward);
            System.out.println("Size of LL:     " + size);
            System.out.println("Sanity checks failed");
            System.exit(-1);
        }
    }

    // print the deck
    public void print() {
        Node curr = head;
        int i = 1;
        while(curr != null) {
            curr.data.print_card();
            if(curr.next != null)
                System.out.print(" -->  ");
            else
                System.out.println(" X");

            if (i % 7 == 0) System.out.println("");
            i = i + 1;
            curr = curr.next;
        }
        System.out.println("");
    }
}