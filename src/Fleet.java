/**
 * Fleet class manages a resizable collection of vehicles.
 * The fleet is stored in an array and grows by 4 when capacity is reached.
 * Vehicles can be added, removed, checked for existence, and printed
 * in sorted order (by make, then by date obtained).
 *
 * @author Jake Cordon and Everett Pan
 */
public class Fleet {

    // ================== Constants ==================
    private static final int CAPACITY = 4;   // initial capacity
    private static final int NOT_FOUND = -1; // sentinel for not found

    // ================== Instance Variables ==================
    private Vehicle[] fleet; // array of vehicles
    private int size;        // current number of vehicles in the fleet

    // ================== Constructors ==================

    /**
     * Creates an empty fleet with the default initial capacity.
     */
    public Fleet() {
        fleet = new Vehicle[CAPACITY];
        size = 0;
    }

    // ================== Private Helper Methods ==================

    /**
     * Finds the index of a vehicle in the fleet.
     *
     * @param vehicle the vehicle to search for
     * @return index of the vehicle, or NOT_FOUND if not present
     */
    private int find(Vehicle vehicle) {
        for (int i = 0; i < size; i++) {
            if (vehicle.equals(fleet[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increases the capacity of the fleet by 4.
     */
    private void grow() {
        int newArrayLength = fleet.length + 4;
        Vehicle[] newFleet = new Vehicle[newArrayLength];

        for (int i = 0; i < fleet.length; i++) {
            newFleet[i] = fleet[i];
        }

        fleet = newFleet;
    }

    /**
     * Compares two vehicles by make, then by obtained date.
     *
     * @param first  the first vehicle
     * @param second the second vehicle
     * @return negative if first < second, 0 if equal, positive if greater
     */
    private int compareByMakeThenDate(Vehicle first, Vehicle second) {
        int makeComparison = first.getMake().compareTo(second.getMake());
        if (makeComparison != 0) {
            return makeComparison;
        }
        return first.getDate().compareTo(second.getDate());
    }

    // ================== Public Methods ==================

    /**
     * Adds a vehicle to the fleet.
     * If the array is full, the capacity is increased by 4.
     *
     * @param vehicle the vehicle to add
     */
    public void add(Vehicle vehicle) {
        if (size == fleet.length) {
            grow();
        }
        fleet[size] = vehicle;
        size++;
    }

    /**
     * Removes a vehicle from the fleet.
     * The removed slot is replaced with the last vehicle in the array.
     *
     * @param vehicle the vehicle to remove
     */
    public void remove(Vehicle vehicle) {
        int index = find(vehicle);
        if (index == NOT_FOUND) {
            return;
        }

        fleet[index] = fleet[size - 1];
        fleet[size - 1] = null;
        size--;
    }

    /**
     * Checks if the fleet contains the given vehicle.
     *
     * @param vehicle the vehicle to check
     * @return true if found, false otherwise
     */
    public boolean contains(Vehicle vehicle) {
        return find(vehicle) != NOT_FOUND;
    }

    /**
     * Prints the list of vehicles ordered by make, then by date obtained.
     */
    public void printByMake() {
        if (size == 0) {
            System.out.println("There is no vehicle in the fleet.");
            return;
        }

        // Selection sort (in-place)
        for (int i = 0; i < size - 1; i++) {
            int minimumIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (compareByMakeThenDate(fleet[j], fleet[minimumIndex]) < 0) {
                    minimumIndex = j;
                }
            }
            if (minimumIndex != i) {
                Vehicle temp = fleet[i];
                fleet[i] = fleet[minimumIndex];
                fleet[minimumIndex] = temp;
            }
        }

        System.out.println("*List of vehicles in the fleet, ordered by make and date obtained.");
        for (int i = 0; i < size; i++) {
            System.out.println(fleet[i].toString());
        }
        System.out.println("*end of list.");
        System.out.println();
    }
}