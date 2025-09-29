public class TripList {
    private static class Node {
        Trip data;
        Node next;
        Node(Trip data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node last; //the reference to the last node of the linked list.

    public void add(Trip trip) {

    }

    public void print() {

    } //print the list ordered by the ending date
}
