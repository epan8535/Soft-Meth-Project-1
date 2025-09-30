public class Vehicle implements Comparable<Vehicle> {
    private String plate;
    private Date obtained;
    private Make make;
    private int mileage;

    //constructor for Vehicle
    public Vehicle(String plate, Date obtained, Make make, int mileage){

        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage;

    }

    // getter methods
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

    //Override .equals() method to test if plates Strings match one another
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Vehicle objectBeingCompared = (Vehicle) obj;

        return plate.equals(objectBeingCompared.getPlate());

    }


    //Override toString to print out in a specific format
    @Override
    public String toString(){
        return (plate +":"+ make +":"+ obtained + " [mileage:" + mileage+ "]" );
    }

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
