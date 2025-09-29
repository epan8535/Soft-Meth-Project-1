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
        Node newNode = new Node(trip);
        if (last == null) {
            last = newNode;
            last.next = last;
            return;
        }
        newNode.next = last.next;
        last.next = newNode;
        last = newNode;
    }

    public void print() {
        if (last == null) {
            System.out.println("There is no archived trips.");
            return;
        }

        Node head = last.next;

        // 1) count nodes without do-while
        int count = 0;
        Node p = head;
        while (true) {
            count++;
            p = p.next;
            if (p == head) break;
        }

        // 2) copy to array
        Trip[] a = new Trip[count];
        p = head;
        for (int i = 0; i < count; i++) {
            a[i] = p.data;
            p = p.next;
        }

        // 3) insertion sort by ending date
        for (int i = 1; i < count; i++) {
            Trip key = a[i];
            int j = i - 1;
            while (j >= 0) {
                Date endKey = key.getBooking().getEnd();
                Date endJ   = a[j].getBooking().getEnd();
                if (endKey.compareTo(endJ) < 0) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }

        // 4) print
        for (int i = 0; i < count; i++) System.out.println(a[i]);
    }

}
