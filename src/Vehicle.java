/**
 * Vehicle class represents the vehicle object within the Fleet class with
 * a plate number, an object obtained of the Date class, a make of the Make class, and an int mileage
 * Vehicles can be compared by Make and then by Date.
 *
 * @author Jake Cordon
 */

public class Vehicle implements Comparable<Vehicle> {

    // ===================== Instance Variables ===================

    private String plate;
    private Date obtained;
    private Make make;
    private int mileage;

    // ===================== Constructors ===================

    /**
     * Creates a vehicle with a given plate, obtained date, make, and mileage
     *
     * @param plate     the license plate of the vehicle
     * @param obtained  the date the vehicle was obtained
     * @param make      the make of the vehicle
     * @param mileage   the current mileage of the vehicle
     */

    public Vehicle(String plate, Date obtained, Make make, int mileage){

        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage;

    }

    // ===================== Getter and Setter Methods ===================
    public String getPlate(){

        return this.plate;

    }

    public Date getDate(){
        return this.obtained;
    }

    public Make getMake(){
        return this.make;
    }

    public int getMileage(){
        return this.mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    // ===================== Public Methods ===================

    /*
    * This method checks if two vehicles' license plates are the same
    *
    *
    * @param Object obj     Object that gets cast to a Vehicle. This vehicle is then compared.
    * @returns true if plate strings are equal and false if they are not
     */

    @Override
    public boolean equals(Object obj){

        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Vehicle objectBeingCompared = (Vehicle) obj;

        return plate.equals(objectBeingCompared.getPlate());

    }


    /*
    * This method overrides the default toString() to print out the plate,make,date obtained, and mileage
    *
     */

    @Override
    public String toString(){

        return (plate + ":" + make + ":" + obtained + " [mileage:" + mileage + "]" );

    }

    /*
    * This method compares vehicle objects first by make and then by Date
    *
    * @param vehicleBeingCompared the vehicle object that is being compared to the first object
    * @return returns -1 if the second object is lexicographically earlier or mileage is greater than first object
    *         returns 0 if date and mileage are equal
    *         returns 1 if the second object is lexicographically later or mileage is less than the first object
     */

    @Override
    public int compareTo(Vehicle vehicleBeingCompared) {

        int makeComparison = this.make.name().compareTo(vehicleBeingCompared.make.name());
        if (makeComparison < 0) {
            return -1;
        }

        if (makeComparison > 0) {
            return 1;
        }

        // If make is the same, compare by date
        int dateComparison = this.obtained.compareTo(vehicleBeingCompared.obtained);
        if (dateComparison < 0){
            return -1;
        }
        if (dateComparison > 0) {
            return 1;
        }

        // Otherwise, they are equal
        return 0;
    }

    // ===================== TestBed Main ===================

    /*
    * Unit Test for the compareTo() method
    *
     */

    public static void main(String[] args){

        Vehicle match = new Vehicle("589ABC", new Date(2025,7,28),Make.HONDA,9000);
        Vehicle notMatch = new Vehicle("123XYZ", new Date(2012,8,10),Make.TOYOTA,1000);
        Vehicle notMatch2 = new Vehicle("987MNO", new Date(2011,11,22),Make.CHEVY,4000);
        Vehicle match2 = new Vehicle("642XYZ", new Date(2025,7,28),Make.HONDA,9000);

        System.out.println("Test One: " + match.compareTo(notMatch) + " (expected: -1)");
        System.out.println("Test Two: " + match.compareTo(match2) + " (expected: 0)");
        System.out.println("Test Three: " + match.compareTo(notMatch2) + " (expected: 1)");




    }

}
